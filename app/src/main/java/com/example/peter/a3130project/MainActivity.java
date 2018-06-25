package com.example.peter.a3130project;

import android.content.Intent;
import android.support.design.widget.TabLayout;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.widget.TextView;
import android.view.View;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent termActivityIntent = getIntent();
        Bundle termActivityBundle = termActivityIntent.getExtras();

        if (termActivityBundle != null) {
            String semester = (String) termActivityBundle.get("semester");
            String year = (String) termActivityBundle.get("year");
            setTitle(semester+" "+year);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        // TODO this breaks clicking on courses to get detailed information
        // I think it just isn't properly fragmented out, as the implementation was half done
        /*
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        */

        getCourses((String) termActivityBundle.get("semester"), (String) termActivityBundle.get("year"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        //Switch to Register Activity
        if(id == R.id.action_settings2){
            Intent myIntent = new Intent(this, RegisterActivity.class);
            startActivity(myIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
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
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            /*TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));*/
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }

    /** Called when the user taps a course card */
    public void viewCourseDetails(View view) {
        Intent intent = new Intent(this, CourseInfo.class);
        startActivity(intent);
    }

    /**
    ** Called when user presses logout button in menu.
    **/
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


                /* Sample way of querying database data
                // Find all dinosaurs whose height is exactly 25 meters.
                    var ref = firebase.database().ref("dinosaurs");
                    ref.orderByChild("height").equalTo(25).on("child_added", function(snapshot) {
                      console.log(snapshot.key);
                    });
                */

    // TODO query out only the courses that are the correct semester
    // we would query for the information then pass it into the Course Class
    // The CourseTime part takes an arrayList of all the course times for that course

    public void getCourses(final String semester, final String year) {
        Log.d("COURSE", "Creating Course View\n");

        final RecyclerView course_rv = findViewById(R.id.course_rv);
        course_rv.setHasFixedSize(true);

        Log.d("COURSE", "Creating Linear Layout\n");

        LinearLayoutManager llm = new LinearLayoutManager(this);
        course_rv.setLayoutManager(llm);

        //Database setup
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("available_courses");

        Log.d("COURSE", "Creating cards\n");

        final List<Course> courses = new ArrayList<>();

        // Read from the database
        ValueEventListener courseListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    // Here is the id of the course (CRN)
                    String key = snapshot.getKey();
                    Log.d("COURSE", "Found course id: " + key );
                    // Object value = snapshot.getValue();

                    Map<String, Object> values = (Map<String, Object>) dataSnapshot.child(key).getValue();

                    List<CourseTime> courseTimes = new ArrayList<>();

                    // Populate course times
                    for (DataSnapshot snapshotCourseTime : dataSnapshot.child(key).child("times").getChildren()) {
                        String timeKey = snapshotCourseTime.getKey();
                        Log.d("COURSE", "[" + key + "] found course day: " + timeKey );
                        Map<String, Object> dataCourseTimes = (Map<String, Object>) dataSnapshot.child(key).child("times").child(timeKey).getValue();

                        CourseTime time = new CourseTime(timeKey, (String) dataCourseTimes.get("start"), (String) dataCourseTimes.get("end"), (String) dataCourseTimes.get("loc"));

                        Log.d("COURSE", "[" + key + "]" + "Found time: " + timeKey + " " + (String) dataCourseTimes.get("start") + " " + (String) dataCourseTimes.get("end") + " " + (String) dataCourseTimes.get("loc"));

                        courseTimes.add(time);
                    }

                    Log.d("COURSE", "We have values" + key + (String) values.get("course") + (String) values.get("prof") + (String) values.get("semester") + (String) values.get("year"));

                    // Call the course constructor will all the values we have here
                    Course course = new Course(key, (String) values.get("name"), (String) values.get("course"), (String) values.get("prof"), (String) values.get("semester"), (String) values.get("year"), courseTimes);

                    if (course.semester.equalsIgnoreCase(semester) && course.year.equalsIgnoreCase(year)) {
                        courses.add(course);
                    }

                    Log.d("COURSE", "courses size is now: " + courses.size());

                }
                Log.d("Course", "returning courses");

                CourseRVAdapter mCourseAdapter = new CourseRVAdapter(courses);
                course_rv.setAdapter(mCourseAdapter);

                Log.d("Course", "finished");

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("MainActivity", "Failed to read value.", error.toException());
            }
        };
        Log.d("Course", "listener init complete");
        myRef.addListenerForSingleValueEvent(courseListener);
        //myRef.removeEventListener(courseListener);

        Log.d("Course", "returning courses");

        Log.d("Course", "finished");

    }

}
