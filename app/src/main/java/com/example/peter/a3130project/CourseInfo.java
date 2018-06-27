package com.example.peter.a3130project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class CourseInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent termActivityIntent = getIntent();
        Bundle termActivityBundle = termActivityIntent.getExtras();
        Log.d("blab", termActivityBundle.toString());


        if (termActivityBundle != null) {
            // TODO query for data from the database based upon the supplied information code, semester, year

            TextView idTextView = (TextView) findViewById(R.id.courseInfo_id);
            TextView nameTextView = (TextView) findViewById(R.id.courseInfo_name);
            TextView codeTextView = (TextView) findViewById(R.id.courseInfo_code);
            TextView professorTextView = (TextView) findViewById(R.id.courseInfo_professor);

            String id = (String) termActivityBundle.get("id");
            String name=(String) termActivityBundle.get("name");
            String code= (String) termActivityBundle.get("code");
            String professor= (String) termActivityBundle.get("professor");
            String semester = (String) termActivityBundle.get("semester");
            String year = (String) termActivityBundle.get("year");
            getCourseInfo(name, semester, year);


            idTextView.setText(id);
            nameTextView.setText(name);
            codeTextView.setText(code);
            professorTextView.setText(professor);
        }
    }
        /** Queries  data from the database based upon  supplied information name, semester, year */
    public void getCourseInfo(String name, String semester, String year){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef =
                database.getReference("available_courses1").child(semester + " " + year).child(name);

        Query query = myRef.child("sections");

        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot sections : dataSnapshot.getChildren()) {

                            Iterator<DataSnapshot> iterator=  sections.getChildren().iterator();
                            //Iterates through each child of the node "sections"
                            while (iterator.hasNext())
                                //TODO do something with the data
                            Log.d("sections","" + iterator.next().getChildrenCount());

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }}

