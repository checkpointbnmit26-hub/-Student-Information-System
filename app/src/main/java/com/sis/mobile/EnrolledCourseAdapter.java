package com.sis.mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EnrolledCourseAdapter extends RecyclerView.Adapter<EnrolledCourseAdapter.ViewHolder> {

    private Context context;
    private List<Course> list;

    public EnrolledCourseAdapter(Context context, List<Course> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public EnrolledCourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.enrolled_course_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EnrolledCourseAdapter.ViewHolder holder, int position) {
        Course c = list.get(position);
        holder.name.setText(c.getName());
        holder.desc.setText(c.getDescription());
        holder.start.setText("Start: " + c.getStartDate());
        holder.end.setText("End: " + (c.getEndDate() == null ? "" : c.getEndDate()));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, desc, start, end;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvCourseNameE);
            desc = itemView.findViewById(R.id.tvCourseDescE);
            start = itemView.findViewById(R.id.tvStartDateE);
            end = itemView.findViewById(R.id.tvEndDateE);
        }
    }
}
