package com.example.peter.a3130project;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * A fragment containing a list of courses
 *
 * @author Joanna Bistekos
 * @author Dawson Wilson
 */
public class CourseFragment extends Fragment {

    private CourseRVAdapter courseRVAdapter;

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

        return view;
    }
}