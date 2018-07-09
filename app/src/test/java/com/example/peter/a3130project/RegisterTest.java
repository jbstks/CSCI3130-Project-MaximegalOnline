package com.example.peter.a3130project;

import com.example.peter.a3130project.register.CourseRegistration;
import com.example.peter.a3130project.course.Course;
import com.example.peter.a3130project.course.CourseTime;

import org.junit.Test;


import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Contributors: PL, MG
 *
 * Tests for register business logic
 */
public class RegisterTest {
    
    /** Tests acceptance in using a valid registration **/
    @Test
    public void validRegister() {
	
	CourseTime at1 = new CourseTime("Monday", "1135", "1225", "lol");
	CourseTime at2 = new CourseTime("Wednesday", "1135", "1225", "lol");
	Course a = new Course("11111", "A", "CSCI1000", "You", "Fall", "year", new ArrayList<CourseTime>(Arrays.asList(at1,at2)));

	CourseTime bt1 = new CourseTime("Monday", "1235", "1325", "lol");
	CourseTime bt2 = new CourseTime("Wednesday", "1235", "1325", "lol");

	Course b = new Course("11112", "B", "CSCI1001", "You", "Fall", "year", new ArrayList<CourseTime>(Arrays.asList(bt1,bt2)));
	
	CourseTime ct1 = new CourseTime("Monday", "1335", "1425", "lol");
	CourseTime ct2 = new CourseTime("Wednesday", "1335", "1425", "lol");

	Course c = new Course("11113", "try", "CSCI1002", "You", "Fall", "year", new ArrayList<CourseTime>((Arrays.asList(ct1,ct2))));


	CourseRegistration cr = new CourseRegistration(new ArrayList<Course>(Arrays.asList(a,b)));

	ArrayList<Course> result = cr.attempt_register(c);

	assert(result!=null);
	assert(result.size() == 0);
	
    }

    /** Tests acceptance in using a valid registration **/
    @Test
    public void validRegister_day() {
	
	CourseTime at1 = new CourseTime("Monday", "1135", "1225", "lol");
	//CourseTime at2 = new CourseTime("Wednesday", "1135", "1225", "lol");
	Course a = new Course("11111", "A", "CSCI1000", "You", "Fall", "year", new ArrayList<CourseTime>(Arrays.asList(at1)));

	//CourseTime bt1 = new CourseTime("Monday", "1235", "1325", "lol");
	CourseTime bt2 = new CourseTime("Wednesday", "1235", "1325", "lol");

	Course b = new Course("11112", "B", "CSCI1001", "You", "Fall", "year", new ArrayList<CourseTime>(Arrays.asList(bt2)));
	
	CourseTime ct1 = new CourseTime("Monday", "1235", "1325", "lol");	
	CourseTime ct2 = new CourseTime("Wednesday", "1135", "1225", "lol");


	Course c = new Course("11113", "try", "CSCI1002", "You", "Fall", "year", new ArrayList<CourseTime>((Arrays.asList(ct1,ct2))));


	CourseRegistration cr = new CourseRegistration((new ArrayList<Course>(Arrays.asList(a,b))));

	ArrayList<Course> result = cr.attempt_register(c);

	assert(result!=null);
	assert(result.size() == 0);
	
    }


    /** Tests acceptance in using a valid registration when they are different seasons**/    
    @Test
    public void validRegister_season() {

	CourseTime at1 = new CourseTime("Monday", "1135", "1225", "lol");
	CourseTime at2 = new CourseTime("Wednesday", "1135", "1225", "lol");
	Course a = new Course("11111", "A", "CSCI1000", "You", "Fall", "year", new ArrayList<CourseTime>(Arrays.asList(at1,at2)));

	CourseTime bt1 = new CourseTime("Monday", "1235", "1325", "lol");
	CourseTime bt2 = new CourseTime("Wednesday", "1235", "1325", "lol");

	Course b = new Course("11112", "B", "CSCI1001", "You", "Winter", "year", new ArrayList<CourseTime>(Arrays.asList(bt1,bt2)));
	
	CourseTime ct1 = new CourseTime("Monday", "1235", "1325", "lol");
	CourseTime ct2 = new CourseTime("Wednesday", "1235", "1325", "lol");

	Course c = new Course("11113", "try", "CSCI1002", "You", "Fall", "year", new ArrayList<CourseTime>((Arrays.asList(ct1,ct2))));


	CourseRegistration cr = new CourseRegistration((new ArrayList<Course>(Arrays.asList(a,b))));

	ArrayList<Course> result = cr.attempt_register(c);

	assert(result!=null);
	assert(result.size() == 0);

    }

    /** Tests conflicting courses in using a invalid registration by start time**/
    @Test
    public void conflictRegister_start() {

	CourseTime at1 = new CourseTime("Monday", "1135", "1225", "lol");
	CourseTime at2 = new CourseTime("Wednesday", "1135", "1225", "lol");
	Course a = new Course("11111", "A", "CSCI1000", "You", "Fall", "year", new ArrayList<CourseTime>(Arrays.asList(at1,at2)));

	CourseTime bt1 = new CourseTime("Monday", "1235", "1325", "lol");
	CourseTime bt2 = new CourseTime("Wednesday", "1235", "1325", "lol");

	Course b = new Course("11112", "B", "CSCI1001", "You", "Fall", "year", new ArrayList<CourseTime>(Arrays.asList(bt1,bt2)));
	
	CourseTime ct1 = new CourseTime("Monday", "1300", "1425", "lol");
	CourseTime ct2 = new CourseTime("Wednesday", "1300", "1425", "lol");

	Course c = new Course("11113", "try", "CSCI1002", "You", "Fall", "year", new ArrayList<CourseTime>((Arrays.asList(ct1,ct2))));


	CourseRegistration cr = new CourseRegistration((new ArrayList<Course>(Arrays.asList(a,b))));

	ArrayList<Course> result = cr.attempt_register(c);

	assert(result!=null);
	assert(result.size() != 0);


    }

    /** Tests conflicting courses in using a invalid registration by endtime **/
    @Test
    public void conflictRegister_end() {

	CourseTime at1 = new CourseTime("Monday", "1135", "1225", "lol");
	CourseTime at2 = new CourseTime("Wednesday", "1135", "1225", "lol");
	Course a = new Course("11111", "A", "CSCI1000", "You", "Fall", "year", new ArrayList<CourseTime>(Arrays.asList(at1,at2)));

	CourseTime bt1 = new CourseTime("Monday", "1235", "1325", "lol");
	CourseTime bt2 = new CourseTime("Wednesday", "1235", "1325", "lol");

	Course b = new Course("11112", "B", "CSCI1001", "You", "Fall", "year", new ArrayList<CourseTime>(Arrays.asList(bt1,bt2)));
	
	CourseTime ct1 = new CourseTime("Monday", "1000", "1200", "lol");
	CourseTime ct2 = new CourseTime("Wednesday", "1000", "1200", "lol");

	Course c = new Course("11113", "try", "CSCI1002", "You", "Fall", "year", new ArrayList<CourseTime>((Arrays.asList(ct1,ct2))));


	CourseRegistration cr = new CourseRegistration((new ArrayList<Course>(Arrays.asList(a,b))));

	ArrayList<Course> result = cr.attempt_register(c);

	assert(result!=null);
	assert(result.size() != 0);


    }

    /** Tests duplicate users in using a invalid registration **/
    @Test
    public void duplicateRegister() {
	CourseTime at1 = new CourseTime("Monday", "1135", "1225", "lol");
	CourseTime at2 = new CourseTime("Wednesday", "1135", "1225", "lol");
	Course a = new Course("11111", "A", "CSCI1000", "You", "Fall", "year", new ArrayList<CourseTime>(Arrays.asList(at1,at2)));

	CourseTime bt1 = new CourseTime("Monday", "1235", "1325", "lol");
	CourseTime bt2 = new CourseTime("Wednesday", "1235", "1325", "lol");

	Course b = new Course("11112", "B", "CSCI1001", "You", "Fall", "year", new ArrayList<CourseTime>(Arrays.asList(bt1,bt2)));
	

	Course c = new Course("11111", "A", "CSCI1000", "You", "Fall", "year", new ArrayList<CourseTime>((Arrays.asList(at1,at2))));


	CourseRegistration cr = new CourseRegistration((new ArrayList<Course>(Arrays.asList(a,b))));

	ArrayList<Course> result = cr.attempt_register(c);

	assert(result==null);


    }

}
