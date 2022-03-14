package com.pwr.dpp.backlog.dpp.business;

import java.util.List;

public class CreateTaskModel {
    private DatabaseHandler databaseHandler;

    public List<String> getUsers(){
        return databaseHandler.getAllUsers();
    }



    public DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }

    public void setDatabaseHandler(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }
}
