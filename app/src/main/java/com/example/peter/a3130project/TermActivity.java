package com.example.peter.a3130project;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

/** TermActivity
 * Activity for viewing the courses by term.
 *
 * @author Joanna Bistekos
 * @author Dawson Wilson
 */

public class TermActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView rv = findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        List<Term> terms;

        // Populate the RecyclerView with these items
        // These will appear as cards the user can click
        terms = new ArrayList<>();
        terms.add(new Term("Winter","2018"));
        terms.add(new Term("Summer","2018"));
        terms.add(new Term("Fall","2018"));
        terms.add(new Term("Winter","2019"));
        terms.add(new Term("Summer","2019"));
        terms.add(new Term("Fall","2019"));

        TermRVAdapter mAdapter = new TermRVAdapter(terms);
        rv.setAdapter(mAdapter);
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
    * Called when the user taps a course card
    */
    public void viewTermInfo(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra("semester", "Winter");
        intent.putExtra("year", "2018");
        startActivity(intent);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends android.support.v4.app.Fragment {
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
            View rootView = inflater.inflate(R.layout.fragment_term, container, false);
            return rootView;
        }
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
