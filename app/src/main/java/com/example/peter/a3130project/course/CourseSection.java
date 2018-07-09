package com.example.peter.a3130project.course;

import java.util.List;
/**
 * @class CourseSelection
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
 **/
public class CourseSection {
    private String sectionNum;
    private String crn;
    private String professor;
    private Course course;
    private List<CourseTime> courseTimeList;
    
    public CourseSection(String sectionNum, String crn, String professor, Course course, List<CourseTime> courseTimeList){
        this.sectionNum = sectionNum;
        this.crn = crn;
        this.professor = professor;
        this.course = course;
        this.courseTimeList = courseTimeList;
        
    }

    /** Get set methods **/
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
