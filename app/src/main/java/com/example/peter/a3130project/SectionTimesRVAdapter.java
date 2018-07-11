package com.example.peter.a3130project;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
/** This class functions as a bridge between the  section times data and the RecyclerView
 * that displays it
 *
 *
 */

import com.example.peter.a3130project.course.CourseTime;

/** SectionTimesRVAdapter
 *
 * Recycler view for section times in calendar
 *
 * @author Dawson Wilson
 * @author Joanna Bistekos
 */
public class SectionTimesRVAdapter extends RecyclerView.Adapter<SectionTimesRVAdapter.SectionTimesViewHolder> {

    List<CourseTime> times;

    /** Constructor
     *
     * @param times A list containing CourseTime objects
     */
    SectionTimesRVAdapter(List<CourseTime> times) {
        this.times = times;
    }

    /**
     * Assigns data from a CourseTime object in the list to the corresponding UI elements
     * of the RecyclerView entry
     */
    public static class SectionTimesViewHolder extends RecyclerView.ViewHolder {
        TextView times_day;
        TextView times_start;
        TextView times_end;
        TextView times_location;

        List<CourseTime> times;

        /**
         * Constructor
         *
         * @param itemView each item/"row" on the RecyclerView list
         * @param times list containing CourseTime objects
         */
        SectionTimesViewHolder(View itemView, final List<CourseTime> times) {
            super(itemView);
            this.times = times;
            times_day = itemView.findViewById(R.id.section_times_day);
            times_start = itemView.findViewById(R.id.section_times_start);
            times_end = itemView.findViewById(R.id.section_times_end);
            times_location = itemView.findViewById(R.id.section_times_location);

            // TODO: maybe we do a pop up and ask if they want to register?
        }
    }

    /**
     * Creates and returns a SectionViewHolder to be used by onBindViewHolder
     *
     * @param viewGroup
     * @param i
     * @return a SectionViewHolder
     */
    @Override
    public SectionTimesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.d("RV", "Creating card");
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.section_times_list, viewGroup, false);
        SectionTimesViewHolder pvh = new SectionTimesViewHolder(v, this.times);
        return pvh;
    }


    /**
     * This method is called by RecyclerView to display the data at the specified position
     * it assigns the data from a CourseTime object to the fields on the corresponding RecyclerView
     * row
     *
     * @param sectionViewHolder
     * @param i the position of a item on the times list
     */
    @Override
    public void onBindViewHolder(SectionTimesViewHolder sectionViewHolder, int i) {
        // This refers to the public class Courses
        Log.d("TIMES", "Section will have these values: " + times.get(i).getday() + " " + times.get(i).getstartTime() + " " + times.get(i).getendTime() + " " + times.get(i).getlocation());
        sectionViewHolder.times_day.setText(times.get(i).getday());
        sectionViewHolder.times_start.setText(times.get(i).getstartTime());
        sectionViewHolder.times_end.setText(times.get(i).getendTime());
        sectionViewHolder.times_location.setText(times.get(i).getlocation());
    }

    /**
     * Gets item count
     *
     * @return number of items on the RecyclerView
     */
    @Override
    public int getItemCount() {
        return times.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}




