package com.example.peter.a3130project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.peter.a3130project.register.CourseDrop;

/**
 * Temporary class to test dropping courses
 *
 * @author Dawson Wilson
 * @author Aecio Cavalcanti
 */
public class DropActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop);
    }

    /**
     * calls drop()
     */
    public void dropButtonClick(View view) {
        EditText editText = findViewById(R.id.crnText);
        String crn = editText.getText().toString();

        CourseDrop cd = new CourseDrop(this.getApplicationContext());
        cd.drop(crn);
    }
}
