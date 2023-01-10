package com.anton.fomenko.models;

import java.util.Objects;

public class UserEmails {

    private Long userId;
    private String email;

    public UserEmails() {
    }

    public UserEmails(Long userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEmails that = (UserEmails) o;
        return Objects.equals(userId, that.userId) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, email);
    }

    @Override
    public String toString() {
        return "GetUserEmailsResponse{" +
                "userId=" + userId +
                ", email=" + email +
                '}';
    }
}
