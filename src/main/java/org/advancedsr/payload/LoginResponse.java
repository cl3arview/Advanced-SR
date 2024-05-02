package org.advancedsr.payload;

import java.time.Instant;

public class LoginResponse {
    private String token;
    private String roleName;
    private String username;
    private Instant tokenExpiration;

    public LoginResponse(String username, String roleName, String token,Instant tokenExpiration) {

        this.username = username;
        this.roleName = roleName;
        this.token = token;
        this.tokenExpiration = tokenExpiration;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Instant getTokenExpiration() {
        return tokenExpiration;
    }

    public void setTokenExpiration(Instant tokenExpiration) {
        this.tokenExpiration = tokenExpiration;
    }
}