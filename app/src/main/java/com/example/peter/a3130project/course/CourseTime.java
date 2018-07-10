package com.example.peter.a3130project.course;

import java.util.HashMap;
/**
 *
 *  CourseTime:
 *
 * Class for keeping track of course times
 *
 * @attr day: String
 *       Day of the week
 *
 * @attr startTime: String
 *       start of class in 24h format
 *
 * @attr endTime: String
 *       end of class in 24h format
 *
 * @attr location: String
 *       location of class
 *
 * @author PL
 * @author MG
 * @author DW
 * @author AC

 **/
public class CourseTime {

    private String day;
    private String startTime;
    private String endTime;
    private String location;
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

        day_time_conversion.put("sunday", 0);
        day_time_conversion.put("monday", 1);
        day_time_conversion.put("tuesday", 2);
        day_time_conversion.put("wednesday", 3);
        day_time_conversion.put("thursday", 4);
        day_time_conversion.put("friday", 5);
        day_time_conversion.put("saturday", 6);
    }
    

    /**
     * get_universal_time:
     *
     * convert's the string time to user's universal times.
     * --------------
     * @return arr array containing the converted start time and end time
     */
    public int [] get_universal_time() {
    	int startime = 0, endtime = 0;
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

    /** Get and set methods...
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
