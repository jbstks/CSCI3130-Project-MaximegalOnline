package com.example.peter.a3130project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.peter.a3130project.course.Course;
import com.example.peter.a3130project.course.CourseSection;
import com.example.peter.a3130project.course.CourseTime;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** AvailableCoursesActivity
 * Activity for viewing the courses by term.
 *
 * @author Joanna Bistekos
 * @author Peter Lee
 */

public class AvailableCoursesActivity extends AppCompatActivity {

    public static String[] faculties = new String[] {"Business", "Chemistry", "Computer Science", "Mathematics", "Statistics"};
    private CourseRVAdapter courseRVAdapter;
    private Spinner sortByFacultySpinner;

    private final List<Course> allcourses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availcourses);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        courseRVAdapter = new CourseRVAdapter(allcourses);

        Intent termActivityIntent = getIntent();
        Bundle termActivityBundle = termActivityIntent.getExtras();

        if (termActivityBundle != null) {
            String semester = (String) termActivityBundle.get("semester");
            String year = (String) termActivityBundle.get("year");
            setTitle(semester+" "+year);
        }

        final RecyclerView course_rv = findViewById(R.id.course_rv);
        course_rv.setHasFixedSize(false);

        course_rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Available Courses", "course list clicked");
            }
        });

        LinearLayoutManager llm = new LinearLayoutManager(this);
        course_rv.setLayoutManager(llm);
        course_rv.setAdapter(courseRVAdapter);

        getCourses((String) termActivityBundle.get("semester"), (String) termActivityBundle.get("year"));
        // Grabbed from documentation https://developer.android.com/guide/topics/ui/controls/spinner

        sortByFacultySpinner = (Spinner) findViewById(R.id.sortByFacultySpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> sortByFacultyAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, faculties);
        sortByFacultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sortByFacultySpinner.setAdapter(sortByFacultyAdapter);
    }

    /**
     * Inflate the menu; this adds items to the action bar if it is present
     *
     * @param menu
     * @return true if successful
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_term, menu);
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

    // TODO: Adjust for refactored database (will be in MainActivity)
    /**
     * Gets courses from the database
     * This will obtain all of the courses offered in that semester and fill in the course_rv recyclerView
     *
     * @param semester The semester of the year you want course from
     * @param year The year that you want courses from
     */
    public void getCourses(final String semester, final String year) {
        Log.d("COURSE", "Creating Course View\n");

        Log.d("COURSE", "Creating Linear Layout\n");

        // Database setup
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("available_courses1").child(semester + " " + year);

        Log.d("COURSE", "Creating cards\n");

        /**
         * Queries the database and fills in the courses ArrayList
         */
        ValueEventListener courseListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Here is the id of the course (CRN)
                    String key = snapshot.getKey();
                    Log.d("COURSE", "Found course id: " + key);

                    Map<String, Object> values = (Map<String, Object>) dataSnapshot.child(key).getValue();
                    Course course = new Course(key, (String) values.get("name"), (String) values.get("semester"), (String) values.get("year"));

                    Log.d("COURSE", "We are adding values: " + key + " " + (String) values.get("name") + " " + (String) values.get("semester") + " " + (String) values.get("year"));

                    allcourses.add(course);

                    Log.d("COURSE", "courses size is now: " + allcourses.size());

                    //courseRVAdapter.notifyDataSetChanged();
                }

                Log.d("Course", "returning courses");

                Log.d("Course", "finished");

                /*String selectedSubject;
                subjectSpinner = findViewById(R.id.sortByFacultySpinner);
                selectedSubject = subjectSpinner.getItemAtPosition(subjectSpinner.getSelectedItemPosition()).toString();

                ArrayList<Course> sortCourseList = subsort.doSort(allcourses).get(selectedSubject);*/
                courseRVAdapter.setcourses(allcourses);
                courseRVAdapter.notifyDataSetChanged();
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

        Log.d("Course", "Getting Courses Complete");
    }
}
