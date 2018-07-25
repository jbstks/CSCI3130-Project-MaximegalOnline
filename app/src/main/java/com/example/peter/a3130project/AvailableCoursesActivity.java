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

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_availcourses);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final RecyclerView course_rv = findViewById(R.id.course_rv);
        course_rv.setHasFixedSize(false);

        course_rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("courses", "course list clicked");
            }
        });

        LinearLayoutManager llm = new LinearLayoutManager(this);
        course_rv.setLayoutManager(llm);
        course_rv.setAdapter(courseRVAdapter);

        // Grabbed from documentation https://developer.android.com/guide/topics/ui/controls/spinner

        sortByFacultySpinner = (Spinner) findViewById(R.id.sortByFacultySpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> sortByFacultyAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, faculties);
        sortByFacultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sortByFacultySpinner.setAdapter(sortByFacultyAdapter);
    }

    /**
     * Modifies the behaviour of the Back button so that it acts like the home button
     * */
    @Override
    public void onBackPressed() {
        Intent pressHome = new Intent(Intent.ACTION_MAIN);
        pressHome.addCategory(Intent.CATEGORY_HOME);
        pressHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(pressHome);
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
}
