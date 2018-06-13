package com.example.peter.a3130project;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class TermSelectionFragment extends Fragment {

    public TermSelectionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_term, container, false);
    }

    /** Called when the user taps a course card */
    public void viewTermInfo(View view) {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra("semester", "Winter");
        intent.putExtra("year", "2018");
        startActivity(intent);
    }
}
