package com.university.MarketPlace.security.dto;

public class LoginResponse {
    private String token;
    private String expiryTime;

    public LoginResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(String expiryTime) {
        this.expiryTime = expiryTime;
    }

    public LoginResponse(String token, String refreshToken) {
        this.token = token;
        this.expiryTime = refreshToken;
    }
}
