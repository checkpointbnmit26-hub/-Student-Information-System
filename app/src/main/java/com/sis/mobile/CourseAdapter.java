package com.sis.mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private Context context;
    private List<Course> list;
    private OnRequestListener listener;

    public interface OnRequestListener {
        void onRequest(Course course);
    }

    public CourseAdapter(Context context, List<Course> list, OnRequestListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.course_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, int position) {
        Course c = list.get(position);
        holder.name.setText(c.getName());
        holder.desc.setText(c.getDescription());
        holder.start.setText("Starts: " + c.getStartDate());

        holder.btn.setOnClickListener(v -> {
            if (listener != null) listener.onRequest(c);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, desc, start;
        Button btn;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvCourseName);
            desc = itemView.findViewById(R.id.tvCourseDesc);
            start = itemView.findViewById(R.id.tvStartDate);
            btn = itemView.findViewById(R.id.btnRequest);
        }
    }
}
