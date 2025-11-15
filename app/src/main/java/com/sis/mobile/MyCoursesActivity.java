package com.sis.mobile;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyCoursesActivity extends AppCompatActivity {
    RecyclerView rvCourses;
    CourseAdapter adapter;
    List<Course> courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_courses);

        rvCourses = findViewById(R.id.rvCourses);
        rvCourses.setLayoutManager(new LinearLayoutManager(this));

        // Dummy data (from portal)
        courses = new ArrayList<>();
        courses.add(new Course(1, "Web Development Fundamentals", "Learn HTML, CSS, and JavaScript basics", "2024-01-15", "2024-04-15"));
        courses.add(new Course(2, "React Advanced Patterns", "Master React hooks and performance", "2024-02-01", "2024-05-01"));
        courses.add(new Course(3, "Database Design", "Learn SQL and database architecture", "2024-03-10", "2024-06-10"));

        adapter = new CourseAdapter(this, courses, new CourseAdapter.OnRequestListener() {
            @Override
            public void onRequest(Course course) {
                // For enrolled courses maybe show details
                new AlertDialog.Builder(MyCoursesActivity.this)
                        .setTitle(course.getName())
                        .setMessage(course.getDescription())
                        .setPositiveButton("OK", null)
                        .show();
            }

            @Override
            public void onEdit(Course course) { }

            @Override
            public void onDelete(Course course) { }
        });

        rvCourses.setAdapter(adapter);
    }
}
