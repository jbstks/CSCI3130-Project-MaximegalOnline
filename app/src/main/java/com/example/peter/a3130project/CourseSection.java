package com.example.peter.a3130project;

import java.util.List;

 class CourseSection {
    String crn;
    String professor;
    List<CourseTime> courseTimeList;

    CourseSection(String crn, String professor, List<CourseTime> courseTimeList){
        this.crn = crn;
        this.professor = professor;
        this.courseTimeList = courseTimeList;
    }


}
