package com.example.peter.a3130project;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.TermViewHolder>{

    List<Term> terms;

    // constructor
    RVAdapter(List<Term> terms) {
        this.terms = terms;
    }

    // provides a way to access data
    public static class TermViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView term_semester;
        TextView term_year;

        TermViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            term_semester = itemView.findViewById(R.id.term_semester);
            term_year = itemView.findViewById(R.id.term_year);

            Log.d("Debug","Entering click function");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    String semester = term_semester.getText().toString();
                    String year = term_year.getText().toString();
                    intent.putExtra("semester", semester);
                    intent.putExtra("year", year);
                    v.getContext().startActivity(intent);
                }
            });
        }

    }

    @Override
    public TermViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_term, viewGroup, false);
        TermViewHolder pvh = new TermViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(TermViewHolder termViewHolder, int i) {
        // This refers to the public class Term
        termViewHolder.term_semester.setText(terms.get(i).semester);
        termViewHolder.term_year.setText(terms.get(i).year);
    }

    @Override
    public int getItemCount() {
        return terms.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}