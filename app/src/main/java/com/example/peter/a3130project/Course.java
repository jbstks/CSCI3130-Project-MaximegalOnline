package com.example.peter.a3130project;

import java.util.ArrayList;
import java.util.List;

public class Course {

    private String code;
    private String name;
    private String semester;
    private String year;


    public Course() {
        // Default constructor
    }

    public Course(String code, String name, String semester, String year) {
        this.code = code;
        this.name = name;
        this.semester = semester;
        this.year = year;
    }

    public List<CourseTime> get_times() {
        return times;
    }

    @Override
    public boolean equals(Course other) {
	return (this.id.equals(other.id) &&
		this.id.equals(other.id) &&
		this.id.equals(other.id) &&
		this.id.equals(other.id) &&
		this.id.equals(other.id) &&
		this.id.equals(other.id) &&
		this.id.equals(other.id));
    }

    public String getcode(){
        return code
    }
    public void setcode(String val){
         this.code = val;
    }

    public String getname(){
        return name
    }
    
    public void setname(String val){
         this.name = val;
    }
    
    public String getsemester(){
        return semester
    }
    
    public void setsemester(String val){
         this.semester = val;
    }
    
    public String getyear(){
        return year
    }
    
    public void setyear(String val){
         this.year = val;
    }
}
