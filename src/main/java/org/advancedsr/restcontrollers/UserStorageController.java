package org.advancedsr.restcontrollers;
import org.springframework.core.io.Resource;
import org.springframework.core.io.InputStreamResource;

import org.advancedsr.dtos.UserStorageDTO;
import org.advancedsr.entities.UserStorage;
import org.advancedsr.services.UserStorageService;
import org.advancedsr.services.fileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/userstorage")
public class UserStorageController {

    private final UserStorageService userStorageService;

    private final fileStorageService fileStorageService;

    @Autowired
    public UserStorageController(UserStorageService userStorageService, org.advancedsr.services.fileStorageService fileStorageService) {
        this.userStorageService = userStorageService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserStorageDTO> getUserStorage(@PathVariable Long id) {
        try {
            UserStorageDTO userStorageDTO = userStorageService.getUserStorage(id);
            return ResponseEntity.ok(userStorageDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserStorageDTO> saveUserStorage(@RequestParam("file") MultipartFile file,
                                                          @RequestParam("username") String username) throws IOException {
        try {
            UserStorageDTO userStorageDTO = userStorageService.saveUserStorage(file, username);
            return new ResponseEntity<>(userStorageDTO, HttpStatus.CREATED);
        } catch (SQLException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

/*
    @GetMapping("/file/{id}")
    public ResponseEntity<Resource> getFile(@PathVariable Long id) {
        try {
             UserStorageDTO userStorage = userStorageService.getUserStorage(id);  // Assume this service call fetches the necessary storage details
            Long largeObjectId = userStorage.getLargeObject();  // This would be your large object ID for PostgreSQL

            // Assuming you have a service method to fetch the file stream based on the large object ID
            InputStream fileStream = fileStorageService.getFileStream(largeObjectId);
            Resource resource = new InputStreamResource(fileStream);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + userStorage.getFileName() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
*/


}
