package com.sis.mobile;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyCoursesActivity extends AppCompatActivity {

    RecyclerView rv;
    EnrolledCourseAdapter adapter;
    List<Course> enrolled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_courses);

        rv = findViewById(R.id.rvCourses);
        rv.setLayoutManager(new LinearLayoutManager(this));

        enrolled = new ArrayList<>();
        enrolled.add(new Course(1, "Web Development", "HTML + CSS + JS", "2025-01-10", "2025-03-10"));
        enrolled.add(new Course(2, "React Basics", "React fundamentals", "2025-02-01", "2025-04-15"));
        enrolled.add(new Course(3, "Database Design", "SQL + DBMS", "2025-03-05", "2025-05-05"));

        adapter = new EnrolledCourseAdapter(this, enrolled);
        rv.setAdapter(adapter);
    }
}
