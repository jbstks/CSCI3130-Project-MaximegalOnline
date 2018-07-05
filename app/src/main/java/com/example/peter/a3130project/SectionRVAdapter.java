package com.example.peter.a3130project;

import android.app.Application;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import static java.security.AccessController.getContext;

import com.example.peter.a3130project.course.CourseSection;

/**
 * @class SectionRVAdapter
 * @author DW
 * @author AC
 * @author PL
 * @author MG
 * Adapter for handling view for courses.
 **/

public class SectionRVAdapter extends RecyclerView.Adapter<SectionRVAdapter.SectionViewHolder>{

    List<CourseSection> sections;

    // constructor
    SectionRVAdapter(List<CourseSection> sections) {
        this.sections = sections;
        applicationContext = null; //TODO: change this

    }

    // provides a way to access data
    public static class SectionViewHolder extends RecyclerView.ViewHolder {
        TextView section_crn;
        TextView section_prof;
        TextView section_id;
        Button register_button;
        RecyclerView times_rv;
        List<CourseSection> sections;
        
        SectionViewHolder(View itemView, final List<CourseSection> sections) {
            super(itemView);
            this.sections = sections;
            section_crn = itemView.findViewById(R.id.section_crn);
            section_prof = itemView.findViewById(R.id.section_professor);
            section_id = itemView.findViewById(R.id.section_id);
            register_button = itemView.findViewById(R.id.register_button);
            times_rv=  (RecyclerView) itemView.findViewById(R.id.section_times_rv);


            
            register_button.setOnClickListener(new View.OnClickListener() {

                    /**
                     * manager for clicking on register button 
                     **/
                @Override
                public void onClick(View v) {
                    // Here we would make a call to the database to register for a course
                    
                    int position = getAdapterPosition();
                    //Update current CourseRegistrationUI

                    CourseRegistrationUI coursereg = new CourseRegistrationUI(null); //TODO change null pointer here
                    
                    CourseSection cs = sections.get(position);
                    
                    ArrayList<CourseSection> register_result = attempt_register(cs);
                    if (register_result == null ){ //TODO: define applicationContext
                        Toast.makeText(applicationContext, "Can't register. Course is already registered for you.",
                                        Toast.LENGTH_SHORT).show();   
                    }
                    
                    else if (register_result.size()!=0 ){  //There is a conflicting course. Mention the conflicting course in the output.
                        StringBuilder outputmessage =new StringBuilder("Can't register. Course conflicts with ");
                        for (int i=0;i< register_result.size();i++) {
                            outputmessage.append(" "+register_result.get(i).getcrn());
                            
                        }
                        
                        Toast.makeText(applicationContext, outputmessage.toString(),
                                       Toast.LENGTH_SHORT).show();
                    }
                    
                    else { //Otherwise, register
                        do_register(cs);
                    }
                    
                    Log.d("SECTION", "REGISTER BUTTON CLICKED for " + sections.get(position).getsectionNum());
                }
            });


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

        //Get the context from the view in the constructor
        LinearLayoutManager layoutManager = new LinearLayoutManager(sectionViewHolder.itemView.getContext());
        sectionViewHolder.times_rv.setLayoutManager(layoutManager);

        //Setting up the inner recycler view
        SectionTimesRVAdapter adapter=new SectionTimesRVAdapter(sections.get(i).getcourseTimeList());
        sectionViewHolder.times_rv.setAdapter(adapter);
        sectionViewHolder.times_rv.setHasFixedSize(true);

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
