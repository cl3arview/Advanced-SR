package org.advancedsr.restcontrollers;

import jakarta.validation.Valid;
import org.advancedsr.dtos.UserDTO;
import org.advancedsr.payload.LoginRequest;
import org.advancedsr.payload.LoginResponse;
import org.advancedsr.security.JwtTokenProvider;
import org.advancedsr.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.Instant;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        String jwt = tokenProvider.generateToken(authentication.getName());
        Instant expiration = tokenProvider.getTokenExpiration();

        // Extract role name from authentication
        String roleName = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("Error fetching role.");

        LoginResponse response = new LoginResponse(loginRequest.getUsername(), roleName , jwt,expiration);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        UserDTO newUser = userService.createUser(userDTO);
        newUser.setPassword(null); // ensuring the password is not sent back in the ResponseEntity (Double measure since it's taken care of in the entity-to-DTO conversion)
        return ResponseEntity.ok(newUser);
    }

}