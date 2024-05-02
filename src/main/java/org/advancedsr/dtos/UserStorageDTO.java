package org.advancedsr.dtos;

import lombok.Data;

@Data
public class UserStorageDTO {


    private Long userStorageId;
    private String username;
    private Long largeObject;

    public UserStorageDTO(Long userStorageId, String username, Long largeObject) {
        this.userStorageId = userStorageId;
        this.username = username;
        this.largeObject = largeObject;
    }


    public Long getUserStorageId() {
        return userStorageId;
    }

    public void setUserStorageId(Long userStorageId) {
        this.userStorageId = userStorageId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getLargeObject() {
        return largeObject;
    }

    public void setLargeObject(Long largeObject) {
        this.largeObject = largeObject;
    }


}
