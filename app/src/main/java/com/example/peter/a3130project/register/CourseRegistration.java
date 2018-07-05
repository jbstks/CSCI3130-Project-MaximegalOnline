package com.example.peter.a3130project.register;
import com.example.peter.a3130project.course.CourseSection;
import com.example.peter.a3130project.course.CourseTime;
import com.example.peter.a3130project.course.CourseSection;

import java.util.ArrayList;

/**
 * Contributors: PL, MG
 *
 * @class CourseRegistration
 * Deals confirming registration conflicts given courses and requested course.
 **/
public class CourseRegistration {
    private ArrayList<CourseSection> current_courses;

    public CourseRegistration() {
    }

    /**
     * CourseRegistration
     * 
     * Constructor for registration
     * --------------
     * Parameters:
     * 
     * @param current_courses:
     *       The current courses that the user has.
     **/
    public CourseRegistration(ArrayList<CourseSection> current_courses){
        this.current_courses = current_courses;
    }

    /**
     * attempt_register:
     * 
     * Attempts to register the course given the user's current courses
     * --------------
     * Parameters:
     * 
     * @param course:
     *       The course that is attempted to register with
     **/
    public ArrayList<CourseSection> attempt_register(CourseSection course) {
	// Steps:
	// 1. sort current_course by times
	// 2. see whether course conflicts with any
	// 3. Give the proper response to which are conflicting.

	/* Check for duplicate course id */
	for (int i=0;i < current_courses.size(); i++) {
	    if (course.getcrn().equals(current_courses.get(i).getcrn())){
		// there is a matching item, therefore return null
		return null;
	    }
            
	}
	ArrayList<CourseSection> result = new ArrayList<CourseSection>();

	ArrayList<CourseTime> coursetimes = (ArrayList<CourseTime>) course.getcourseTimeList();

	for (int a=0; a < coursetimes.size(); a++ ){
            
	    // Get a time segment for current course
	    int [] course_time = coursetimes.get(a).get_universal_time();

	    for (int i=0;i < current_courses.size(); i++) {
		CourseSection candidate = current_courses.get(i);
		ArrayList<CourseTime> candtimes = (ArrayList<CourseTime>)candidate.getcourseTimeList();

                if (!(candidate.getcourse().getsemester().equals(course.getcourse().getsemester()) && candidate.getcourse().getyear().equals(course.getcourse().getyear()))) { //This term and/or year doesn't match. No need to check schedule
                    continue;
                }
                
		for  (int j=0; j < candtimes.size(); j++ ){
		    int [] cand_time = candtimes.get(j).get_universal_time();
		    
		    /* Check for conflict, if there is add it to the list */
		    if ((course_time[0] >= cand_time[0] && course_time[0] <= cand_time[1])||
			(course_time[1] >= cand_time[0] && course_time[1] <= cand_time[1])||
			(cand_time[0] >= course_time[0] && cand_time[0] <= course_time[1])||
			(cand_time[1] >= course_time[0] && cand_time[1] <= course_time[1])) {
			result.add(candidate);
		    
		    }
		}
	    }
	}
	return result;
	
    }
    

    public ArrayList<CourseSection> getcurrent_courses(){
        return current_courses;
    }
    public void setcurrent_courses(ArrayList<CourseSection> val){
         this.current_courses = val;
    }

    public boolean equals(CourseRegistration cr) {
	return this.current_courses.equals(cr.getcurrent_courses());
    }
}
