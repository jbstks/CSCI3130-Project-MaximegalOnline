package com.example.peter.a3130project;

import java.util.ArrayList;
import java.util.List;

public class Course {

    public String code;
    public String name;
    public String semester;
    public String year;


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
}
