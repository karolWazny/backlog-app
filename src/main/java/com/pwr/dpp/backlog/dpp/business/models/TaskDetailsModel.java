package com.pwr.dpp.backlog.dpp.business.models;

import com.pwr.dpp.backlog.dpp.business.DatabaseHandler;
import com.pwr.dpp.backlog.dpp.business.LoggedUserRepository;
import com.pwr.dpp.backlog.dpp.business.orm.Category;
import com.pwr.dpp.backlog.dpp.business.orm.Comment;
import com.pwr.dpp.backlog.dpp.business.orm.Task;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Provides methods which are used to access the data in task details scene.
 */
public class TaskDetailsModel implements Observable {
    private Task task;
    private DatabaseHandler databaseHandler;
    private final List<InvalidationListener> listeners = new LinkedList<>();
    private LoggedUserRepository loggedUserRepository;
    private boolean invalidatedComments = true;

    private List<Comment> comments;

    public TaskDetailsModel(Task task) {
        this.task = task;
    }

    public List<String> getUsers(){
        return databaseHandler.getUsers();
    }

    /**
     * Assigns task to given user
     * @param username username
     */
    public void assignUser(String username){
        String currentAssignee = task.getUser();
        task.setUser(username);
        try{
            databaseHandler.saveTask(task);
            invalidate();
        } catch (Exception e){
            task.setUser(currentAssignee);
        }
    }

    /**
     * Returns assigned username.
     * @return assigned username.
     */
    public String getAssignedUser(){
        return task.getUser() == null ? "" : task.getUser();
    }

    /**
     * Returns task description.
     * @return task description.
     */
    public String getDescription(){
        return task.getDescription();
    }

    /**
     * Updates task description
     * @param description description.
     */
    public void setDescription(String description){
        String currentDescription = task.getDescription();
        task.setDescription(description);
        try{
            databaseHandler.saveTask(task);
            invalidate();
        } catch (Exception e){
            task.setDescription(currentDescription);
        }
    }

    /**
     * Returns task title.
     * @return task title.
     */
    public String getTaskTitle(){
        return task.getName();
    }

    /**
     * Returns task creation time.
     * @return task creation time.
     */
    public String getTimeCreated(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateFormat.format(task.getTimeCreated());
    }

    /**
     * Returns task category.
     * @return task category.
     */
    public Category getStatus(){
        return task.getCategory();
    }

    /**
     * Updates task category.
     * @param category category to set.
     */
    public void setStatus(Category category){
        Category currentCategory = task.getCategory();
        task.setCategory(category);
        try{
            databaseHandler.saveTask(task);
            invalidate();
        } catch (Exception e){
            task.setCategory(currentCategory);
        }
    }

    /**
     * Deletes task.
     */
    public void deleteTask(){
        databaseHandler.deleteTask(task);
        task = null;
    }

    /**
     * Adds comment to task.
     * @param content comment content.
     */
    public void addComment(String content){
        String user = loggedUserRepository.getLoggedUser();
        Comment comment = new Comment();
        comment.setTask(this.task);
        comment.setContent(content);
        comment.setAuthor(user);
        try{
            databaseHandler.saveComment(comment);
            invalidateComments();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Invalidates comments.
     */
    private void invalidateComments(){
        invalidatedComments = true;
        invalidate();
    }

    /**
     * Returns all task comments.
     * @return {@link List} of {@link Comment} assigned to task.
     */
    public List<Comment> getComments(){
        if(invalidatedComments){
            comments = databaseHandler.getCommentsForTask(task.getId());
            invalidatedComments = false;
        }
        return comments;
    }

    /**
     * Returns database handler.
     * @return {@link DatabaseHandler}
     */
    public DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }

    /**
     * Sets database handler to given {@link DatabaseHandler}.
     * @param databaseHandler {@link DatabaseHandler} to set.
     */
    public void setDatabaseHandler(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }

    @Override
    public void addListener(InvalidationListener invalidationListener) {
        listeners.add(invalidationListener);
    }

    @Override
    public void removeListener(InvalidationListener invalidationListener) {
        listeners.remove(invalidationListener);
    }


    public void invalidate(){
        for (InvalidationListener listener:
                listeners) {
            listener.invalidated(this);
        }
    }

    /**
     * Sets logged user repository.
     * @param loggedUserRepository {@link LoggedUserRepository} to set.
     */
    public void setLoggedUserRepository(LoggedUserRepository loggedUserRepository) {
        this.loggedUserRepository = loggedUserRepository;
    }

    /**
     * Sets task.
     * @param task {@link Task} to set.
     */
    public void setTask(Task task) {
        this.task = task;
    }

    /**
     * Returns task.
     */
    public Task getTask() {
        return task;
    }
}
