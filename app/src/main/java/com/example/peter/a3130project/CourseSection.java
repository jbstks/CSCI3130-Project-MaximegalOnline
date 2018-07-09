package com.example.peter.a3130project;

import java.util.List;
/**
 * Represents a course section
 *
 *
 * @attr sectionNum: String
 *       name of the section
 *
 * @attr crn: String
 *       name of course id
 *
 * @attr professor: String
 *       name of professor
 *
 * @attr courseTimeList: List<CourseTime>
 *       list of course times.
 *
 * @author Aecio and Dawson
 *
 **/
public class CourseSection {
    private String sectionNum;
    private String crn;
    private String professor;
    private List<CourseTime> courseTimeList;

    /** Constructor
     *
     * @param sectionNum
     * @param crn
     * @param professor
     * @param courseTimeList
     */
    public CourseSection(String sectionNum, String crn, String professor, List<CourseTime> courseTimeList){
        this.sectionNum = sectionNum;
        this.crn = crn;
        this.professor = professor;
        this.courseTimeList = courseTimeList;
    }

    /** Getters and setters
     *
     *
     */
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
}
