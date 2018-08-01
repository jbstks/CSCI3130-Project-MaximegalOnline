package com.example.peter.a3130project.register;
import android.content.Context;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.peter.a3130project.CourseInfo;
import com.example.peter.a3130project.course.CourseSection;
import com.example.peter.a3130project.course.Course;

import com.example.peter.a3130project.course.CourseTime;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

/**
 * Deals confirming registration conflicts given courses and requested course.
 * Updates Firebase if and only if it is a valid addition.
 *
 * @author Peter Lee
 * @author Megan Gosse
 * @author Joanna Bistekos
 */

public class CourseRegistrationUI extends CourseRegistration{

    private DatabaseReference dbRef;
    private ArrayList<String> CRNs;
    private HashSet<String> setCRN;
    private ArrayList<CourseSection> currentCourseSections;
    private ArrayList<String> completeCourseCodes;
    private Context applicationContext;
    private CourseSection coursesection;
    private String B00;
    private FirebaseUser user;

    /**
     * Constructor
     */
    public CourseRegistrationUI(){
        super();
    }

    /**
     * Checks to see if registration is valid, and then registers course in firebase if it is
     *
     * @param fuser holds the information of the user that is logged in
     * @param cs    course section the user is trying to register for
     * @param ac    application context
     */
    public void firebaseRegister(FirebaseUser fuser, CourseSection cs, Context ac) {
        applicationContext = ac;
        coursesection = cs;
        // Fills in current_courses and complete_courses with the firebase instance.
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        user = fuser;
        dbRef = db.getReference();

        final ArrayList<Integer> students = new ArrayList<>(Arrays.asList(0));
        final String crn = cs.getcrn();
        final int capacity = cs.getcapacity();

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String email = user.getEmail();
                int students = 0;
                B00 = null;
                UUID uuid = UUID.randomUUID();
                String index = uuid.toString();
                for (DataSnapshot bentry : dataSnapshot.child("students").getChildren()) {
                    String Bcand = bentry.getKey();

                    for (DataSnapshot course : bentry.child("courses").child("current").getChildren()) {
                        if (((String)course.getValue()).equals(crn)) {
                            students += 1;
                        }
                    }

                    if (bentry.child("email").getValue(String.class).equals(email)) {
                        B00 = Bcand;
                    }
                }

                String fullterm = coursesection.getcourse().getsemester()+ " " + coursesection.getcourse().getyear();

                int enrolled = dataSnapshot.child("crn").child(crn).child("enrolled").getValue(Integer.class);

                if (B00 == null) {
                    Toast.makeText(applicationContext, "Can't register. Not logged in.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (enrolled >= capacity) {
                    Toast.makeText(applicationContext, "Can't register. Course section is full.", Toast.LENGTH_SHORT).show();
                    return;
                }

                CRNs = new ArrayList<>();
                setCRN = new HashSet<>();
                for (DataSnapshot snapshot : dataSnapshot.child("students").child(B00).child("courses").child("current").getChildren()) {
                    String tmp = snapshot.getValue(String.class);
                    if (!setCRN.contains(tmp)) {
                        CRNs.add(tmp);
                        setCRN.add(tmp);
                    }
                }
                //get Codes for complete courses
                completeCourseCodes = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.child("students").child(B00).child("courses").child("complete").getChildren()) {
                    String tmp = snapshot.getValue(String.class);
                    if (!completeCourseCodes.contains(tmp)) {
                        completeCourseCodes.add(tmp);
                    }
                }
                // get courseSection info from CRNs
                currentCourseSections = new ArrayList<>();
                for (DataSnapshot crnSnapshot : dataSnapshot.child("crn").getChildren()) {
                    for (String CRN: CRNs) {
                        if (crnSnapshot.getKey().equals(CRN)) {
                            String sectionNum = crnSnapshot.child("section").getValue(String.class);
                            String prof = crnSnapshot.child("professor").getValue(String.class);

                            Log.d("registration", "Looking up the crn " + CRN);

                            List<CourseTime> courseTimeList = new ArrayList<>();
                            //get CourseTime info
                            for (DataSnapshot timesSnapshot : crnSnapshot.child("times").getChildren()) {
                                String day = timesSnapshot.getKey();
                                String startTime = timesSnapshot.child("start").getValue(String.class);
                                String endTime = timesSnapshot.child("end").getValue(String.class);
                                String location = timesSnapshot.child("location").getValue(String.class);

                                CourseTime courseTime = new CourseTime(day, startTime, endTime, location);
                                courseTimeList.add(courseTime);
                            }

                            //get Course info
                            String code = crnSnapshot.child("code").getValue(String.class);
                            String name = crnSnapshot.child("name").getValue(String.class);
                            String semester = crnSnapshot.child("semester").getValue(String.class);
                            String year = crnSnapshot.child("year").getValue(String.class);
                            int capacity = crnSnapshot.child("capacity").getValue(Integer.class);

                            Log.d("registration", "semester: " + semester + " year: " + year);

                            Course course = new Course(code, name, semester, year);
                            CourseSection section = new CourseSection(enrolled, capacity, sectionNum, CRN, prof, course, courseTimeList);
                            currentCourseSections.add(section);
                        }
                    }
                }

                setcurrent_courses(currentCourseSections);
                setcomplete_courses(completeCourseCodes);
                if (currentCourseSections.size() == 0) {
                    Log.d("registration", "current course is 0 for some reason");
                }

                if (currentCourseSections == null) {
                    Log.d("registration","current course is null");
                    SystemClock.sleep(100);
                }

                /* Get Prerequisites and if required prerequisites are achieved then attempt a register */
                ArrayList<String> prerequisites = new ArrayList<>();
                DataSnapshot prerequisitesSnapshot = dataSnapshot.child("course_listings").child(coursesection.getcourse().getcode()).child("prerequisites");
                if (prerequisitesSnapshot.exists()) {
                    /* Check Prerequisites */
                    for (DataSnapshot prereqChildren : prerequisitesSnapshot.getChildren()) {
                        prerequisites.add(prereqChildren.getValue(String.class));
                    }

                    Log.d("registration", "Started checking prerequisites");
                    ArrayList<String> prereq_result = checkPrerequisites(prerequisites);
                    Log.d("registration", "Finished checking prerequisites");
                    if (prereq_result.size() > 0) {
                        Toast.makeText(applicationContext, "Can't register. You do not have the required prerequisites", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                /* Try registration */
                Log.d("registration","values of sometimes broken if " + coursesection.getcourse().getsemester()
                        + " " + coursesection.getcourse().getyear());
                ArrayList<CourseSection> register_result = attempt_register(coursesection);

                if (register_result == null) {
                    Toast.makeText(applicationContext, "Can't register. Course is already registered for you.", Toast.LENGTH_SHORT).show();
                }
                else if (register_result.size() != 0) {  // There is a conflicting course. Mention the conflicting course in the output.
                    StringBuilder outputmessage = new StringBuilder("Can't register. Course conflicts with ");
                    for (int i = 0; i < register_result.size(); i++) {
                        outputmessage.append(" " + register_result.get(i).getcrn());
                    }

                    Toast.makeText(applicationContext, outputmessage.toString(), Toast.LENGTH_SHORT).show();
                }
                else { //Otherwise, register
                    try {
                        pushRegister(coursesection, index);
                    } catch (RegistrationException e) {
                        Log.d("Register", "Something bad happened on register");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    /** pushRegister
     *-------------
     * @param course_sec:
     *    course that is desired to be registered
     **/
    public void pushRegister(final CourseSection course_sec, String index) throws RegistrationException {

        /* Check to see if the course is valid */
        ArrayList<CourseSection> cs= attempt_register(course_sec);

        /* Check for bad things which shouldn't happen */
        if (cs == null) {
            throw new RegistrationException("Invalid registration state");
        }

        if (cs.size() != 0 ) {
            throw new RegistrationException("Invalid registration state");
        }

        DatabaseReference.CompletionListener onComplete = new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
            if (databaseError == null) {
                String code = course_sec.getcourse().getcode();
                Toast.makeText(applicationContext, "Successfully registered for " + code + ".", Toast.LENGTH_SHORT).show();
            }
            }
        };

        /* Adds the course to the database*/
        dbRef.child("students").child(B00).child("courses").child("current").child(index).setValue(course_sec.getcrn(), onComplete);

        //increments the number of students enrolled at course
        String fullterm = course_sec.getcourse().getsemester()+ " " + course_sec.getcourse().getyear();

        dbRef.child("crn").child(course_sec.getcrn()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.child("enrolled").getRef().setValue(dataSnapshot.child("enrolled").getValue(Integer.class) + 1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}