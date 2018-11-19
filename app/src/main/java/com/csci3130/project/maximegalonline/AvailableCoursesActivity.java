package com.csci3130.project.maximegalonline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.csci3130.project.maximegalonline.course.Course;
import com.csci3130.project.maximegalonline.subject.SubjectSort;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**  July 24, 2018
 * AvailableCoursesActivity
 * Activity for viewing the courses by term.
 *
 * @author Joanna Bistekos
 * @author Peter Lee
 */

public class AvailableCoursesActivity extends AppCompatActivity {

    public static String[] faculties = new String[] {"Business", "Chemistry", "Computer Science", "Mathematics", "Statistics"};
    public static String[] faccodes = new String[] {"BUSI", "CHEM", "CSCI", "MATH", "STAT"};
    private HashMap<String, String> facmap;
    private CourseRVAdapter courseRVAdapter;
    private Spinner sortByFacultySpinner;
    private SubjectSort subsort;

    private final List<Course> allcourses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availcourses);

        subsort = new SubjectSort(Arrays.asList(faccodes));
        facmap = new HashMap<String, String>();
        facmap.put("Business","BUSI");
        facmap.put("Chemistry","CHEM");
        facmap.put("Computer Science","CSCI");
        facmap.put("Mathematics","MATH");
        facmap.put("Statistics","STAT");
        
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        courseRVAdapter = new CourseRVAdapter(allcourses);

        Intent mainActivityIntent = getIntent();
        Bundle mainActivityBundle = mainActivityIntent.getExtras();

        if (mainActivityBundle != null) {
            String semester = (String) mainActivityBundle.get("semester");
            String year = (String) mainActivityBundle.get("year");
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

        getCourses((String) mainActivityBundle.get("semester"), (String) mainActivityBundle.get("year"));
        
        // Grabbed from documentation https://developer.android.com/guide/topics/ui/controls/spinner
        sortByFacultySpinner = findViewById(R.id.sortByFacultySpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> sortByFacultyAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, faculties);
        sortByFacultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sortByFacultySpinner.setAdapter(sortByFacultyAdapter);

        sortByFacultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Check to see if courses are null first
                if (allcourses == null) return;
                updateSorting();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
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

    /**
     * Gets courses from the database
     * This will obtain all of the courses offered in that semester and fill in the course_rv recyclerView
     *
     * @param inSemester The semester of the year you want course from
     * @param inYear The year that you want courses from
     */
    public void getCourses(final String inSemester, final String inYear) {
        Log.d("COURSE", "Creating Course View for " + inSemester + " " + inYear + "\n");

        Log.d("COURSE", "Creating Linear Layout\n");

        // Database setup
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("available_courses2").child(inSemester + " " + inYear);

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

                    Course course = new Course(key, (String) values.get("name"), inSemester, inYear);

                    Log.d("COURSE", "We are adding values: " + key + " " + values.get("name") + " " +  inSemester + " " + inYear);

                    allcourses.add(course);

                    Log.d("COURSE", "courses size is now: " + allcourses.size());

                    courseRVAdapter.notifyDataSetChanged();
                }
                Log.d("Course", "returning courses");

                Log.d("Course", "finished");
                updateSorting();
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

    /**
     * updateSorting()
     *
     * Updates the courses based on faculty.
     **/
    public void updateSorting() {
        String selectedSubject;
        sortByFacultySpinner = findViewById(R.id.sortByFacultySpinner);
        selectedSubject = facmap.get(sortByFacultySpinner.getItemAtPosition(sortByFacultySpinner.getSelectedItemPosition()).toString());

        ArrayList<Course> sortCourseList = subsort.doSort(allcourses).get(selectedSubject);
        courseRVAdapter.setcourses(sortCourseList);
        courseRVAdapter.notifyDataSetChanged();
    }
}
