package com.example.peter.a3130project.course;

import java.util.List;
/**
 * Represents a course section
 *
 * @author Peter Lee
 * @author Megan Gosse
 * @author Dawson Wilson
 * @author Aecio Cavalcanti
 * @author Joanna Bistekos
 */
public class CourseSection {
    private int capacity;
    private int enrolled;
    private String sectionNum;
    private String crn;
    private String professor;
    private Course course;
    private List<CourseTime> courseTimeList;

    /**
     * Constructor to create a CourseSection object
     *
     * @param enrolled       number of students who are enrolled
     * @param capacity       maximum number of students who can enroll
     * @param sectionNum     the section number of the course
     * @param crn            the course identification number
     * @param professor      the name of professor
     * @param courseTimeList list of course times
     */
    public CourseSection(int enrolled, int capacity, String sectionNum, String crn, String professor, Course course, List<CourseTime> courseTimeList){

        this.enrolled = enrolled;
        this.capacity = capacity;
        this.sectionNum = sectionNum;
        this.crn = crn;
        this.professor = professor;
        this.course = course;
        this.courseTimeList = courseTimeList;
    }

    /**
     * Getters and setters for enrolled, capacity, sectionNum, crn, professor, courseTimeList, and course
     */
    public int getenrolled(){
        return this.enrolled;
    }
    public void setenrolled(int val){
        this.enrolled = val;
    }

    public int getcapacity(){
        return this.capacity;
    }
    public void setcapacity(int val){
        this.capacity = val;
    }

    public String getsectionNum(){
        return this.sectionNum;
    }
    public void setsectionNum(String val){
         this.sectionNum = val;
    }

    public String getcrn(){
        return this.crn;
    }
    public void setcrn(String val){
         this.crn = val;
    }

    public String getprofessor(){
        return this.professor;
    }
    public void setprofessor(String val){
         this.professor = val;
    }

    public List<CourseTime> getcourseTimeList(){
        return this.courseTimeList;
    }
    public void setcourseTimeList(List<CourseTime> val){
         this.courseTimeList = val;
    }

    public Course getcourse(){
        return this.course;
    }
    public void setcourse(Course val){
         this.course = val;
    }
}
