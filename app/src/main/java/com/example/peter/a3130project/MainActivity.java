package com.example.peter.a3130project;

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

/**
 * MainActivity class
 *
 * @author Joanna Bistekos
 * @author Dawson Wilson
 * @author Aecio Cavalcanti
 * @author Peter Lee
 */
public class MainActivity extends AppCompatActivity {

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

    private final List<Course> courseList = new ArrayList<>();
    private CourseRVAdapter courseRVAdapter;
    private ScheduleFragment scheduleFragment;

    /**
     * Things to be done on activity creation
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        courseRVAdapter = new CourseRVAdapter(courseList);

        Intent termActivityIntent = getIntent();
        Bundle termActivityBundle = termActivityIntent.getExtras();

        if (termActivityBundle != null) {
            String semester = (String) termActivityBundle.get("semester");
            String year = (String) termActivityBundle.get("year");
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

        getCourses((String) termActivityBundle.get("semester"), (String) termActivityBundle.get("year"));
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
     * @return MenuItem selectied
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

    /**
     * Called when the user taps a course card
     *
     * @param view
     */
    public void viewCourseDetails(View view) {
        Intent intent = new Intent(this, CourseInfo.class);
        startActivity(intent);
    }

    /**
     * Called when user presses logout button in menu.
     *
     * @author Peter Lee
     * @author Aecio Cavalcanti
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

    // TODO: delete commented code
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

    /**
     * Gets courses from the database
     *
     * @author Dawson Wilson
     * @author Joanna Bistekos
     * @param semester selected semester month
     * @param year     selected semester year
     */
    public void getCourses(final String semester, final String year) {
        Log.d("COURSE", "Creating Course View\n");

        Log.d("COURSE", "Creating Linear Layout\n");

        // Database setup
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("available_courses");

        Log.d("COURSE", "Creating cards\n");

        // Read from the database
        final ValueEventListener courseListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    // Here is the id of the course (CRN)
                    String key = snapshot.getKey();
                    Log.d("COURSE", "Found course id: " + key );

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
                        courseList.add(course);
                    }

                    Log.d("COURSE", "courses size is now: " + courseList.size());

                    courseRVAdapter.notifyDataSetChanged();
                    if (scheduleFragment != null)
                        scheduleFragment.update(courseList);

                }
                Log.d("Course", "returning courses");

                Log.d("Course", "finished");
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


        Log.d("Course", "listener init complete");
        myRef.addListenerForSingleValueEvent(courseListener);
        //myRef.removeEventListener(courseListener);

        Log.d("Course", "returning courses");

        Log.d("Course", "finished");
    }
}
