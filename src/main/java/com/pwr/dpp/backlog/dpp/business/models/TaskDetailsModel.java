package com.pwr.dpp.backlog.dpp.business.models;

import com.pwr.dpp.backlog.dpp.business.DatabaseHandler;
import com.pwr.dpp.backlog.dpp.business.LoggedUserRepository;
import com.pwr.dpp.backlog.dpp.business.orm.Category;
import com.pwr.dpp.backlog.dpp.business.orm.Comment;
import com.pwr.dpp.backlog.dpp.business.orm.Task;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskDetailsModel implements Observable {
    private Task task;
    private DatabaseHandler databaseHandler;
    private final List<InvalidationListener> listeners = new LinkedList<>();
    private LoggedUserRepository loggedUserRepository;
    private boolean invalidatedComments = true;

    private List<Comment> comments;

    public List<String> getUsers(){
        return databaseHandler.getAllUsers();
    }

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

    public String getAssignedUser(){
        return task.getUser() == null ? "" : task.getUser();
    }

    public String getDescription(){
        return task.getDescription();
    }

    public String getTaskTitle(){
        return task.getName();
    }

    public String getTimeCreated(){
        return task.getTimeCreated()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public Category getStatus(){
        return task.getCategory();
    }

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

    public void deleteTask(){
        databaseHandler.deleteTask(task);
        task = null;
    }

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

    private void invalidateComments(){
        invalidatedComments = true;
        invalidate();
    }

    public List<Comment> getComments(){
        if(invalidatedComments){
            comments = databaseHandler.getAllComments()
                    .stream()
                    .filter(comment -> comment.getTask() == task)
                    .sorted()
                    .collect(Collectors.toList());
            invalidatedComments = false;
        }
        return comments;
    }

    public DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }

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

    public void setLoggedUserRepository(LoggedUserRepository loggedUserRepository) {
        this.loggedUserRepository = loggedUserRepository;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
