package org.advancedsr.restcontrollers;

import org.advancedsr.entities.User;
import org.advancedsr.repositories.UserRepository;
import org.advancedsr.services.ImageService;
import org.advancedsr.services.UpscalingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.io.IOException;
import java.util.Optional;



@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/images")
public class UpscalingController {

    private final UpscalingService upscalingService;

    private final UserRepository userRepository;

    private  final ImageService imageService;

    @Autowired
    public UpscalingController(UpscalingService upscalingService, UserRepository userRepository, ImageService imageService) {
        this.upscalingService = upscalingService;
        this.userRepository = userRepository;
        this.imageService = imageService;
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @Operation(summary = "Upload an image for super-resolution processing")
    @ApiResponse(responseCode = "200", description = "Image processed successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @ApiResponse(responseCode = "400", description = "Fatal error")
    @PostMapping(
            value = "/upload",
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE },
            produces = { MediaType.IMAGE_PNG_VALUE }
    )
    public ResponseEntity<?> uploadImage(@RequestPart("image") MultipartFile file) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                Optional<User> user = userRepository.findByUsername(userDetails.getUsername());

                imageService.saveImage(file, userDetails.getUsername());

                return processImage(file);
            } else  {
                // Log unexpected principal type
                Logger logger = LoggerFactory.getLogger(UpscalingController.class);

                logger.error("Expected UserDetails as principal, but found: {}", principal);
                return processImage(file);

            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Image ingesting failed, try again.");
        }
    }

    private ResponseEntity<?> processImage(MultipartFile file) {
        try {
            byte[] enhancedImage = upscalingService.enhanceImage(file);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(enhancedImage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during image processing: " + e.getMessage());
        }
    }


    @Operation(summary = "Fetch an image by name after processing")
    @ApiResponse(responseCode = "200", description = "Image found and returned successfully")
    @ApiResponse(responseCode = "404", description = "Image not found")
    @GetMapping(value = "/{imageName}", produces = { MediaType.IMAGE_PNG_VALUE })
    public ResponseEntity<?> getImage(@PathVariable String imageName) {
        try {
            byte[] image = upscalingService.getImageByName(imageName);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(image);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Image not found: " + e.getMessage());
        }
    }
}
