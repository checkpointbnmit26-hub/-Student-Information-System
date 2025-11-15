package com.sis.mobile;

public class LoginResponse {
    private String token;
    private String role;
    private long userId;
    private String email;

    public LoginResponse() {}

    public String getToken() { return token; }
    public String getRole() { return role; }
    public long getUserId() { return userId; }
    public String getEmail() { return email; }

    public void setToken(String token) { this.token = token; }
    public void setRole(String role) { this.role = role; }
    public void setUserId(long userId) { this.userId = userId; }
    public void setEmail(String email) { this.email = email; }
}
