package com.pwr.dpp.backlog.dpp.business.models;

import com.pwr.dpp.backlog.dpp.business.DatabaseHandler;
import com.pwr.dpp.backlog.dpp.business.orm.Category;
import com.pwr.dpp.backlog.dpp.business.orm.Task;

import java.util.Date;
import java.util.List;
import java.util.Objects;
/**
 * Provides methods which are used to access the data in create new task scene.
 */
public class CreateTaskModel {
    private DatabaseHandler databaseHandler;

    public List<String> getUsers(){
        return databaseHandler.getUsers();
    }

    /**
     * Creates task basing on information given in given {@link CreateTaskInfo}
     * @param info {@link CreateTaskInfo} which should contain information about the task we want to create.
     */
    public void createTask(CreateTaskInfo info){
        Task task = new Task();
        task.setTimeCreated(new Date(System.currentTimeMillis()));
        if(info.getCategory() == null)
            task.setCategory(Category.OPEN);
        else
            task.setCategory(info.getCategory());
        if(info.getUsername() == null || info.getUsername().equals(""))
            task.setUser("Unassigned");
        else
            task.setUser(info.getUsername());
        if(info.getDescription() == null)
            task.setDescription("");
        else
            task.setDescription(info.getDescription());
        task.setName(Objects.requireNonNull(info.getTitle()));
        if(task.getName().trim().equals(""))
            throw new RuntimeException("Cannot create task with empty name!");
        databaseHandler.saveTask(task);
    }

    public DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }

    public void setDatabaseHandler(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }
}
