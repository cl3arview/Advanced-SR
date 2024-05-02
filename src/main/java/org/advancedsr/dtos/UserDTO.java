package org.advancedsr.dtos;

public class UserDTO {
    private String username;
    private Boolean actif;
    private String roleName;
    private String password;



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }


    public String getPassword() { // temporary - pre security
        return password;
    }

    public void setPassword(String password) { // temporary - pre security
        this.password = password;
    }
}