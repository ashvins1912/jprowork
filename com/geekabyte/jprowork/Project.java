package com.geekabyte.jprowork;

import com.geekabyte.jprowork.apiendpoints.APIendpoint;
import com.geekabyte.jprowork.exceptions.APIerrorMessage;
import com.geekabyte.jprowork.exceptions.InvalidParameterException;
import com.geekabyte.jprowork.exceptions.InvalidTokenException;
import com.geekabyte.jprowork.exceptions.JproworkRuntimeException;
import com.geekabyte.jprowork.exceptions.RemoteAPIHandlerException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.Map;

/**
 * The Java object representation of a Project on Prowork.
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

    /**
     * Method return the project ID.
     *
     * @return the project ID.
     */
    public int getProjectId() {
        return id;
    }

    /**
     * Method returns the workspace ID of the project. 
     *
     * @return the workspace ID of the project.
     */
    public int getWorkSpaceId() {
        return workspace_id;
    }

    /**
     * Method returns the workspace name of the project. 
     *
     * @return the workspace name of the project.
     */
    public String getWorkSpaceName() {
        return workspace_name;
    }

    /**
     * Method returns the number of member belonging to the project. 
     *
     * @return the number of members belonging to the project.
     */
    public int getMemberCount() {
        return member_count;
    }

    /**
     * Method returns the number of tasks belonging to the project.
     *
     * @return the number of tasks belonging to the project.
     */
    public int getTaskCount() {
        return task_count;
    }

    /**
     * Method returns Project Name. 
     *
     * @return Project name.
     */
    public String getProjectName() {
        return name;
    }

    /**
     * Method returns Project description. 
     *
     * @return Project description.
     */
    public String getProjectDescription() {
        return description;
    }

    /**
     * Method returns administrator count. 
     *
     * @return administrator count.
     */
    public int getAdmin() {
        return admin;
    }

    /**
     * Method returns the created date of a project. 
     *
     * @return the created date of a project as String.
     */
    public String getCreatedDate() {
        // TODO convert string representation to Date object
        return this.created_date;
    }

    /**
     * Method to get a list of unread activities associated with this project
     * object. 
     *
     * @throws JproworkRuntimeException if an error occurs while retrieving
     * projects Activities.
     * @return List<Activity> A list of Prowork Activity objects
     * @see Activity
     */
    public List<Activity> getProjectActivity() throws JproworkRuntimeException {

        String token = RemoteAPIHandler.getToken();
        if (token == null) {
            throw new InvalidTokenException("Invalid token or none given");
        }

        String responseJson;
        String params = "token=" + token;
        params += "&";
        params += "project_id=" + this.getProjectId();


        try {
            responseJson = RemoteAPIHandler.getJsonFromAPIEndPoint(APIendpoint.getProjectActivities, params);
        } catch (Exception e) {
            throw new RemoteAPIHandlerException("Error calling API Endpoint", e);
        }


        Gson gson = new Gson();

        List<Activity> resultList = gson.fromJson(responseJson,
                new TypeToken<List<Activity>>() {
        }.getType());

        return resultList;
    }

    /**
     * Method to get a list of activities associated with this project object. 
     *
     * @param read Optional. By default, only unread activity messages are
     * returned. To include read messages, set this parameter to 1
     * @throws JproworkRuntimeException if an error occurs while retrieving
     * projects Activities.
     * @return List<Activity> A list of Prowork Activity objects
     * @see Activity
     */
    public List<Activity> getProjectActivity(int read) throws JproworkRuntimeException {

        if (read != 1 && read != 0) {
            throw new InvalidParameterException("read parameter should be 1 or 0");
        }

        String token = RemoteAPIHandler.getToken();
        if (token == null) {
            throw new InvalidTokenException("Invalid token or none given");
        }

        String responseJson;
        String params = "token=" + token;
        params += "&";
        params += "project_id=" + this.getProjectId();
        params += "&";
        params += "read=" + read;

        try {
            responseJson = RemoteAPIHandler.getJsonFromAPIEndPoint(APIendpoint.getProjectActivities, params);
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
     * Adds a single member to a project. 
     *
     * @throws JproworkRuntimeException
     * @return Boolean. True if member is added successfully
     * @see Member
     */
    public Boolean addMember(String email) {
        return this.addMembers(new String[]{email});
    }

    /**
     * Adds list of members to a project. 
     *
     * @throws JproworkRuntimeException
     * @return Boolean. True if members are added successfully
     * @see Member
     */
    public Boolean addMembers(String[] emails) throws JproworkRuntimeException {

        String token = RemoteAPIHandler.getToken();
        if (token == null) {
            throw new InvalidTokenException("Invalid token or none given");
        }

        String responseJson = null;
        String params = "token=" + token;
        params += "&";
        params += "project_id=" + this.getProjectId();
        params += "&";


        // Convert array to comma seperated string
        String emailString = "";
        int count = 0;
        for (String email : emails) {
            if (count == 0) {
                emailString += email;
                count++;
                continue;
            }
            emailString += "," + email;
            count++;
        }

        params += "emails=" + emailString;

        try {
            responseJson = RemoteAPIHandler.postToAPIEndPoint(APIendpoint.addProjectMembers, params);
        } catch (Exception e) {
            throw new RemoteAPIHandlerException("Error occured while performing post operation to API Endpoint.", e);
        }


        Gson gson = new Gson();

        Map<String, String> resultMap = gson.fromJson(responseJson,
                new TypeToken<Map<String, String>>() {
        }.getType());

        if (resultMap.get("status") != null && resultMap.get("status").equals("done")) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Removes a member from a project. 
     *
     * @throws JproworkRuntimeException
     * @return Boolean. true on successful removal of member.
     * @see Member
     */
    public Boolean removeMember(int memberid) throws JproworkRuntimeException {
        String token = RemoteAPIHandler.getToken();
        if (token == null) {
            throw new InvalidTokenException("Invalid token or none given");
        }

        String responseJson = null;
        String params = "token=" + token;
        params += "&";
        params += "member_id=" + memberid;
        params += "&";
        params += "project_id=" + this.getProjectId();

        try {
            responseJson = RemoteAPIHandler.getJsonFromAPIEndPoint(APIendpoint.removeProjectMember, params);
        } catch (Exception e) {
            throw new RemoteAPIHandlerException("Error occured while calling API Endpoint", e);
        }

        Gson gson = new Gson();

        Map<String, String> resultList = gson.fromJson(responseJson,
                new TypeToken<Map<String, String>>() {
        }.getType());

        if (resultList.get("status").equals("done")) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Returns a list of members of a project. 
     *
     * @throws JproworkRuntimeException
     * @return A list of members.
     * @see Member
     */
    public List<Member> getProjectMembers() throws JproworkRuntimeException {
        String token = RemoteAPIHandler.getToken();
        if (token == null) {
            throw new InvalidTokenException("Invalid token or none given");
        }

        String responseJson = null;
        String params = "token=" + token;
        params += "&";
        params += "project_id=" + this.getProjectId();

        try {
            responseJson = RemoteAPIHandler.getJsonFromAPIEndPoint(APIendpoint.getProjectMembers, params);
        } catch (Exception e) {
            throw new RemoteAPIHandlerException("Error occured while calling API Endpoint", e);
        }
        Gson gson = new Gson();

        List<Member> resultList = gson.fromJson(responseJson,
                new TypeToken<List<Member>>() {
        }.getType());


        return resultList;
    }

    /**
     * Returns a list of tasks. 
     *
     * @throws JproworkRuntimeException
     * @return a list of Task object.
     * @see Task
     */
    public List<Task> getTasks() throws JproworkRuntimeException {
        String token = RemoteAPIHandler.getToken();
        if (token == null) {
            throw new InvalidTokenException("Invalid token or none given");
        }

        String responseJson = null;
        String params = "token=" + token;
        params += "&";
        params += "project_id=" + this.getProjectId();

        try {
            responseJson = RemoteAPIHandler.getJsonFromAPIEndPoint(APIendpoint.getProjectTasks, params);
        } catch (Exception e) {
            throw new RemoteAPIHandlerException("Error occured while calling API Endpoint.", e);
        }

        Gson gson = new Gson();

        List<Task> resultList = gson.fromJson(responseJson,
                new TypeToken<List<Task>>() {
        }.getType());

        // Workaround till API call returns projectId
        for (Task tsk : resultList) {
            tsk.setProjectId(this.getProjectId());
        }

        return resultList;
    }

    /**
     * Returns a task. 
     * 
     * @param taskID the ID of the task to be retrieved
     * @throws JproworkRuntimeException
     * @return Task object.
     * @see Task
     */
    public Task getTask(int taskId) throws JproworkRuntimeException {

        String token = RemoteAPIHandler.getToken();
        if (token == null) {
            throw new InvalidTokenException("Invalid token or none given");
        }

        String responseJson = null;
        String params = "token=" + token;
        params += "&";
        params += "project_id=" + this.getProjectId();
        params += "&";
        params += "task_id=" + taskId;

        try {
            responseJson = RemoteAPIHandler.getJsonFromAPIEndPoint(APIendpoint.getProjectTask, params);
        } catch (Exception e) {
            throw new RemoteAPIHandlerException("Error occured while calling API Endpoint", e);
        }

        Gson gson = new Gson();

        Task task = gson.fromJson(responseJson, Task.class);


        // Workaround till API call returns projectId
        task.setProjectId(this.getProjectId());
        return task;

    }

    /**
     * Create a new task. 
     *
     * @param title the title of the new task.
     * @param dueDate the due date of task added.
     * @throws JproworkRuntimeException
     * @return Task object created
     * @see Task
     */
    public Task addTask(String title, String dueDate) throws JproworkRuntimeException {
        String token = RemoteAPIHandler.getToken();
        if (token == null) {
            throw new InvalidTokenException("Invalid token or none given");
        }

        String responseJson = null;
        String params = "token=" + token;
        params += "&";
        params += "project_id=" + this.getProjectId();
        params += "&";
        params += "title=" + title;
        params += "&";
        params += "date=" + dueDate;

        try {
            responseJson = RemoteAPIHandler.postToAPIEndPoint(APIendpoint.addProjectTask, params);
        } catch (Exception e) {
            throw new RemoteAPIHandlerException("Error occured while performing POST operation to API Endpoint.", e);
        }

        Gson gson = new Gson();
        Map<String, String> resultMap = gson.fromJson(responseJson,
                new TypeToken<Map<String, String>>() {
        }.getType());

        if (resultMap == null) {
            throw new APIerrorMessage("Conversion from Json string returned null");
        } else if (resultMap.get("error") != null) {
            throw new APIerrorMessage(resultMap.get("error"));
        } else {
            // create a new task object and return
            int taskId = Integer.parseInt(resultMap.get("id"));
            Task newTask = this.getTask(taskId);

            // Workaround till API call returns projectId
            newTask.setProjectId(this.getProjectId());
            return newTask;
        }

    }

    /**
     * Create a new task. 
     *
     * @param title the title of the new task.
     * @throws JproworkRuntimeException
     * @return Task object created
     * @see Task
     */
    public Task addTask(String title) throws JproworkRuntimeException {
        String token = RemoteAPIHandler.getToken();
        if (token == null) {
            throw new InvalidTokenException("Invalid token or none given");
        }

        String responseJson = null;
        String params = "token=" + token;
        params += "&";
        params += "project_id=" + this.getProjectId();
        params += "&";
        params += "title=" + title;

        try {
            responseJson = RemoteAPIHandler.postToAPIEndPoint(APIendpoint.addProjectTask, params);
        } catch (Exception e) {
            throw new RemoteAPIHandlerException("Error occured while Posting to the API Endpoint", e);
        }

        Gson gson = new Gson();
        Map<String, String> resultMap = gson.fromJson(responseJson,
                new TypeToken<Map<String, String>>() {
        }.getType());

        if (resultMap == null) {
            throw new APIerrorMessage("Conversion from Json string returned null");
        } else if (resultMap.get("error") != null) {
            throw new APIerrorMessage(resultMap.get("error"));
        } else {
            // create a new task object and return
            int taskId = Integer.parseInt(resultMap.get("id"));
            Task newTask = this.getTask(taskId);

            // Workaround till API call returns projectId
            newTask.setProjectId(this.getProjectId());
            return newTask;
        }

    }

    /**
     * Delete a task. 
     *
     * @param taskId the ID of the task to be deleted.
     * @throws JproworkRuntimeException
     * @return Boolean. True if task is successfully deleted and false if not
     * @see Task
     */
    public Boolean deleteTask(int taskId) throws JproworkRuntimeException {
        String token = RemoteAPIHandler.getToken();
        if (token == null) {
            throw new InvalidTokenException("Invalid token or none given");
        }


        String responseJson;;
        String params = "token=" + token;
        params += "&";
        params += "project_id=" + this.getProjectId();
        params += "&";
        params += "task_id=" + taskId;

        try {
            responseJson = RemoteAPIHandler.getJsonFromAPIEndPoint(APIendpoint.deleteProjectTask, params);
        } catch (Exception e) {
            throw new RemoteAPIHandlerException("Error occured while calling the API Endpoint", e);
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
}
