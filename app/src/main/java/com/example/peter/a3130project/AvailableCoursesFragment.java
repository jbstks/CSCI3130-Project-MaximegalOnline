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

/**
 * A fragment containing a list of courses
 * A duplicate of CourseFragment that instead shows all courses instead of the courses a user has
 * registered for
 *
 * @author Joanna Bistekos
 * @author Dawson Wilson
 * @author Peter Lee
 */
public class AvailableCoursesFragment extends Fragment {
    public static String[] faculties = new String[] {"Business", "Chemistry", "Computer Science", "Mathematics", "Statistics"};
    private CourseRVAdapter courseRVAdapter;
    private Spinner sortByFacultySpinner;

    /**
     * Default constructor
     */
    public AvailableCoursesFragment() {
    }

    /**
     * Creates a CourseFragment
     *
     * @param courseRVAdapter RecyclerView adapter for the course list
     */
    public AvailableCoursesFragment(CourseRVAdapter courseRVAdapter) {
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

        View view = inflater.inflate(R.layout.fragment_availcourses, container, false);
        return view;
    }
}
