package com.csci3130.project.maximegalonline.register;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

/**
 * Class for dropping registered courses
 *
 * @author Aecio Cavalcanti
 * @author Dawson Wilson
 */
public class CourseDrop {

    private DatabaseReference dbRef;
    private FirebaseUser user;

    private Context applicationContext;
    public String B00;

    /**
     * Sets up the database and gets the logged in user when created
     */
    public CourseDrop(Context ac) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        applicationContext = ac;
        dbRef = db.getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }

    /**
     * query for the key containing that crn in the student current courses, remove the key
     */
    public void drop(final String crn) {
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // fetch B#, same as CourseRegistration
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
                    return;
                }

                // dropping the section
                dbRef.child("students").child(B00).child("courses").child("current").orderByValue().equalTo(crn).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()) {
                            Log.d("drop", "notfound");
                            Toast.makeText(applicationContext, "Can't drop course. Not registered.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            
                            Toast.makeText(applicationContext, "Course successfully dropped.", Toast.LENGTH_SHORT).show();
                            decrementCourse(crn);
                        }
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

    /** decrementCourse
     *
     * decreases the enroll count bc someone dropped.
     *
     **/
    public void decrementCourse(String crn) {
        dbRef.child("crn").child(crn).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.child("enrolled").getRef().setValue(dataSnapshot.child("enrolled").getValue(Integer.class) - 1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}