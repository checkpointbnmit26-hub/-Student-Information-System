package com.sis.mobile;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("/api/v1/auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);
}
