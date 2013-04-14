/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geekabyte.jprowork;

import com.geekabyte.jprowork.apiendpoints.APIendpoint;
import com.geekabyte.jprowork.exceptions.APIerrorMessage;
import com.geekabyte.jprowork.exceptions.InvalidParameterException;
import com.geekabyte.jprowork.exceptions.InvalidResponseJsonString;
import com.geekabyte.jprowork.exceptions.MissingParameterException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dade
 */
public class Member {
    
    private String name;
    private String email;
    private String memberId;
    private String token;
    
    public Member(String name, String email, String userId, String sessionToken) {
        this.name = name;
        this.email = email;
        this.memberId = userId;
        this.token = sessionToken;
    }
    
    public String getName() {
        return name;
    }
    
    public String getEmail () {
        return email;
    }
    
    public String getMemberId() {
        return memberId;
    }
    
    public String getToken() {
        return token;
    }
    
    public String getAvatar(String size) {
        
        String responseJson;
        
        if (size == null) {
            size = "50";
        }
        
        String params = "token="+token;
        params += "&";
        params += "email="+email;
        params += "&";
        params += "size="+size;
        responseJson = RemoteAPIHandler.getJsonFromAPIEndPoint(APIendpoint.getAvatarEndpoint, params);
        
        //TODO
        // Map out every single response type as a class! Thats seems to be a lot of structure to trival stuff? init?
        
        // response format
        // {"avatar":"http:\/\/www.gravatar.com\/avatar\/52a641825a91199799643ea46c936eaa?s=48&d=mm&r=g"}
        Gson gson = new Gson();
        Map<String, String> resultMap = gson.fromJson(responseJson,
                new TypeToken<Map<String, String>>() {
        }.getType());
        
        return resultMap.get("avatar");
    }
    
    
    public Project getProject(int projectId) {
        
        String responseJson;
        String params = "token="+token;
        params += "&";
        params += "project_id="+projectId;
        responseJson = RemoteAPIHandler.getJsonFromAPIEndPoint(APIendpoint.getProjectEndpoint, params);
        // {"id":"208","workspace_id":null,"workspace_name":null,"member_count":"1","task_count":"2","name":"JimiAja","description":"","admin":"196","created_date":"2012-08-31 11:24:39"}
        
        if (responseJson.equals("null")) {
            return null;
        } else {
            Gson gson = new Gson();
            Project project = gson.fromJson(responseJson,Project.class);
            return project;
        }
 

        
    }
    
    
    public List<Project> getProjects() {
        
        String responseJson;
        String params = "token="+token;
        responseJson = RemoteAPIHandler.getJsonFromAPIEndPoint(APIendpoint.getProjectsEndpoint, params);
        Gson gson = new Gson();
        
        List<Project> resultList = gson.fromJson(responseJson,
                new TypeToken<List<Project>>() {
        }.getType());
        
        
        return resultList;
    }
    
    
    public Project createProject(String projectTitle, String projectDescription) {
        String responseJson;
        Project newProject = null;
        String params = "token="+token;
        params += "&";
        params += "title="+projectTitle;
        params += "&";
        params += "description="+projectDescription;
        
        responseJson = RemoteAPIHandler.getJsonFromAPIEndPoint(APIendpoint.createNewProject, params);
        
        Gson gson = new Gson();
        Map<String, String> resultMap = gson.fromJson(responseJson,
                new TypeToken<Map<String, String>>() {
        }.getType());
        
        
            if (resultMap == null) {
                // TODO standardize all the exception api calls can throw
                //throw new InvalidResponseJsonString();
                return null;
            } else if (resultMap.get("error") != null) {
                throw new APIerrorMessage(resultMap.get("error"));
            } else {
                // create a new Project object and return
                int projectId = Integer.parseInt(resultMap.get("id"));
                newProject = this.getProject(projectId);
                return newProject;
            }

    }
    
    
    
    
    public List<Activity> getProjectActivities(int projectId, int read) throws InvalidParameterException {
        
        if (read != 1 && read != 0) {
            throw new InvalidParameterException("read parameter should be 1 or 0");
        }
        String responseJson;
        String params = "token="+token;
        params += "&";
        params += "project_id="+projectId;
        if (read == 1) {
            params += "&";
            params += "read=" + "1";
        }
        responseJson = RemoteAPIHandler.getJsonFromAPIEndPoint(APIendpoint.getProjectActivities, params);
        Gson gson = new Gson();
        
        List<Activity> resultList = gson.fromJson(responseJson,
                new TypeToken<List<Activity>>() {
        }.getType());
        
        return resultList;
    }
    
    
        public List<Activity> getProjectActivities(int projectId) {
        String responseJson;
        String params = "token="+token;
        params += "&";
        params += "project_id="+projectId;
        responseJson = RemoteAPIHandler.getJsonFromAPIEndPoint(APIendpoint.getProjectActivities, params);
        Gson gson = new Gson();
        List<Activity> resultList = gson.fromJson(responseJson,
                    new TypeToken<List<Activity>>() {
            }.getType());
        
        return resultList;
    }
    
}
