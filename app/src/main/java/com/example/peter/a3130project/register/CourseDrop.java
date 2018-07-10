package com.example.peter.a3130project.register;

import android.support.annotation.NonNull;
import android.util.Log;


import com.example.peter.a3130project.course.CourseSection;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;



public class CourseDrop {


    private DatabaseReference dbRef;
    private FirebaseUser user;

    public String B00;


    public CourseDrop() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        dbRef = db.getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();


    }

    //query for the key containing that crn in the student current courses, remove the key
    public void drop(final String crn) {


        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //fetch B#, same as   CourseRegistration
                String email = user.getEmail();
                B00 = null;
                int index = 0;
                for (DataSnapshot bentry : dataSnapshot.child("students").getChildren()) {
                    String Bcand = bentry.getKey();

                    if (bentry.child("email").getValue(String.class).equals(email)) {

                        B00 = Bcand;
                        Log.d("B#", B00);
                        break;
                    }

                }
                if (B00 == null) {
                    // Toast.makeText(applicationContext, "Can't register. Not logged in.", Toast.LENGTH_SHORT).show();
                    return;
                }

                //dropping the section
                dbRef.child("students").child(B00).child("courses").child("current").orderByValue().equalTo(crn).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot postsnapshot : dataSnapshot.getChildren()) {

                            postsnapshot.getRef().removeValue();



                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }


                });


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
}
