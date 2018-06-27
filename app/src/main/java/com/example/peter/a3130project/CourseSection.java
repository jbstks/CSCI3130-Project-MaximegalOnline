package com.example.peter.a3130project;

import java.util.List;

class CourseSection {
    String sectionNum;
    String crn;
    String professor;
    List<CourseTime> courseTimeList;

    CourseSection(String sectionNum, String crn, String professor, List<CourseTime> courseTimeList){
        this.sectionNum = sectionNum;
        this.crn = crn;
        this.professor = professor;
        this.courseTimeList = courseTimeList;
    }


}
