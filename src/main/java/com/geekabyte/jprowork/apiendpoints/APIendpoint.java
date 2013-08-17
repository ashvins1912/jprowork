package com.geekabyte.jprowork.apiendpoints;

/**
 *
 * @author dade
 */
public class APIendpoint {

    private APIendpoint() {
    }
    /**
     * @see <a href="http://dev.prowork.me/accounts-login">Documentation on Prowork Web site </a>
     */
    public static String loginEndpoint = "http://api.prowork.me/session/get";
    /**
     * @see <a href="http://dev.prowork.me/accounts-register">Documentation on Prowork Web site</a>
     */
    public static String registerEndpoint = "http://api.prowork.me/session/register";
    /**
     * @see <a href="http://dev.prowork.me/member-avatar">Documentation on Prowork Web site</a>
     */
    public static String getAvatarEndpoint = "http://api.prowork.me/member/avatar";
    /**
     * @see <a href="http://dev.prowork.me/project-get">Documentation on Prowork Web site</a>
     */
    public static String getProjectEndpoint = "http://api.prowork.me/project/get";
    /**
     * @see <a href="http://dev.prowork.me/projects-get">Documentation on Prowork Web site</a>
     */
    public static String getProjectsEndpoint = "http://api.prowork.me/projects/get";
    /**
     * @see <a href="http://dev.prowork.me/project-get-activities">Documentation on Prowork Web site</a>
     */
    public static String getProjectActivities = "http://api.prowork.me/project/activities";
    /**
     * @see <a href="http://dev.prowork.me/project-new">Documentation on Prowork Web site</a>
     */
    public static String createNewProject = "http://api.prowork.me/project/new";
    /**
     * @see <a href="http://dev.prowork.me/project-delete">Documentation on Prowork Web site</a>
     */
    public static String deleteProject = "http://api.prowork.me/project/delete";
    /**
     * @see <a href="http://dev.prowork.me/accounts-notifications">Documentation on Prowork Web site</a>
     */
    public static String getNotificationCount = "http://api.prowork.me/session/notifications";
    /**
     * @see <a href="http://dev.prowork.me/project-members-add">Documentation on Prowork Web site</a>
     */
    public static String addProjectMembers = "http://api.prowork.me/project/members_add";
    /**
     * @see <a href="http://dev.prowork.me/project-members-get">Documentation on Prowork Web site</a>
     */
    public static String getProjectMembers = "http://api.prowork.me/project/members_get";
    /**
     * @see <a href="http://dev.prowork.me/project-members-remove">Documentation on Prowork Web site</a>
     */
    public static String removeProjectMember = "http://api.prowork.me/project/member_remove";
    /**
     * @see <a href="http://dev.prowork.me/accounts-activities">Documentation on Prowork Web site</a>
     */
    public static String getMemberProjectActivities = "http://api.prowork.me/session/get_activities";
    /**
     * @see <a href="http://dev.prowork.me/tasks-get">Documentation on Prowork Web site</a>
     */
    public static String getProjectTasks = "http://api.prowork.me/tasks/get";
    /**
     * @see <a href="http://dev.prowork.me/project-get">Documentation on Prowork Web site</a>
     */
    public static String getProjectTask = "http://api.prowork.me/task/get";
    /**
     * @see <a href="http://dev.prowork.me/task-new">Documentation on Prowork Web site</a>
     */
    public static String addProjectTask = "http://api.prowork.me/task/new";
    /**
     * @see <a href="http://dev.prowork.me/task-delete">Documentation on Prowork Web site</a>
     */
    public static String deleteProjectTask = "http://api.prowork.me/task/delete";
    /**
     * @see <a href="http://dev.prowork.me/task-set-status">Documentation on Prowork Web site</a>
     */
    public static String setTaskStatus = "http://api.prowork.me/task/set_status";
    /**
     * @see <a href="http://dev.prowork.me/task-members-add">Documentation on Prowork Web site</a>
     */
    public static String addTaskMembers = "http://api.prowork.me/task/members_add";
    /**
     * @see <a href="http://dev.prowork.me/task-members-get-unassigned">Documentation on Prowork Web site</a>
     */
    public static String getUnassignedMembers = "http://api.prowork.me/task/members_get_unassigned";
    /**
     * @see <a href="http://dev.prowork.me/push-subscribe">Documentation on Prowork Web site</a>
     */
    public static String subscribe = "http://api.prowork.me/notifications/subscribe";
    /**
     * @see <a href="http://dev.prowork.me/push-unsubscribe">Documentation on Prowork Web site</a>
     */
    public static String unsubscribe = "http://api.prowork.me/notifications/unsubscribe";
}
