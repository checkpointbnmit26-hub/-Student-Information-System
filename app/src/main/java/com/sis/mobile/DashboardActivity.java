package com.sis.mobile;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.google.android.material.card.MaterialCardView;

public class DashboardActivity extends AppCompatActivity {
    private TextView tvWelcome, tvEmail;
    private MaterialCardView cardMyCourses, cardAvailable, cardProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tvWelcome = findViewById(R.id.tvWelcome);
        tvEmail = findViewById(R.id.tvEmail);
        cardMyCourses = findViewById(R.id.cardMyCourses);
        cardAvailable = findViewById(R.id.cardAvailable);
        cardProfile = findViewById(R.id.cardProfile);

        String email = getSharedPreferences("SIS_PREF", MODE_PRIVATE).getString("EMAIL", "user");
        tvWelcome.setText("Welcome");
        tvEmail.setText(email);

        cardMyCourses.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, MyCoursesActivity.class)));
        cardAvailable.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, AvailableCoursesActivity.class)));
        cardProfile.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, ProfileActivity.class)));
    }
}
