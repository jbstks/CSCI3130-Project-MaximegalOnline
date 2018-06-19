package com.example.peter.a3130project;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        // All of the text view fields
        // TODO
        TextView course_code;
        TextView course_name;
        TextView course_id;

        CourseViewHolder(View itemView) {
            super(itemView);
            course_cv = itemView.findViewById(R.id.course_cv);
            // TODO
            course_code = itemView.findViewById(R.id.course_code);
            course_name = itemView.findViewById(R.id.course_name);
            course_id = itemView.findViewById(R.id.course_id);

            Log.d("Debug","Entering click function");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), CourseInfo.class);
                    v.getContext().startActivity(intent);

                    /*
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    String semester = term_semester.getText().toString();
                    String year = term_year.getText().toString();

                    Log.d("Debug","Trying with " + semester + " and " + year + " .");

                    intent.putExtra("semester", semester);
                    intent.putExtra("year", year);
                    v.getContext().startActivity(intent);
                    */
                }
            });
        }

    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.d("RV", "Creating card");
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_card_list, viewGroup, false);
        CourseViewHolder pvh = new CourseViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(CourseViewHolder courseViewHolder, int i) {
        // This refers to the public class Courses
        courseViewHolder.course_code.setText(courses.get(i).code);
        courseViewHolder.course_name.setText(courses.get(i).name);
        courseViewHolder.course_id.setText(courses.get(i).id);
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