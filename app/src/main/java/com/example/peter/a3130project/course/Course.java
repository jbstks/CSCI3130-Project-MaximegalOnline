package com.example.peter.a3130project.course;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a course
 *
 * @author Peter Lee
 * @author Megan Gosse
 * @author Dawson Wilson
 * @author Aecio Cavalcanti
 */
public class Course {

    private String code;
    private String name;
    // TODO this probably is not needed (semester and year)
    private String semester;
    private String year;

    /**
     * Default constructor
     */
    public Course() {
    }

    /**
     * Constructor to create a Course object
     *
     * @param code      the course code (i.e., CSCI3120)
     * @param name      the name of the course
     * @param semester  the semester the course is in (Winter, Summer, Fall)
     * @param year      the year of the semester
     */
    public Course(String code, String name, String semester, String year) {
        this.code = code;
        this.name = name;
        this.semester = semester;
        this.year = year;
    }

    /**
     * Checks if this course and another course are equal
     *
     * @param ot the other course
     * @return boolean, whether or not this course and {@code ot} are equal
     */
    @Override
    public boolean equals(Object ot) {
	Course other = (Course) ot;
	return (this.code.equals(other.getcode()) &&
		this.name.equals(other.getname()) &&
		this.semester.equals(other.getsemester()) &&
		this.year.equals(other.getyear()));

    }

    /**
     * Get and set methods for code, name, semester, and year
     */
    public String getcode(){
        return code;
    }
    public void setcode(String val){
         this.code = val;
    }

    public String getname(){
        return name;
    }
    public void setname(String val){
         this.name = val;
    }
    
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
