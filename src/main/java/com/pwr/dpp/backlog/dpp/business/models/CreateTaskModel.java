package com.pwr.dpp.backlog.dpp.business.models;

import com.pwr.dpp.backlog.dpp.business.DatabaseHandler;
import com.pwr.dpp.backlog.dpp.business.orm.Task;

import java.util.Date;
import java.util.List;

public class CreateTaskModel {
    private DatabaseHandler databaseHandler;

    public List<String> getUsers(){
        return databaseHandler.getUsers();
    }

    public void createTask(CreateTaskInfo info){
        Task task = new Task();
        task.setTimeCreated(new Date(System.currentTimeMillis()));
        task.setCategory(info.getCategory());
        task.setUser(info.getUsername());
        task.setDescription(info.getDescription());
        task.setName(info.getTitle());
        databaseHandler.saveTask(task);
    }

    public DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }

    public void setDatabaseHandler(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }
}
