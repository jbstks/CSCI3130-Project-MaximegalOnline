package com.example.peter.a3130project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class  CourseInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent termActivityIntent = getIntent();
        Bundle termActivityBundle = termActivityIntent.getExtras();

        if (termActivityBundle != null) {
            /*
            String id = (String) termActivityBundle.get("id");
            String name = (String) termActivityBundle.get("name");
            String code = (String) termActivityBundle.get("code");
            String professor = (String) termActivityBundle.get("professor");
            String semester = (String) termActivityBundle.get("semester");
            String year = (String) termActivityBundle.get("year");
            */

            TextView idTextView = (TextView) findViewById(R.id.courseInfo_id);
            TextView nameTextView = (TextView) findViewById(R.id.courseInfo_name);
            TextView codeTextView = (TextView) findViewById(R.id.courseInfo_code);
            TextView professorTextView = (TextView) findViewById(R.id.courseInfo_professor);

            idTextView.setText((String) termActivityBundle.get("id"));
            nameTextView.setText((String) termActivityBundle.get("name"));
            codeTextView.setText((String) termActivityBundle.get("code"));
            professorTextView.setText((String) termActivityBundle.get("professor"));
        }
    }
}
