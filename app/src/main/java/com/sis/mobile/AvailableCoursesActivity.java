package com.sis.mobile;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AvailableCoursesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_courses);

        TextView tv = findViewById(R.id.tvAvailableCourses);
        tv.setText("No available courses loaded (demo).");
        // Later: call GET /api/v1/courses and show in RecyclerView
    }
}
