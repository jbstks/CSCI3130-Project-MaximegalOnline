package com.csci3130.project.maximegalonline.subject;

import com.csci3130.project.maximegalonline.course.Course;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class for handling sorting of classes by subject
 *
 * @author Peter Lee
 * @author Joanna Bistekos
 * @date July 23, 2018
 **/
public class SubjectSort {
    private List<String> categories;
    public SubjectSort(List<String> categories) {
        this.categories = categories;
    }

    /** doSort
     * @param courses
     * @return Hashset for each course identifier
     **/
    public HashMap<String,ArrayList<Course>> doSort(List<Course> courses) {
        HashMap<String,ArrayList<Course>> ret = new HashMap<String,ArrayList<Course>>();
        /* Initialize hashset */
        for (String s : categories) {
            ret.put(s, new ArrayList<Course>());
        }

        /* Go through and sort each course into subjects */
        for (Course c : courses) {
            for (String s : categories) {
                if (c.getcode().startsWith(s)) {
                    ret.get(s).add(c);
                    break;
                }
            }
        }

        return ret;
    }

        
    public List<String> getcategories(){
        return this.categories;
    }
    public void setcategories(List<String> val){
         this.categories = val;
    }}
