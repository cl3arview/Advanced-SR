package org.advancedsr.restcontrollers;

import org.advancedsr.dtos.ImageDTO;
import org.advancedsr.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageDTO> uploadImage(@RequestParam("file") MultipartFile file,
                                                @RequestParam("username") String username) throws IOException {
        ImageDTO savedImage = imageService.saveImage(file, username);
        return ResponseEntity.ok(savedImage);
    }

    @GetMapping
    public ResponseEntity<List<ImageDTO>> getAllImagesByUser(@RequestParam("username") String username) {
        List<ImageDTO> images = imageService.getAllImagesByUser(username);
        return ResponseEntity.ok(images);
    }

/*
    @GetMapping("/{id}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String id) throws IOException {
        Resource file = imageService.loadImageAsResource(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(file);
    }
*/

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) throws MalformedURLException {
        Resource resource = imageService.loadImageAsResource(fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // Adjust content type accordingly
                .body(resource);
    }


    @GetMapping("/file/{imageId}")
    public ResponseEntity<Resource> getImageByImageId(@PathVariable String imageId) {
        Resource resource = imageService.loadImageAsResourceById(imageId);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // Adjust content type accordingly
                .body(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable String id) {
        imageService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }
}
