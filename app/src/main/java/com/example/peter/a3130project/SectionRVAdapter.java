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

public class SectionRVAdapter extends RecyclerView.Adapter<SectionRVAdapter.SectionViewHolder>{

    List<CourseSection> sections;

    // constructor
    SectionRVAdapter(List<CourseSection> sections) {
        this.sections = sections;
    }

    // provides a way to access data
    public static class SectionViewHolder extends RecyclerView.ViewHolder {
        TextView section_crn;
        TextView section_prof;
        TextView section_id;

        List<CourseSection> sections;

        SectionViewHolder(View itemView, final List<CourseSection> sections) {
            super(itemView);
            this.sections = sections;
            section_crn = itemView.findViewById(R.id.section_crn);
            section_prof = itemView.findViewById(R.id.section_professor);
            section_id = itemView.findViewById(R.id.section_id);



            // itemView.setOnClickListener(this);

//            Log.d("Debug","Creating click function");
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(v.getContext(), CourseInfo.class);
//                    int position = getAdapterPosition();
//
//                    Course intentCourse = courses.get(position);
//                    String name = intentCourse.getname();
//                    String code = intentCourse.getcode();
//                    String semester = intentCourse.getsemester();
//                    String year = intentCourse.getyear();
//
//                    intent.putExtra("name", name);
//                    intent.putExtra("code", code);
//                    intent.putExtra("semester", semester);
//                    intent.putExtra("year", year);
//
//                    Log.d("COURSE", "Course click found" + code);
//
//                    v.getContext().startActivity(intent);
//                }
//            });
        }
    }

    @Override
    public SectionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.d("RV", "Creating card");
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.section_list, viewGroup, false);
        SectionViewHolder pvh = new SectionViewHolder(v, this.sections);
        return pvh;
    }

    @Override
    public void onBindViewHolder(SectionViewHolder sectionViewHolder, int i) {
        // This refers to the public class Courses
        Log.d("SECTION","Section will have these values: " + sections.get(i).getcrn() + " " + sections.get(i).getprofessor());
        sectionViewHolder.section_crn.setText(sections.get(i).getcrn());
        sectionViewHolder.section_prof.setText(sections.get(i).getprofessor());
        sectionViewHolder.section_id.setText(sections.get(i).getsectionNum());
    }

    @Override
    public int getItemCount() {
        return sections.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }



}
