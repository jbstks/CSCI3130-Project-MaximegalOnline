package com.example.peter.a3130project;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TermActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView rv = findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        List<Term> terms;

        // Populate the RecyclerView with these items
        // These will appear as cards the user can click
        terms = new ArrayList<>();
        terms.add(new Term("Winter","2018"));
        terms.add(new Term("Summer","2018"));
        terms.add(new Term("Fall","2018"));
        terms.add(new Term("Winter","2019"));
        terms.add(new Term("Summer","2019"));
        terms.add(new Term("Fall","2019"));

        RVAdapter mAdapter = new RVAdapter(terms);
        rv.setAdapter(mAdapter);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    /** Modifies the behaviour of the Back button so that it acts like  the home button  */
    @Override
    public void onBackPressed() {
        Intent pressHome = new Intent(Intent.ACTION_MAIN);
        pressHome.addCategory(Intent.CATEGORY_HOME);
        pressHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(pressHome);

    }

   /** Called when the user taps a course card */
    public void viewTermInfo(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra("semester", "Winter");
        intent.putExtra("year", "2018");
        startActivity(intent);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends android.support.v4.app.Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static TermActivity.PlaceholderFragment newInstance(int sectionNumber) {
            TermActivity.PlaceholderFragment fragment = new TermActivity.PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_term, container, false);
            /*TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));*/
            return rootView;
        }


    }

}
