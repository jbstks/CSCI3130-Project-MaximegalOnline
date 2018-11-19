package com.example.project.maximegalonline;
import org.junit.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.csci3130.project.maximegalonline.subject.SubjectSort;
import com.csci3130.project.maximegalonline.course.Course;
import static org.junit.Assert.*;

/**
 * Contributors: PL, JB
 *
 * Tests for register business logic
 */
public class SubjectSortTest {
    
    /** Tests **/
    @Test
    public void sortClassesBySubject() {
	SubjectSort ss = new SubjectSort(new ArrayList<String>(Arrays.asList("CSCI","MATH")));
        Course a_a = new Course( "CSCI1000", "A", "Fall",  "2050");
        Course a_b = new Course( "CSCI1022", "A", "Fall",  "2050");
        Course b_a = new Course( "MATH1000", "A", "Fall",  "2050");
        Course b_b = new Course( "MATH1022", "A", "Fall",  "2050");

        ArrayList<Course> courses = new ArrayList<Course>(Arrays.asList(a_a, a_b, b_a, b_b));
        HashMap<String,ArrayList<Course>> result = ss.doSort(courses);
        
        assertTrue(result.get("CSCI").size()==2);
        assertTrue(result.get("CSCI").contains(a_a));
        assertTrue(result.get("CSCI").contains(a_b));

        assertTrue(result.get("MATH").size()==2);
        assertTrue(result.get("MATH").contains(b_a));
        assertTrue(result.get("MATH").contains(b_b));


	
    }


}
