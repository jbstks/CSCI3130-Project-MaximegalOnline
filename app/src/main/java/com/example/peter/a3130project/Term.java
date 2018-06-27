package com.example.peter.a3130project;

import java.util.ArrayList;
import java.util.List;

class Term {

    private String semester;
    private String year;

    Term(String semester, String year) {
        this.semester = semester;
        this.year = year;
    }

    /* Get and set methods */
    public String getsemester(){
        return semester;
    }
    public void setsemester(String val){
         this.semester = val;
    }
    public String getyear(){
        return year;
    }
    public void setyear(String val){
         this.year = val;
    }
}
