package com.example.peter.a3130project;

import java.util.ArrayList;
import java.util.List;

public class Course {

    public String id;
    public String name;
    public String code;
    public String professor;
    public String semester;
    public String year;
    public List<CourseTime> times;

    public Course() {
        // Default constructor
    }

    public Course(String id, String name, String code, String professor, String semester, String year, List<CourseTime> times) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.professor = professor;
        this.semester = semester;
        this.year = year;
        this.times = times;
    }
}
