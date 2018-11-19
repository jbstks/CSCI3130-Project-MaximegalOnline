package com.csci3130.project.maximegalonline;

import com.csci3130.project.maximegalonline.course.Course;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
/** CourseRVAdapter
 * Adapter view for holding course selection
 * 
 * @author Dawson Wilson
 * @author Joanna Bistekos
 * @author Peter Lee
 * @author Megan Gosse
 */
public class CourseRVAdapter extends RecyclerView.Adapter<CourseRVAdapter.CourseViewHolder>{

    private List<Course> courses;

    /**
     * Creates a CourseRVAdapter
     *
     * @param courses list of courses
     */
    CourseRVAdapter(List<Course> courses) {
        this.courses = courses;

        Log.d("COURSERV", "called the CourseRVAdapter");
    }

    /**
     * Provides a way to access data
     */
    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        CardView course_cv;
        TextView course_code;
        TextView course_name;

        CourseRVAdapter adapter;

        CourseViewHolder(View itemView, final CourseRVAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            course_cv = itemView.findViewById(R.id.course_cv);
            course_code = itemView.findViewById(R.id.course_code);
            course_name = itemView.findViewById(R.id.course_name);

            Log.d("COURSERV","Creating click function");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CourseInfo.class);
                int position = getAdapterPosition();

                Course intentCourse = adapter.courses.get(position);
                String name = intentCourse.getname();
                String code = intentCourse.getcode();
                String semester = intentCourse.getsemester();
                String year = intentCourse.getyear();

                intent.putExtra("name", name);
                intent.putExtra("code", code);
                intent.putExtra("semester", semester);
                intent.putExtra("year", year);

                Log.d("COURSERV", "semester: " + semester + " year: " + year);

                Log.d("COURSERV", "Course click found" + code);
                Log.d("COURSERV", "getSimpleName" + v.getContext().getClass().getSimpleName());
                if (v.getContext().getClass().getSimpleName().equals("MainActivity")) intent.putExtra("prevActivity", "Main");
                else intent.putExtra("prevActivity", "AvailCourses");
                v.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.d("COURSE", "Creating card");
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_card_list, viewGroup, false);
        CourseViewHolder pvh = new CourseViewHolder(v, this);
        return pvh;
    }

    @Override
    public void onBindViewHolder(CourseViewHolder courseViewHolder, int i) {
        // This refers to the public class Courses
        Log.d("COURSE","Card will have these values: " + courses.get(i).getcode() + " " + courses.get(i).getname());
        courseViewHolder.course_code.setText(courses.get(i).getcode());
        courseViewHolder.course_name.setText(courses.get(i).getname());
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public List<Course> getcourses(){
        return this.courses;
    }
    public void setcourses(List<Course> val) { this.courses = val; }
}
