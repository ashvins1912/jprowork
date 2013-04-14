/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geekabyte.jprowork.apiendpoints;

/**
 *
 * @author dade
 */
public class APIendpoint {
    
    private APIendpoint() {}
    
    public static String loginEndpoint = "http://api.prowork.me/session/get";
    public static String registerEndpoint = "http://api.prowork.me/member/register";
    public static String getAvatarEndpoint = "http://api.prowork.me/member/avatar";
    public static String getProjectEndpoint = "http://api.prowork.me/project/get";
    public static String getProjectsEndpoint = "http://api.prowork.me/projects/get";
    public static String getProjectActivities = "http://api.prowork.me/project/activities";
    public static String createNewProject = "http://api.prowork.me/project/new";

}
