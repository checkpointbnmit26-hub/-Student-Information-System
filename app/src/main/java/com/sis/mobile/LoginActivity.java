package com.sis.mobile;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText etEmail, etPassword;
    private Button btnLogin;
    private ProgressBar progress;

    // default dev base URL (emulator -> host)
    private String BASE_URL = "http://10.0.2.2:8080";

    // toggle mock mode for frontend demo
    private static final boolean USE_MOCK = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        progress = findViewById(R.id.progress);

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
            String pass = etPassword.getText() != null ? etPassword.getText().toString() : "";
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.setError("Invalid email");
                return;
            }
            if (pass.isEmpty()) {
                etPassword.setError("Password required");
                return;
            }
            doLogin(email, pass);
        });
    }

    private void doLogin(String email, String password) {
        if (USE_MOCK) {
            progress.setVisibility(View.VISIBLE);
            new android.os.Handler().postDelayed(() -> {
                progress.setVisibility(View.GONE);
                getSharedPreferences("SIS_PREF", MODE_PRIVATE)
                        .edit()
                        .putString("TOKEN", "mock-token")
                        .putString("EMAIL", email)
                        .apply();
                startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                finish();
            }, 600);
            return;
        }

        progress.setVisibility(View.VISIBLE);
        AuthService authService = ApiClient.getClient(BASE_URL).create(AuthService.class);
        LoginRequest req = new LoginRequest(email, password);
        authService.login(req).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progress.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse body = response.body();
                    getSharedPreferences("SIS_PREF", MODE_PRIVATE)
                            .edit()
                            .putString("TOKEN", body.getToken())
                            .putLong("USER_ID", body.getUserId())
                            .putString("EMAIL", body.getEmail())
                            .apply();

                    startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                    finish();
                } else {
                    String msg = "Login failed";
                    try {
                        if (response.errorBody() != null) {
                            String err = response.errorBody().string();
                            JSONObject j = new JSONObject(err);
                            msg = j.optString("message", msg);
                        }
                    } catch (Exception e) { e.printStackTrace(); }
                    Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
