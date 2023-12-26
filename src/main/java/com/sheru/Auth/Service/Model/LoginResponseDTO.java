package com.sheru.Auth.Service.Model;

public class LoginResponseDTO {
    private User user;
    private String jwt;


    public LoginResponseDTO(User user, String jwt) {
        this.user = user;
        this.jwt = jwt;
    }

    public LoginResponseDTO() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
