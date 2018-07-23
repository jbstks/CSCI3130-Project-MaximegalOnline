package com.example.peter.a3130project;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

/**
 * A fragment containing a list of courses
 *
 * @author Joanna Bistekos
 * @author Dawson Wilson
 * @author Peter Lee
 */
public class CourseFragment extends Fragment {

    private CourseRVAdapter courseRVAdapter;
    private Spinner sortByFacultySpinner;

    /**
     * Default constructor
     */
    public CourseFragment() {
    }

    /**
     * Creates a CourseFragment
     *
     * @param courseRVAdapter RecyclerView adapter for the course list
     */
    public CourseFragment(CourseRVAdapter courseRVAdapter) {
        this.courseRVAdapter = courseRVAdapter;
    }

    /**
     * Things to do on create
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return created view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_courses, container, false);
        final RecyclerView course_rv = view.findViewById(R.id.course_rv);
        course_rv.setHasFixedSize(false);

        course_rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("courses", "course list clicked");
            }
        });

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        course_rv.setLayoutManager(llm);
        course_rv.setAdapter(courseRVAdapter);

        // Grabbed from documentation https://developer.android.com/guide/topics/ui/controls/spinner
        String[] faculties = new String[] {"Business", "Chemistry", "Computer Science", "Mathematics", "Statistics"};
        sortByFacultySpinner = (Spinner) view.findViewById(R.id.sortByFacultySpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> sortByFacultyAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, faculties);
        sortByFacultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sortByFacultySpinner.setAdapter(sortByFacultyAdapter);

        return view;
    }
}