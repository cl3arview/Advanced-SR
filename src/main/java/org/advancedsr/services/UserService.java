package org.advancedsr.services;

import org.advancedsr.dtos.UserDTO;
import org.advancedsr.entities.User;
import org.advancedsr.entities.Role;
import org.advancedsr.repositories.UserRepository;
import org.advancedsr.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository; // RoleRepository injection to set the role for the user

    @Autowired
    private PasswordEncoder passwordEncoder;




    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword())); // added password encoding in user creation
        user = userRepository.save(user);
        return convertToDTO(user);
    }

    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDTO(user);
    }

    public UserDTO updateUser(String username, UserDTO userDTO) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword())); // added password encoding in user update
        user = userRepository.save(user);
        return convertToDTO(user);
    }

    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setUsername(user.getUsername());
        dto.setActif(user.getActif());
        // Removed setPassword from entity-to-DTO conversion for security
        dto.setRoleName(user.getRole().getRoleName()); // Set role name for DTO (error handling in the dto-to-entity conversion)
        return dto;
    }

    private User convertToEntity(UserDTO dto) {
        Role role = roleRepository.findById(dto.getRoleName())
                .orElseThrow(() -> new RuntimeException("Role not found: " + dto.getRoleName()));

        User user = new User();
        user.setUsername(dto.getUsername());
        // Removed setPassword from DTO-to-entity conversion for security
        user.setActif(dto.getActif());
        user.setRole(role); // Set the role fetched from the database
        return user;
    }
}