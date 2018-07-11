package com.example.peter.a3130project;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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