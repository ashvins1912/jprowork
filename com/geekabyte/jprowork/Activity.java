package com.geekabyte.jprowork;



/**
 * Java Object representing an Activity on Prowork. 
 * @author dade
 */
public class Activity {
    
    private String name;
    private String avatar;
    private String action;
    private String date;
    
    
    
    
   /**
     * Method returns the URL to the users Avatar. 
     *
     * @return String representing URL to users Avatar.
     */
    public String getAvatar() {
        return avatar;
    }
    
    
     /**
     * Method returns the action associated to an activity. 
     *
     * @return the action associated to an activity.
     */
    public String getAction() {
        return action;
    }
    
    
     /**
     * Method the due date associated to an activity. 
     *
     * @return the due date associated to an activity.
     */
    public String getDate() {
        return date;
    }
}
