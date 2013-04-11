/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geekabyte.jprowork;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author dade
 */
public class Project {
    
    private int id;
    private int memberCount;
    private int taskCount;
    private String projectName;
    private String projectDescription;
    private int admin;
    private Date createdDate;
   
    
    public Project(int id, String projectName, String projectDesc, String created, int memberCount, int taskCount, int admin) {
        this.id = id;
        this.memberCount = memberCount;
        this.taskCount = taskCount;
        this.projectName = projectName;
        this.projectDescription = projectDesc;
        this.admin = admin;
        try {
            this.createdDate = new SimpleDateFormat("MM/dd/yy").parse(created);
        } catch (Exception e) {
        }
    }
    
    
    
    public int getProjectId() {
        return id;
    }
    
    public int getMemberCount() {
        return memberCount;
    }
    
    public String getProjectName() {
        return projectName;
    }
    
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    
    public int getAdmin() {
        return admin;
    }
    
    public Date getCreatedDate() {
        return createdDate;
    }
    
}
