package org.advancedsr.services;

import org.advancedsr.dtos.ImageDTO;
import org.advancedsr.entities.Image;
import org.advancedsr.entities.User;
import org.advancedsr.repositories.ImageRepository;
import org.advancedsr.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageService {

    private static final String IMAGE_FOLDER = "src/main/resources/static/images/";

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public ImageDTO saveImage(MultipartFile file, String username) throws IOException {
        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path path = Paths.get(IMAGE_FOLDER + filename);
        Files.copy(file.getInputStream(), path);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Image image = new Image();
        image.setImagePath("/images/" + filename);
        image.setUploadedAt(new Date());
        image.setUser(user);
        image = imageRepository.save(image);

        return convertToDTO(image);
    }

    @Transactional(readOnly = true)
    public List<ImageDTO> getAllImagesByUser(String username) {
        return imageRepository.findAllByUserUsername(username).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

/*    @Transactional(readOnly = true)
    public Resource loadImageAsResource(String imageId) throws IOException {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));
        Path filePath = Paths.get(IMAGE_FOLDER).resolve(image.getImagePath()).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        if(resource.exists()) {
            return resource;
        } else {
            throw new RuntimeException("File not found " + imageId);
        }
    }*/

    @Transactional(readOnly = true)
    public Resource loadImageAsResource(String fileName) throws MalformedURLException {
        Path filePath = Paths.get("src/main/resources/static/images/" + fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        if (!resource.exists()) {
            throw new RuntimeException("File not found " + fileName);
        }
        return resource;
    }

    public Resource loadImageAsResourceById(String imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found with id: " + imageId));

        Path filePath = Paths.get("src/main/resources/static" + image.getImagePath()).normalize();
        Resource resource;
        try {
            resource = new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error while reading file", e);
        }
        if (!resource.exists()) {
            throw new RuntimeException("File not found " + image.getImagePath());
        }
        return resource;
    }
    @Transactional
    public void deleteImage(String imageId) {
        imageRepository.deleteById(imageId);
    }

    private ImageDTO convertToDTO(Image image) {
        ImageDTO dto = new ImageDTO();
        dto.setImagePath(image.getImagePath());
        dto.setUploadedAt(image.getUploadedAt());
        dto.setUsername(image.getUser().getUsername());
        return dto;
    }
}
