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
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TermActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
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

   /** Called when the user taps a course card */
    public void viewTermInfo(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra("semester", "Winter");
        intent.putExtra("year", "2018");
        startActivity(intent);
    }

}
