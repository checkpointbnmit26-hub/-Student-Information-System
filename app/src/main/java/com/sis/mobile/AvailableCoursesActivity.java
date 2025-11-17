package com.sis.mobile;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AvailableCoursesActivity extends AppCompatActivity {

    RecyclerView rv;
    CourseAdapter adapter;
    List<Course> courseList;
    EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_courses);

        etSearch = findViewById(R.id.etSearch);
        rv = findViewById(R.id.rvAvailableCourses);
        rv.setLayoutManager(new LinearLayoutManager(this));

        courseList = new ArrayList<>();
        courseList.add(new Course(1, "Web Dev Fundamentals", "Learn HTML CSS JS", "2024-01-15", ""));
        courseList.add(new Course(2, "React Advanced", "Hooks, Context, Optimization", "2024-02-01", ""));
        courseList.add(new Course(3, "Database Design", "SQL + DBMS", "2024-03-10", ""));
        courseList.add(new Course(4, "Node.js Backend", "Express + APIs", "2024-04-01", ""));
        courseList.add(new Course(5, "UI/UX Design", "Design principles", "2024-05-15", ""));

        adapter = new CourseAdapter(this, courseList, course -> showRequestDialog(course));
        rv.setAdapter(adapter);

        etSearch.addTextChangedListener(new android.text.TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }
            @Override public void afterTextChanged(android.text.Editable s) {}
        });
    }

    private void filter(String text) {
        List<Course> filtered = new ArrayList<>();
        for (Course c : courseList) {
            if (c.getName().toLowerCase().contains(text.toLowerCase()) ||
                    c.getDescription().toLowerCase().contains(text.toLowerCase())) {
                filtered.add(c);
            }
        }
        adapter = new CourseAdapter(this, filtered, course -> showRequestDialog(course));
        rv.setAdapter(adapter);
    }

    private void showRequestDialog(Course course) {
        new AlertDialog.Builder(this)
                .setTitle("Request Enrollment")
                .setMessage("Request enrollment for:\n\n" + course.getName())
                .setPositiveButton("Send Request", (dialog, which) ->
                        Toast.makeText(this, "Request sent!", Toast.LENGTH_SHORT).show())
                .setNegativeButton("Cancel", null)
                .show();
    }
}
