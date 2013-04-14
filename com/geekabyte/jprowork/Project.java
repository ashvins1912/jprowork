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
    private int workspace_id;
    private String workspace_name;
    private int member_count;
    private int task_count;
    private String name;
    private String description;
    private int admin;
    private String created_date;
   
    
    public Project(int id, String projectName, String projectDesc, String created, int memberCount, int taskCount, int admin) {
        this.id = id;
        this.member_count = memberCount;
        this.task_count = taskCount;
        this.name = projectName;
        this.description = projectDesc;
        this.admin = admin;
        this.created_date = created;

    }
    
   
    public int getProjectId() {
        return id;
    }
    
    
    public int getWorkSpaceId() {
        return workspace_id;
    }
    
    
    public String getWorkSpaceName() {
        return workspace_name;
    }
    
    public int getMemberCount() {
        return member_count;
    }
    
    
    public int getTaskCount() {
        return task_count;
    }
    
    public String getProjectName() {
        return name;
    }
    
    public String getProjectDescription() {
        return description;
    }
    
    
    public int getAdmin() {
        return admin;
    }
    
    public String getCreatedDate() {
        // TODO convert string representation to Date object
        return this.created_date;
    }
    
}
