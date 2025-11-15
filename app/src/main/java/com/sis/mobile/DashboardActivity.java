package com.sis.mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class DashboardActivity extends AppCompatActivity {
    TextView tvWelcome;
    CardView cardProfile, cardMyCourses, cardRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tvWelcome = findViewById(R.id.tvWelcome);
        cardProfile = findViewById(R.id.cardProfile);
        cardMyCourses = findViewById(R.id.cardMyCourses);
        cardRequest = findViewById(R.id.cardRequest);

        // get name from SharedPreferences or intent
        String name = getSharedPreferences("SIS_PREF", MODE_PRIVATE).getString("NAME", null);
        if (name == null) {
            name = getIntent().getStringExtra("userName");
        }
        if (name == null) name = "Student";
        tvWelcome.setText("Welcome, " + name + "!");

        cardProfile.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, ProfileActivity.class)));
        cardMyCourses.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, MyCoursesActivity.class)));
        cardRequest.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, AvailableCoursesActivity.class)));
    }
}
