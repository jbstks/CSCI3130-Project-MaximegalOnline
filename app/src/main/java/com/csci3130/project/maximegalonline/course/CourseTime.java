package com.csci3130.project.maximegalonline.course;

import java.util.HashMap;
/**
 * Class for keeping track of course times
 *
 * @author Peter Lee
 * @author Megan Gosse
 * @author Dawson Wilson
 * @author Aecio Cavalcanti
 */
public class CourseTime {

    private String day;
    private String startTime;
    private String endTime;
    private String location;
    private HashMap<String, Integer> day_time_conversion;

    /**
     * Construct to create a CourseTime object
     *
     * @param day       day of the week
     * @param startTime start of class in 24h format
     * @param endTime   end of class in 24h format
     * @param location  location of class
     */
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
     * Converts the string time to user's universal times.
     *
     * @return arr array containing the converted start time and end time
     */
    public int [] get_universal_time() {
    	int startime = 0, endtime = 0;
        try {
            int daymul = day_time_conversion.get(this.day);
            startime = daymul*2400 + Integer.parseInt(startTime);
            endtime = daymul*2400 + Integer.parseInt(endTime);
        }
        catch (Exception e) { }
        int arr[] = {startime, endtime};
        return arr;
    }

    /**
     * Get and set methods for day, startTime, endTime, and location
     */
    public String getday(){
        return day;
    }
    public void setday(String val){
         this.day = val;
    }
    
    public String getstartTime(){
        return startTime;
    }
    public void setstartTime(String val){
         this.startTime = val;
    }
    
    public String getendTime(){
        return endTime;
    }
    public void setendTime(String val){
         this.endTime = val;
    }
    
    public String getlocation(){
        return location;
    }
    public void setlocation(String val){
         this.location = val;
    }
}
