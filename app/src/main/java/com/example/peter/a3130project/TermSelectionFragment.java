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
 * A placeholder fragment containing a simple view.
 */
public class TermSelectionFragment extends Fragment {

    public TermSelectionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_term, container, false);

        CardView getCards = (CardView) view.findViewById(R.id.cv);
        getCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), MainActivity.class);
                TextView textViewSemester = view.findViewById(R.id.term_semester);
                String semester = textViewSemester.getText().toString();

                TextView textViewYear = view.findViewById(R.id.term_year);
                String year = textViewYear.getText().toString();

                in.putExtra("semester", semester);
                in.putExtra("year", year);
                startActivity(in);
            }
        });

        return view;
    }

}