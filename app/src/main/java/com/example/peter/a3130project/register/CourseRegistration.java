package com.example.peter.a3130project.register;
import com.example.peter.a3130project.Course;
import com.example.peter.a3130project.CourseTime;

import java.util.ArrayList;

public class CourseRegistration {
    private ArrayList<Course> current_courses;

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
    public CourseRegistration(ArrayList<Course> current_courses){
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
    public ArrayList<Course> attempt_register(Course course) {
	// Steps:
	// 1. sort current_course by times
	// 2. see whether course conflicts with any
	// 3. Give the proper response to which are conflicting.

	/* Check for duplicate course id */
	for (int i=0;i < current_courses.size(); i++) {
	    if (course.id.equals(current_courses.get(i).id)){
		// there is a matching item, therefore return null
		return null;
	    }
	}
	ArrayList<Course> result = new ArrayList<Course>();

	ArrayList<CourseTime> coursetimes = (ArrayList<CourseTime>) course.get_times();

	for (int a=0; a < coursetimes.size(); a++ ){
	    // Get a time segment for current course
	    int [] course_time = coursetimes.get(a).get_universal_time();

	    for (int i=0;i < current_courses.size(); i++) {
		Course candidate = current_courses.get(i);
		ArrayList<CourseTime> candtimes = (ArrayList<CourseTime>)candidate.get_times();
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
    

    public ArrayList<Course> getcurrent_courses(){
        return current_courses
    }
    public void setcurrent_courses(ArrayList<Course> val){
         this.current_courses = val;
    }
}
