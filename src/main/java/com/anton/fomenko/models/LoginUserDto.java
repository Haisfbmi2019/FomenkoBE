package com.anton.fomenko.models;

import com.sun.istack.NotNull;

public class LoginUserDto {

    @NotNull
    private String email;

    @NotNull
    private String password;

    public LoginUserDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginUserDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
