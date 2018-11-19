package com.csci3130.project.maximegalonline;

/** Term
 *
 * Data structure for holding term information
 *
 * @author Joanna Bistekos
 * @author Dawson Wilson
 */
class Term {

    private String semester;
    private String year;

    /**
     * Constructor
     *
     * @param semester winter, summer, fall
     * @param year     year of semester
     */
    Term(String semester, String year) {
        this.semester = semester;
        this.year = year;
    }

    /**
     *  Get and set methods for semester and year
     */
    public String getsemester(){
        return semester;
    }
    public void setsemester(String val){
         this.semester = val;
    }

    public String getyear(){
        return year;
    }
    public void setyear(String val){
         this.year = val;
    }
}
