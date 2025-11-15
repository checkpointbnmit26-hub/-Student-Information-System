package com.sis.mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    public interface OnRequestListener {
        void onRequest(Course course);
        void onEdit(Course course);
        void onDelete(Course course);
    }

    private List<Course> courses;
    private OnRequestListener listener;
    private Context ctx;

    public CourseAdapter(Context ctx, List<Course> courses, OnRequestListener listener) {
        this.courses = courses;
        this.listener = listener;
        this.ctx = ctx;
    }

    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CourseAdapter.ViewHolder holder, int position) {
        Course c = courses.get(position);
        holder.tvName.setText(c.getName());
        holder.tvDesc.setText(c.getDescription());
        holder.tvDate.setText("Starts: " + c.getStartDate());

        holder.btnRequest.setOnClickListener(v -> {
            if (listener != null) listener.onRequest(c);
        });
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDesc, tvDate;
        Button btnRequest;
        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvCourseName);
            tvDesc = itemView.findViewById(R.id.tvCourseDesc);
            tvDate = itemView.findViewById(R.id.tvStartDate);
            btnRequest = itemView.findViewById(R.id.btnRequest);
        }
    }
}
