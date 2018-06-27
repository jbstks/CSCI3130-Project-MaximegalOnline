package com.example.peter.a3130project;

import java.util.HashMap;

public class CourseTime {

    public String day;
    public String startTime;
    public String endTime;
    public String location;
    private HashMap<String, Integer> day_time_conversion;
	public CourseTime(String day, String startTime, String endTime, String location) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
	
	day_time_conversion = new HashMap<String, Integer>();
	day_time_conversion.put("Sunday", 0);
	day_time_conversion.put("Monday", 1);
	day_time_conversion.put("Tuesday", 2);
	day_time_conversion.put("Wednesday", 3);
	day_time_conversion.put("Thursday", 4);
	day_time_conversion.put("Friday", 5);
	day_time_conversion.put("Saturday", 6);
    }

    /**
     * get_universal_time:
     *
     * convert's the string time to user's universal times.
     * --------------
     * Parameters:
     * 
     * None
     */
    public int [] get_universal_time() {
    	int startime=0,endtime=0;
	try {
	    int daymul = day_time_conversion.get(this.day);
	    startime = daymul*2400 + Integer.parseInt(startTime);
	    endtime = daymul*2400 + Integer.parseInt(endTime);
	    
	}
	catch (Exception e) {//change this
	}
	int arr[] = {startime, endtime};
	return arr;
    }

}
