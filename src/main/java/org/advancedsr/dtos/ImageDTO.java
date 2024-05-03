package org.advancedsr.dtos;

import java.util.Date;

public class ImageDTO {
    private String imageId; // This should be read-only in the DTO
    private String imagePath;
    private Date uploadedAt;
    private String username; // Username to identify the user associated with the image

    // Getters and Setters but omit setter for imageId to make it read-only
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Date getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Date uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // No setImageId to ensure it's not settable through DTO
}
