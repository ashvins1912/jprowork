package com.geekabyte.jprowork;

import com.geekabyte.jprowork.apiendpoints.APIendpoint;
import com.geekabyte.jprowork.exceptions.InvalidTokenException;
import com.geekabyte.jprowork.exceptions.JproworkRuntimeException;
import com.geekabyte.jprowork.exceptions.RemoteAPIHandlerException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.Map;

/**
 * Task Represents Prowork Tasks
 *
 * @author dade
 */
public class Task {

    private String task_id;
    private String member_id;
    private String title;
    private String date;
    private int done;
    private String notes;
    private String status;
    private int projectId;

    // Work around as API does not return project Id
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getProjectId() {
        return this.projectId;
    }

    public Integer getTaskId() {
        return Integer.parseInt(task_id);
    }

    public Integer getMemberId() {
        return Integer.parseInt(member_id);
    }

    public String getTaskTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    /**
     * Checks if a task is finished or not.
     *
     * @return Boolean. True if a task is finished
     */
    public Boolean isDone() {
        switch (done) {
            case 0:
                return false;
            case 1:
                return true;
            default:
                return false;
        }
    }

    public Integer getNumberOfNotes() {
        return Integer.parseInt(notes);
    }

    /**
     * Set a task’s status to completed.
     *
     * @throws JproworkRuntimeException
     * @return Boolean. true on task status changed to done. False otherwise
     */
    public Boolean setTaskToDone() throws JproworkRuntimeException {
        String token = RemoteAPIHandler.getToken();
        if (token == null) {
            throw new InvalidTokenException("Invalid token or none given");
        }

        String doneStatus = "1"; // 1 -> done, 0 -> undone 

        String responseJson;
        String params = "token=" + token;
        params += "&";
        params += "project_id=" + this.getProjectId();
        params += "&";
        params += "task_id=" + this.getTaskId();
        params += "&";
        params += "done=" + doneStatus;
        try {
            responseJson = RemoteAPIHandler.getJsonFromAPIEndPoint(APIendpoint.setTaskStatus, params);
        } catch (Exception e) {
            throw new RemoteAPIHandlerException("Error while calling API Endpoint", e);
        }
        Gson gson = new Gson();


        Map<String, String> resultMap = gson.fromJson(responseJson,
                new TypeToken<Map<String, String>>() {
        }.getType());

        if (resultMap.containsKey("id")) {
            done = 1;
            return true;
        } else {
            return false;
        }



    }

    /**
     * Set a task’s status to not completed.
     *
     * @throws JproworkRuntimeException
     * @return Boolean. true on task status changed to undone. False otherwise
     */
    public Boolean setTaskToUnDone() throws JproworkRuntimeException {
        String token = RemoteAPIHandler.getToken();
        if (token == null) {
            throw new InvalidTokenException("Invalid token or none given");
        }

        String doneStatus = "0"; // 1 -> done, 0 -> undone 

        String responseJson;
        String params = "token=" + token;
        params += "&";
        params += "project_id=" + this.getProjectId();
        params += "&";
        params += "task_id=" + this.getTaskId();
        params += "&";
        params += "done=" + doneStatus;
        try {
            responseJson = RemoteAPIHandler.getJsonFromAPIEndPoint(APIendpoint.setTaskStatus, params);
        } catch (Exception e) {
            throw new RemoteAPIHandlerException("Error while calling API Endpoint", e);
        }

        Gson gson = new Gson();
        Map<String, String> resultMap = gson.fromJson(responseJson,
                new TypeToken<Map<String, String>>() {
        }.getType());

        if (resultMap.containsKey("id")) {
            done = 0;
            return true;
        } else {
            return false;
        }

    }

    /**
     * Add a member to a task.
     *
     * @param An array of Member IDs
     * @throws JproworkRuntimeException
     * @return Boolean. true on successful addition. False otherwise
     */
    public Boolean addMembers(int[] memberIds) throws JproworkRuntimeException {
        String token = RemoteAPIHandler.getToken();
        if (token == null) {
            throw new InvalidTokenException("Invalid token or none given");
        }


        // Convert array integer to comma seperated string
        String member_ids = "";
        int count = 0;
        for (int mId : memberIds) {
            if (count == 0) {
                member_ids += Integer.toString(mId);
                count++;
                continue;
            }
            member_ids += "," + Integer.toString(mId);
            count++;
        }

        String responseJson = null;
        String params = "token=" + token;
        params += "&";
        params += "project_id=" + this.getProjectId();
        params += "&";
        params += "task_id=" + this.getTaskId();
        params += "&";
        params += "member_ids=" + member_ids;


        try {
            responseJson = RemoteAPIHandler.postToAPIEndPoint(APIendpoint.addTaskMembers, params);

        } catch (Exception e) {
            throw new RemoteAPIHandlerException("Error while posting to API Endpoint", e);
        }


        Gson gson = new Gson();

        Map<String, String> resultMap = gson.fromJson(responseJson,
                new TypeToken<Map<String, String>>() {
        }.getType());

        if (resultMap.containsKey("ids")) {
            return true;
        } else {
            return false;
        }


    }

    /**
     * Remove members from a task
     *
     * @param An array of Member IDs
     * @throws JproworkRuntimeException
     * @return Boolean. true on successful removal. False otherwise
     */
    public Boolean removeMembers(int[] memberIds) throws JproworkRuntimeException {
        String token = RemoteAPIHandler.getToken();
        if (token == null) {
            throw new InvalidTokenException("Invalid token or none given");
        }


        // Convert array integer to comma seperated string
        String member_ids = "";
        int count = 0;
        for (int mId : memberIds) {
            if (count == 0) {
                member_ids += Integer.toString(mId);
                count++;
                continue;
            }
            member_ids += "," + Integer.toString(mId);
            count++;
        }

        String responseJson = null;
        String params = "token=" + token;
        params += "&";
        params += "project_id=" + this.getProjectId();
        params += "&";
        params += "task_id=" + this.getTaskId();
        params += "&";
        params += "member_ids=" + member_ids;


        try {
            responseJson = RemoteAPIHandler.getJsonFromAPIEndPoint(APIendpoint.addTaskMembers, params);

        } catch (Exception e) {
            throw new RemoteAPIHandlerException("Error while posting to API Endpoint", e);
        }


        Gson gson = new Gson();

        Map<String, String> resultMap = gson.fromJson(responseJson,
                new TypeToken<Map<String, String>>() {
        }.getType());

        if (resultMap.containsKey("status") && resultMap.get("status").equals("done")) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Get Unassigned Members of a Task. This can be used to display a list of
     * members to pick from to assign new members to a task.
     *
     * @throws JproworkRuntimeException
     * @return list of Prowork members that are unassigned
     * @see Member
     */
    public List<Member> getUnassignedMembers() throws JproworkRuntimeException {
        String token = RemoteAPIHandler.getToken();
        if (token == null) {
            throw new InvalidTokenException("Invalid token or none given");
        }

        String responseJson;
        String params = "token=" + token;
        params += "&";
        params += "task_id=" + this.getTaskId();
        try {
            responseJson = RemoteAPIHandler.getJsonFromAPIEndPoint(APIendpoint.getUnassignedMembers, params);
        } catch (Exception e) {
            throw new RemoteAPIHandlerException("Error while calling API Endpoint", e);

        }

        Gson gson = new Gson();
        List<Member> resultList = gson.fromJson(responseJson,
                new TypeToken<List<Member>>() {
        }.getType());

        return resultList;

    }
}
