package com.geekabyte.jprowork;

import com.geekabyte.jprowork.apiendpoints.APIendpoint;
import com.geekabyte.jprowork.exceptions.APIerrorMessage;
import com.geekabyte.jprowork.exceptions.InvalidParameterException;
import com.geekabyte.jprowork.exceptions.JproworkRuntimeException;
import com.geekabyte.jprowork.exceptions.RemoteAPIHandlerException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.Map;

/**
 * The Java object representation of a user on Prowork. 
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

    /**
     * This method returns the name of a logged in Prowork user as string. 
     *
     * @return Name of user.
     */
    public String getName() {
        return name;
    }

    /**
     * This method return the email address of a logged in Prowork user as string. 
     *
     * @return The email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method return the ID of a logged in Prowork user as string. 
     *
     * @return the member ID of user.
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * Method returns the token for logged in session to Prowork. 
     *
     * @return String token.
     */
    public String getToken() {
        return token;
    }

    /**
     * Method returns the URL to the users Avatar. 
     *
     * @throws JproworkRuntimeException if an error occurs while retrieving
     * Avatar
     * @return String representing URL to users Avatar.
     */
    public String getAvatar() throws JproworkRuntimeException {
        return getAvatar(null);
    }

    /**
     * Method returns the URL to the users Avatar. 
     *
     * @throws JproworkRuntimeException if an error occurs while retrieving
     * Avatar
     * @param size Integer specifying the size in Pixels of image to return
     * @return String representing URL to users Avatar.
     */
    public String getAvatar(Integer size) throws JproworkRuntimeException {

        String responseJson;

        if (size == null) {
            size = 50;
        }

        String params = "token=" + token;
        params += "&";
        params += "email=" + email;
        params += "&";
        params += "size=" + size;

        try {
            responseJson = RemoteAPIHandler.getJsonFromAPIEndPoint(APIendpoint.getAvatarEndpoint, params);
        } catch (Exception e) {
            throw new RemoteAPIHandlerException("Error calling API Endpoint", e);
        }

        Gson gson = new Gson();
        Map<String, String> resultMap = gson.fromJson(responseJson,
                new TypeToken<Map<String, String>>() {
        }.getType());

        return resultMap.get("avatar");
    }

    /**
     * Method for getting a Prowork Project Object. 
     *
     * @throws JproworkRuntimeException if an error occurs while retrieving
     * Project
     * @param projectId The ID of the project to return.
     * @return String representing URL to users Avatar.
     */
    public Project getProject(int projectId) throws JproworkRuntimeException {

        String responseJson;
        String params = "token=" + token;
        params += "&";
        params += "project_id=" + projectId;
        try {
            responseJson = RemoteAPIHandler.getJsonFromAPIEndPoint(APIendpoint.getProjectEndpoint, params);
        } catch (Exception e) {
            throw new RemoteAPIHandlerException("Error while calling API Endpoint", e);
        }

        if (responseJson.equals("null")) {
            return null;
        } else {
            Gson gson = new Gson();
            Project project = gson.fromJson(responseJson, Project.class);
            return project;
        }



    }

    /**
     * Method for retrieving all project an authenticated user belongs to. 
     *
     * @throws JproworkRuntimeException if an error occurs while retrieving
     * lists of projects.
     * @return A list of all project an authenticated user belongs to.
     */
    public List<Project> getProjects() throws JproworkRuntimeException {

        String responseJson;
        String params = "token=" + token;

        try {
            responseJson = RemoteAPIHandler.getJsonFromAPIEndPoint(APIendpoint.getProjectsEndpoint, params);
        } catch (Exception e) {
            throw new RemoteAPIHandlerException("Error Occured while calling API Endpoint.", e);
        }


        Gson gson = new Gson();

        List<Project> resultList = gson.fromJson(responseJson,
                new TypeToken<List<Project>>() {
        }.getType());


        return resultList;
    }

    /**
     * Method to create a new project. 
     *
     * @param projectTitle the title of project to be created
     * @param projectDescription description of project
     * @throws JproworkRuntimeException if an error occurs while creating
     * project
     * @return A Prowork Project
     * @see Project
     */
    public Project createProject(String projectTitle, String projectDescription)
            throws JproworkRuntimeException {
        String responseJson = null;
        Project newProject = null;
        String params = "token=" + token;
        params += "&";
        params += "title=" + projectTitle;
        params += "&";
        params += "description=" + projectDescription;

        try {
            responseJson = RemoteAPIHandler.postToAPIEndPoint(APIendpoint.createNewProject, params);
        } catch (Exception e) {
            throw new RemoteAPIHandlerException("Error occured while performing POST operation to API Endpoint.", e);
        }

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

    /**
     * Method to create a new project. 
     *
     * @param projectId the ID of project to delete
     * @return True or False. True if project was deleted and false if not.
     * @throws JproworkRuntimeException if an error occurs while deleting
     * project
     * @see Project
     */
    public Boolean deleteProject(int projectId) throws JproworkRuntimeException {

        String responseJson;
        String params = "token=" + token;
        params += "&";
        params += "project_id=" + projectId;

        try {
            responseJson = RemoteAPIHandler.getJsonFromAPIEndPoint(APIendpoint.deleteProject, params);
        } catch (Exception e) {
            throw new RemoteAPIHandlerException("Error occured while calling API Endpoint", e);
        }

        Gson gson = new Gson();

        Map<String, String> resultMap = gson.fromJson(responseJson,
                new TypeToken<Map<String, String>>() {
        }.getType());

        if (resultMap.get("status").equals("done")) {
            return true;
        } else {
            return false;
        }


    }

    /**
     * Method to get a list of project activities. 
     *
     * @param projectID the ID of project whose activities is to retrieved
     * @param read Optional. By default, only unread activity messages are
     * returned. To include read messages, set this parameter to 1
     * @throws JproworkRuntimeException if an error occurs while retrieving
     * projects Activities.
     * @return List<Activity> A list of Prowork Activity objects
     * @see Activity
     */
    public List<Activity> getProjectActivities(int projectId, int read) throws JproworkRuntimeException {

        if (read != 1 && read != 0) {
            throw new InvalidParameterException("read parameter should be 1 or 0");
        }
        String responseJson;
        String params = "token=" + token;
        params += "&";
        params += "project_id=" + projectId;
        if (read == 1) {
            params += "&";
            params += "read=" + "1";
        }

        try {
            responseJson = RemoteAPIHandler.getJsonFromAPIEndPoint(APIendpoint.getProjectActivities, params);
        } catch (Exception e) {
            throw new RemoteAPIHandlerException("Error occured while calling API Endpoint.", e);
        }
        Gson gson = new Gson();

        List<Activity> resultList = gson.fromJson(responseJson,
                new TypeToken<List<Activity>>() {
        }.getType());

        return resultList;
    }

    /**
     * Method to get a list of unread project activities. 
     *
     * @param projectID the ID of project whose activities is to retrieved
     * @throws JproworkRuntimeException if an error occurs while retrieving
     * projects Activities.
     * @return List<Activity> A list of Prowork Activity objects
     * @see Activity
     */
    public List<Activity> getProjectActivities(int projectId) throws JproworkRuntimeException {
        String responseJson;
        String params = "token=" + token;
        params += "&";
        params += "project_id=" + projectId;

        try {
            responseJson = RemoteAPIHandler.getJsonFromAPIEndPoint(APIendpoint.getProjectActivities, params);
        } catch (Exception e) {
            throw new RemoteAPIHandlerException("Error occured while calling API Endpoint.", e);
        }

        Gson gson = new Gson();
        List<Activity> resultList = gson.fromJson(responseJson,
                new TypeToken<List<Activity>>() {
        }.getType());

        return resultList;
    }

    /**
     * Get the activity stream of all projects the authenticated user is a
     * member of. Note that this resets the notification count to zero. By
     * default the method pulls only unread notifications. To retrieve read
     * notifications as well, add the read query parameter. 
     *
     * @throws JproworkRuntimeException
     * @return A list of Prowork Activity object
     * @see Activity
     */
    public List<Activity> getAllProjectActivities() throws JproworkRuntimeException {
        String responseJson;
        String params = "token=" + token;

        try {
            responseJson = RemoteAPIHandler.getJsonFromAPIEndPoint(APIendpoint.getMemberProjectActivities, params);
        } catch (Exception e) {
            throw new RemoteAPIHandlerException("Error occured while calling API Endpoint", e);
        }


        Gson gson = new Gson();
        List<Activity> resultList = gson.fromJson(responseJson,
                new TypeToken<List<Activity>>() {
        }.getType());

        return resultList;
    }

    /**
     * Get the activity stream of all projects the authenticated user is a
     * member of. Note that this resets the notification count to zero. By
     * default the method pulls only unread notifications. To retrieve read
     * notifications as well, add the read query parameter. 
     *
     * @param read flag that determines whether to return read notifications. 1
     * means fetch
     * @throws JproworkRuntimeException
     * @return list of Prowork Activity Object
     * @see Activity
     */
    public List<Activity> getAllProjectActivities(int read) throws JproworkRuntimeException {

        if (read != 1 && read != 0) {
            throw new InvalidParameterException("read parameter should be 1 or 0");
        }

        String responseJson;
        String params = "token=" + token;
        params += "&";

        if (read == 1) {
            params += "&";
            params += "read=" + "1";
        }

        try {
            responseJson = RemoteAPIHandler.getJsonFromAPIEndPoint(APIendpoint.getMemberProjectActivities, params);
        } catch (Exception e) {
            throw new RemoteAPIHandlerException("Error occured while calling API Endpoint.", e);
        }


        Gson gson = new Gson();
        List<Activity> resultList = gson.fromJson(responseJson,
                new TypeToken<List<Activity>>() {
        }.getType());

        return resultList;
    }

    /**
     * Get the activity stream notification count for this user. 
     *
     * @throws JproworkRuntimeException
     * @return The number of notification count for this user
     */
    public int getNotificationCount() throws JproworkRuntimeException {
        String responseJson;
        int notificationCount;
        String params = "token=" + token;

        try {
            responseJson = RemoteAPIHandler.getJsonFromAPIEndPoint(APIendpoint.getNotificationCount, params);
        } catch (Exception e) {
            throw new RemoteAPIHandlerException("Error occured while calling API Endpoint.", e);
        }

        Gson gson = new Gson();
        Map<String, Integer> resultMap = gson.fromJson(responseJson,
                new TypeToken<Map<String, Integer>>() {
        }.getType());

        notificationCount = resultMap.get("notifications");
        return notificationCount;
    }
}
