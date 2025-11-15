package com.sis.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin;
    TextView tvError, tvToggleSignup;

    static class User {
        String email, password, role, name;
        int id;

        User(String e, String p, String r, String n, int i) {
            email = e; password = p; role = r; name = n; id = i;
        }
    }

    List<User> dummyUsers = Arrays.asList(
            new User("student@test.com", "student123", "student", "John Doe", 1),
            new User("admin@test.com", "admin123", "admin", "Admin User", 2)
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvError = findViewById(R.id.tvError);
        tvToggleSignup = findViewById(R.id.tvToggleSignup);

        btnLogin.setOnClickListener(v -> handleLogin());
    }

    private void handleLogin() {
        String email = etEmail.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();

        tvError.setVisibility(View.GONE);

        for (User u : dummyUsers) {
            if (u.email.equals(email) && u.password.equals(pass)) {
                Intent i = new Intent(this, DashboardActivity.class);
                i.putExtra("userName", u.name);
                startActivity(i);
                return;
            }
        }

        tvError.setText("Invalid email or password");
        tvError.setVisibility(View.VISIBLE);
    }
}
