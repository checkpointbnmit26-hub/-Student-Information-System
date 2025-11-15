package com.sis.mobile;

public class LoginRequest {
    private String email;
    private String password;

    public LoginRequest() { }

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // getters and setters (Retrofit/Gson needs no-arg constructor or public fields)
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
