package com.sis.mobile;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AvailableCoursesActivity extends AppCompatActivity {
    RecyclerView rv;
    CourseAdapter adapter;
    List<Course> courses;
    EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_courses);

        etSearch = findViewById(R.id.etSearch);
        rv = findViewById(R.id.rvAvailableCourses);
        rv.setLayoutManager(new GridLayoutManager(this, 1));

        courses = new ArrayList<>();
        courses.add(new Course(1, "Web Development Fundamentals", "Learn HTML, CSS, and JavaScript basics", "2024-01-15", ""));
        courses.add(new Course(2, "React Advanced Patterns", "Master React hooks and performance", "2024-02-01", ""));
        courses.add(new Course(3, "Database Design", "Learn SQL and database architecture", "2024-03-10", ""));
        courses.add(new Course(4, "Node.js Backend Development", "Build scalable backend applications with Node.js and Express", "2024-04-01", ""));
        courses.add(new Course(5, "UI/UX Design Principles", "Create beautiful and user-friendly interfaces", "2024-05-15", ""));
        courses.add(new Course(6, "Cloud Computing with AWS", "Deploy and manage applications on AWS", "2024-06-01", ""));

        adapter = new CourseAdapter(this, courses, new CourseAdapter.OnRequestListener() {
            @Override
            public void onRequest(Course course) {
                // Show a simple dialog for request
                showRequestDialog(course);
            }

            @Override
            public void onEdit(Course course) { }

            @Override
            public void onDelete(Course course) { }
        });

        rv.setAdapter(adapter);

        etSearch.addTextChangedListener(new android.text.TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int st, int c, int a){}
            @Override public void onTextChanged(CharSequence s, int st, int b, int c) {
                filter(s.toString());
            }
            @Override public void afterTextChanged(android.text.Editable s){}
        });
    }

    private void filter(String q) {
        List<Course> filtered = new ArrayList<>();
        for (Course c : courses) {
            if (c.getName().toLowerCase().contains(q.toLowerCase()) ||
                    c.getDescription().toLowerCase().contains(q.toLowerCase())) {
                filtered.add(c);
            }
        }
        adapter = new CourseAdapter(this, filtered, new CourseAdapter.OnRequestListener() {
            @Override public void onRequest(Course course) { showRequestDialog(course); }
            @Override public void onEdit(Course course) {}
            @Override public void onDelete(Course course) {}
        });
        rv.setAdapter(adapter);
    }

    private void showRequestDialog(Course course) {
        new AlertDialog.Builder(this)
                .setTitle("Request Enrollment")
                .setMessage("Request enrollment for:\n\n" + course.getName())
                .setPositiveButton("Send Request", (d, w) -> {
                    Toast.makeText(AvailableCoursesActivity.this, "Enrollment request sent", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
