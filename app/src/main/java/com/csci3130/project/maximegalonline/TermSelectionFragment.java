package com.csci3130.project.maximegalonline;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * TermSelectionFragment
 *
 * Fragment holding list of terms
 *
 * @author Joanna Bistekos
 * @author Dawson Wilson
 */
public class TermSelectionFragment extends Fragment {

    /**
     * Default constructor
     */
    public TermSelectionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_term, container, false);
        return view;
    }
}