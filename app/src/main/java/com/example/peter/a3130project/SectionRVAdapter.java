package com.example.peter.a3130project;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/** This class functions as a bridge between the course section data and the RecyclerView
 * that displays it
 *
 *
 */

public class SectionRVAdapter extends RecyclerView.Adapter<SectionRVAdapter.SectionViewHolder>{

    List<CourseSection> sections;

    /** Constructor
     *
     * @param sections A list containing CourseSection objects
     */
    SectionRVAdapter(List<CourseSection> sections) {
        this.sections = sections;
    }


    /** Assigns data from a CourseSection object in the list  to UI elements of an item in the RecyclerView
     * for display.
     *
     */
    public static class SectionViewHolder extends RecyclerView.ViewHolder {
        TextView section_crn;
        TextView section_prof;
        TextView section_id;
        Button register_button;
        RecyclerView times_rv;

        List<CourseSection> sections;

        /** Constructor
         *
         * @param itemView each item/"row" on the RecyclerView list
         * @param sections A list containing CourseSection objects
         */
        SectionViewHolder(View itemView, final List<CourseSection> sections) {
            super(itemView);
            this.sections = sections;
            section_crn = itemView.findViewById(R.id.section_crn);
            section_prof = itemView.findViewById(R.id.section_professor);
            section_id = itemView.findViewById(R.id.section_id);
            register_button = itemView.findViewById(R.id.register_button);
            times_rv=  (RecyclerView) itemView.findViewById(R.id.section_times_rv);

            register_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Here we would make a call to the database to register for a course
                    int position = getAdapterPosition();
                    Log.d("SECTION", "REGISTER BUTTON CLICKED for " + sections.get(position).getsectionNum());
                }
            });


        }
    }

    /** Creates and returns a SectionViewHolder to be used by onBindViewHolder
     *
     * @param viewGroup
     * @param i
     * @return a SectionViewHolder
     */
    @Override
    public SectionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.d("RV", "Creating card");
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.section_list, viewGroup, false);
        SectionViewHolder pvh = new SectionViewHolder(v, this.sections);
        return pvh;
    }

    /** This method is called by RecyclerView to display the data at the specified position
     * it assigns the data from a CourseSection object to the fields on the corresponding RecyclerView
     * row
     * @param sectionViewHolder
     * @param i the position of a item on the sections list
     */
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

    /**
     *
     * @return number of itens on the RecyclerView
     */
    @Override
    public int getItemCount() {
        return sections.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }






}
