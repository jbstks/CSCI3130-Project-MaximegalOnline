package com.example.peter.a3130project;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.Map;
/** Not used, delete!**/

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        populateClasses();
    }

    //Populate the textfields with course names
    private void populateClasses()
    {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("available_courses");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        collectCourses((Map<String,Object>) dataSnapshot.getValue());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });


    }

    private void collectCourses(Map<String,Object> users) {

        ArrayList<String> courseList = new ArrayList<>();

        //iterate through each course
        for (Map.Entry<String, Object> entry : users.entrySet()){

            //Get user map
            Map course = (Map) entry.getValue();
            //Get phone field and append to list
            courseList.add((String) course.get("course"));
        }

        System.out.println(courseList.toString());

        //Bad hardcoding that needs to be made dynamic
        //Should itterate though and create textviews/cardviews
        TextView tv1 = (TextView)findViewById(R.id.textView7);
        TextView tv2 = (TextView)findViewById(R.id.textView9);
        TextView tv3 = (TextView)findViewById(R.id.textView10);

        tv1.setText(courseList.get(0));
        tv2.setText(courseList.get(1));
        tv3.setText(courseList.get(2));


    }
}
