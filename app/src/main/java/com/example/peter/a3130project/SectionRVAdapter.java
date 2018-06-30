package com.example.peter.a3130project;

import android.app.Application;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import static java.security.AccessController.getContext;

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
        RecyclerView times_rv;



        List<CourseSection> sections;

        SectionViewHolder(View itemView, final List<CourseSection> sections) {
            super(itemView);
            this.sections = sections;
            section_crn = itemView.findViewById(R.id.section_crn);
            section_prof = itemView.findViewById(R.id.section_professor);
            section_id = itemView.findViewById(R.id.section_id);
          times_rv=  (RecyclerView) itemView.findViewById(R.id.section_times_rv);




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
        //Setting up the inner recycler view
        SectionTimesRVAdapter adapter=new SectionTimesRVAdapter(sections.get(i).getcourseTimeList());
        sectionViewHolder.times_rv.setAdapter(adapter);
        sectionViewHolder.times_rv.setHasFixedSize(true);
        //I couldn't get this to work:
        //LinearLayoutManager layoutManager = new LinearLayoutManager(context?, LinearLayoutManager.HORIZONTAL, false);
       // sectionViewHolder.times_rv.setLayoutManager(layoutManager);

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
