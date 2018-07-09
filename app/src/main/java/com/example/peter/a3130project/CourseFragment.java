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
 * A placeholder fragment containing a simple view.
 */
public class CourseFragment extends Fragment {

    private CourseRVAdapter courseRVAdapter;

    public CourseFragment(CourseRVAdapter courseRVAdapter) {
        this.courseRVAdapter = courseRVAdapter;
    }

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