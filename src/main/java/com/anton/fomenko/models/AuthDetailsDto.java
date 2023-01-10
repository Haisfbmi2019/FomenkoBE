package com.anton.fomenko.models;

public class AuthDetailsDto {
    private Long userId;
    private String accessToken;
    private final String tokenType;

    public AuthDetailsDto(Long userId, String accessToken) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.tokenType = "Bearer";
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    @Override
    public String toString() {
        return "AuthDetailsDto{" +
                "userId='" + userId + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", tokenType='" + tokenType + '\'' +
                '}';
    }
}
