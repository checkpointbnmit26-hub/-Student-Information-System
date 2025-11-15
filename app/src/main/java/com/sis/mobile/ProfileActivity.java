package com.sis.mobile;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView tv = findViewById(R.id.tvProfile);
        String email = getSharedPreferences("SIS_PREF", MODE_PRIVATE).getString("EMAIL", "user");
        tv.setText("Email: " + email + "\nName: Demo Student");
    }
}
