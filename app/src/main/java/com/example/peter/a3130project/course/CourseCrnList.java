package com.example.peter.a3130project.course;

/**
 * Represents a course
 *
 * @author Peter Lee
 * @author Megan Gosse
 * @author Dawson Wilson
 * @author Aecio Cavalcanti
 */
public class CourseCrnList {

    private String section;
    private String crn;

    /**
     * Default constructor
     */
    public CourseCrnList() {
    }

    /**
     * Constructor to create a CourseCrnList object
     *
     * @param section   the section id
     * @param crn      the crn correlated with the section
     */
    public CourseCrnList(String section, String crn) {
        this.section = section;
        this.crn = crn;
    }

    /**
     * Get and set methods for code, name, semester, and year
     */
    public String getSection(){
        return section;
    }
    public void setsection(String val){
         this.section = val;
    }

    public String getcrn(){
        return crn;
    }
    public void setcrn(String val){
         this.crn = val;
    }
}
