package com.sis.mobile;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MyCoursesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_courses);

        TextView tv = findViewById(R.id.tvMyCourses);
        tv.setText(getString(R.string.my_courses));
    }
}
