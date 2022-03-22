package com.pwr.dpp.backlog.dpp.business;

import com.pwr.dpp.backlog.dpp.business.orm.Comment;
import com.pwr.dpp.backlog.dpp.business.orm.Task;

import java.util.List;

/**
 * Provides connection with database.
 */
public interface DatabaseHandler {
    /**
     * Returns loaded tasks.
     * @return {@link List} of tasks.
     */
    List<Task> getTasks();
    /**
     * Adds given {@link Task} to tasks {@link List}, if task with the same id exists, it overwrites existing task.
     * @param task
     */
    void saveTask(Task task);
    /**
     * Removes given task from tasks {@link List} if task with the same id exists.
     * @param task task to remove-
     */
    void deleteTask(Task task);
    /**
     * Returns comments related to task with given task ID.
     *
     * @param taskId task ID.
     * @return comments related to task with given task ID.
     */
    List<Comment> getCommentsForTask(Integer taskId);
    /**
     * Returns all loaded comments.
     *
     * @return all loaded comments.
     */
    List<Comment> getComments();
    /**
     * Adds given {@link Comment} to comments {@link List}, if comment with the same id exists, it overwrites existing commment.
     * @param comment comment to add/update.
     */
    void saveComment(Comment comment);
    /**
     * Returns stored users list.
     *
     * @return stored users list.
     */
    List<String> getUsers();
    /**
     * Creates new user with given username unless user with given username exists.
     *
     * @param username
     * @return True if new user was created, false otherwise.
     */
    boolean createUser(String username);
    /**
     * Checks if user with given username exists.
     *
     * @param username
     * @return True if user exists, false otherwise.
     */
    boolean logAs(String username);
    /**
     * Saves tasks, comments and users to location data files given in {@link JsonDatabaseHandler} constructor.
     */
    void save();
    /**
     * Loads tasks, comments and users from memory if data files given in {@link JsonDatabaseHandler} constructor exist.
     */
    void load();

}
