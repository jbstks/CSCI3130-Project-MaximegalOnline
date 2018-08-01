package com.example.peter.a3130project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.peter.a3130project.course.CourseCrnList;
import com.example.peter.a3130project.course.CourseSection;
import com.example.peter.a3130project.course.CourseTime;
import com.example.peter.a3130project.subject.SubjectSort;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * MainActivity class
 *
 * @author Joanna Bistekos
 * @author Dawson Wilson
 * @author Aecio Cavalcanti
 * @author Peter Lee
 */

import com.example.peter.a3130project.course.Course;

/** MainActivity
 *
 * The main activity for the app
 *
 * @author Peter Lee
 * @author Megan Gosse
 * @author Aecio Cavalcanti
 * @author Dawson Wilson
 * @author Bradley Garagan
 * @author Joanna Bistekos
 */
public class MainActivity extends AppCompatActivity implements Serializable{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
     * sections. We use a {@link FragmentPagerAdapter} derivative, which will keep every loaded
     * fragment in memory. If this becomes too memory intensive, it may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private PagerAdapter mPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private String semester;
    private String year;
    private final List<Course> registeredCourses = new ArrayList<>();
    private final List<Course> allcourses = new ArrayList<>();
    private final List<CourseSection> courseList = new ArrayList<>();
    private CourseRVAdapter courseRVAdapter;
    private ScheduleFragment scheduleFragment;

    private ArrayList<String> registeredCRNs;
    private HashSet<String> setCRN;
    private ArrayList<CourseSection> currentCourseSections;
    private Context applicationContext;
    private CourseSection coursesection;
    private String B00;
    private FirebaseUser user;
    private int i;

    private SubjectSort subsort;
    private Spinner subjectSpinner;
    private ArrayList<String> subjects;
    /**
     * Things to be done on activity creation
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        courseRVAdapter = new CourseRVAdapter(registeredCourses);

        Intent termActivityIntent = getIntent();
        Bundle termActivityBundle = termActivityIntent.getExtras();

        if (termActivityBundle != null) {
            semester = (String) termActivityBundle.get("semester");
            year = (String) termActivityBundle.get("year");
            setTitle(semester+" "+year);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //getCourses(semester, year);
        getRegisteredCourses(semester, year);
        // make subject sorting object
        subjects = new ArrayList<String>(Arrays.asList(CourseFragment.faculties));
        subsort = new SubjectSort(subjects);
        subjectSpinner = findViewById(R.id.sortByFacultySpinner);
    }

    /**
     * Inflate the menu; this adds items to the action bar if it is present
     *
     * @param menu
     * @return true if successful
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    /**
     * Handle action bar item clicks here. The action bar will automatically handle clicks on
     * the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
     *
     * @param item
     * @return MenuItem selected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class PagerAdapter extends FragmentStatePagerAdapter {

        /**
         * Constructor for PagerAdapter
         *
         * @param fm FragmentManager
         */
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * getItem is called to instantiate the fragment for the given page
         *
         * @param position the selected tab
         * @return the fragment corresponding to the selected tab
         */
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    CourseFragment courseTab = new CourseFragment(courseRVAdapter);
                    return courseTab;
                case 1:
                    scheduleFragment = new ScheduleFragment();
                    // TODO should this be the courseList or currentCourseList?
                    scheduleFragment.update(courseList);
                    return scheduleFragment;
                default:
                    return null;
            }
        }

        /**
         * Gets the number of tabs
         *
         * @return 2, the number of tabs
         */
        @Override
        public int getCount() {
            return 2;
        }
    }


    /** viewCourseDetails
     * Called when the user taps a course card
     *
     * @param view
     */
    public void viewCourseDetails(View view) {
        Intent intent = new Intent(this, CourseInfo.class);
        startActivity(intent);
    }

    /** viewAvailableCourses
     * Called when the user taps the register button
     *
     * @param view
     */
    public void viewAvailableCourses(View view) {
        Intent intent = new Intent(this, AvailableCoursesActivity.class);
        Log.d("IntentCall", "semester: " + semester + " and year: " + year);
        intent.putExtra("semester", semester);
        intent.putExtra("year", year);
        startActivity(intent);
    }

    /** logOut
     * Called when user presses logout button in menu.
     *
     * @param item logout button in the menu
     * @return whether or not the log out was successful
     */
    public boolean logOut(MenuItem item){
        try {
            FirebaseAuth.getInstance().signOut();
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        return true;
    }

    /**
     * Gets courses as CourseSections and put them in a list, from the database
     * Modified from {@link com.example.peter.a3130project.register.CourseRegistrationUI}'s
     * {@code firebaseRegister()} function
     *
     * @param inSemester selected semester month
     * @param inYear     selected semester year
     */
    public void getRegisteredCourses(final String inSemester, final String inYear) {
        // Database setup
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef  = database.getReference();

        Log.d("REGISTEREDCOURSES", "we called the function\n");

        // Read from the database
        final ValueEventListener courseSectionListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                B00 = null;

                for (DataSnapshot bentry : dataSnapshot.child("students").getChildren()) {
                    Log.d("REGISTEREDCOURSES", "going through list of students");

                    String Bcand = bentry.getKey();
                    Log.d("REGISTEREDCOURSES", "current student b00: " + Bcand);

                    // if this student matches the currently logged in user
                    if (bentry.child("email").getValue(String.class).equals(email)) {
                        Log.d("REGISTEREDCOURSES", "woohoo we found a match!");
                        B00 = Bcand;
                        break;
                    }
                }
                if (B00 == null) {
                    // student doesn't exist
                    return;
                }

                registeredCRNs = new ArrayList<>();
                // Getting the CRNs for courses a user has already enrolled in
                for (DataSnapshot snapshot : dataSnapshot.child("students").child(B00).child("courses").child("current").getChildren()) {
                    Log.d("REGISTEREDCOURSES", "user is registered for this course: " + snapshot.getValue(String.class));
                    registeredCRNs.add(snapshot.getValue(String.class));
                }

                i = 0;
                // get courseSection info from CRNs
                currentCourseSections = new ArrayList<>();
                for (i = 0; i < registeredCRNs.size(); i++) {
                    String crn = registeredCRNs.get(i);
                    Log.d("REGISTEREDCOURSES", "Looking up the crn" + " " + crn );
                    String professor = dataSnapshot.child("crn").child(crn).child("professor").getValue(String.class);
                    String sectionNum = dataSnapshot.child("crn").child(crn).child("section").getValue(String.class);
                    List<CourseTime> courseTimeList = new ArrayList<>();

                    Log.d("REGISTEREDCOURSES", "going into times with " + crn + " " + professor + " " + sectionNum);

                    for(DataSnapshot time : dataSnapshot.child("crn").child(crn).child("times").getChildren()) {
                        String day = time.getKey();
                        String start = time.child("start").getValue(String.class);
                        String end = time.child("end").getValue(String.class);
                        String location = time.child("location").getValue(String.class);
                        Log.d("REGISTEREDCOURSES", "Found values " + day + " " + start + " " + end + " " + location);
                        CourseTime courseTime = new CourseTime(day, start, end, location);
                        courseTimeList.add(courseTime);
                    }

                    //get Course info
                    String code = dataSnapshot.child("crn").child(crn).child("code").getValue(String.class);
                    String name = dataSnapshot.child("crn").child(crn).child("name").getValue(String.class);
                    String semester = dataSnapshot.child("crn").child(crn).child("semester").getValue(String.class);
                    String year = dataSnapshot.child("crn").child(crn).child("year").getValue(String.class);
                    int capacity = dataSnapshot.child("crn").child(crn).child("capacity").getValue(Integer.class);
                    int enrolled = dataSnapshot.child("crn").child(crn).child("enrolled").getValue(Integer.class);
                    Course course = new Course(code, name, semester, year);

                    if (year.equalsIgnoreCase(inYear) && semester.equalsIgnoreCase(inSemester)) {
                        Log.d("REGISTEREDCOURSES", "This course " + crn + " was found to be in this semester");
                        CourseSection section = new CourseSection(enrolled, capacity, sectionNum, crn, professor, course, courseTimeList);
                        currentCourseSections.add(section);
                        registeredCourses.add(course);
                        courseRVAdapter.setcourses(registeredCourses);
                        courseRVAdapter.notifyDataSetChanged();
                        if (scheduleFragment != null) scheduleFragment.update(currentCourseSections);
                    }
                }
            }
            /**
             * Prints error if there was a problem getting data from the database
             *
             * @param error
             */
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("MainActivity", "Failed to read value.", error.toException());
            }
        };

        myRef.addListenerForSingleValueEvent(courseSectionListener);
    }

    /**
     * changelistener for spinner. Changes sorting by faculty/subject
     **/
    public void changeSubjectSpinner(View view) {
        String selectedSubject;
        subjectSpinner = findViewById(R.id.sortByFacultySpinner);
        selectedSubject = subjectSpinner.getItemAtPosition(subjectSpinner.getSelectedItemPosition()).toString();

        ArrayList<Course> sortCourseList = subsort.doSort(allcourses).get(selectedSubject);
    }
}
