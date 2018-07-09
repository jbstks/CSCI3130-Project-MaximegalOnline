package com.example.peter.a3130project;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.example.peter.a3130project.course.CourseTime;

public class SectionTimesRVAdapter extends RecyclerView.Adapter<SectionTimesRVAdapter.SectionTimesViewHolder> {

    List<CourseTime> times;

    // constructor
    SectionTimesRVAdapter(List<CourseTime> times) {
        this.times = times;
    }

    // provides a way to access data
    public static class SectionTimesViewHolder extends RecyclerView.ViewHolder {
        TextView times_day;
        TextView times_start;
        TextView times_end;
        TextView times_location;

        List<CourseTime> times;

        SectionTimesViewHolder(View itemView, final List<CourseTime> times) {
            super(itemView);
            this.times = times;
            times_day = itemView.findViewById(R.id.section_times_day);
            times_start = itemView.findViewById(R.id.section_times_start);
            times_end = itemView.findViewById(R.id.section_times_end);
            times_location = itemView.findViewById(R.id.section_times_location);

            // TODO maybe we do a pop up and ask if they want to register?

        }
    }

    @Override
    public SectionTimesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.d("RV", "Creating card");
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.section_times_list, viewGroup, false);
        SectionTimesViewHolder pvh = new SectionTimesViewHolder(v, this.times);
        return pvh;
    }

    @Override
    public void onBindViewHolder(SectionTimesViewHolder sectionViewHolder, int i) {
        // This refers to the public class Courses
        Log.d("TIMES", "Section will have these values: " + times.get(i).getday() + " " + times.get(i).getstartTime() + " " + times.get(i).getendTime() + " " + times.get(i).getlocation());
        sectionViewHolder.times_day.setText(times.get(i).getday());
        sectionViewHolder.times_start.setText(times.get(i).getstartTime());
        sectionViewHolder.times_end.setText(times.get(i).getendTime());
        sectionViewHolder.times_location.setText(times.get(i).getlocation());
    }

    @Override
    public int getItemCount() {
        return times.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}




