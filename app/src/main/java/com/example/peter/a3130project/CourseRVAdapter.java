package com.example.peter.a3130project;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CourseRVAdapter extends RecyclerView.Adapter<CourseRVAdapter.CourseViewHolder>{

    List<Course> courses;

    // constructor
    CourseRVAdapter(List<Course> courses) {
        this.courses = courses;
    }

    // provides a way to access data
    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        CardView course_cv;
        TextView course_code;
        TextView course_name;

        List<Course> courses = new ArrayList<>();

        CourseViewHolder(View itemView, final List<Course> courses) {
            super(itemView);
            this.courses = courses;
            course_cv = itemView.findViewById(R.id.course_cv);
            course_code = itemView.findViewById(R.id.course_code);
            course_name = itemView.findViewById(R.id.course_name);
            // itemView.setOnClickListener(this);

            Log.d("Debug","Creating click function");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), CourseInfo.class);
                    int position = getAdapterPosition();

                    Course intentCourse = courses.get(position);
                    String name = intentCourse.name;
                    String code = intentCourse.code;
                    String semester = intentCourse.semester;
                    String year = intentCourse.year;

                    intent.putExtra("name", name);
                    intent.putExtra("code", code);
                    intent.putExtra("semester", semester);
                    intent.putExtra("year", year);

                    Log.d("COURSE", "Course click found" + code);

                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.d("RV", "Creating card");
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_card_list, viewGroup, false);
        CourseViewHolder pvh = new CourseViewHolder(v, this.courses);
        return pvh;
    }

    @Override
    public void onBindViewHolder(CourseViewHolder courseViewHolder, int i) {
        // This refers to the public class Courses
        Log.d("COURSE","Card will have these values: " + courses.get(i).code + " " + courses.get(i).name);
        courseViewHolder.course_code.setText(courses.get(i).code);
        courseViewHolder.course_name.setText(courses.get(i).name);
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }



}