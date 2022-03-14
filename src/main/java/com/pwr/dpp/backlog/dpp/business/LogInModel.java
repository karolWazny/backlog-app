package com.pwr.dpp.backlog.dpp.business;

public class LogInModel {
    private DatabaseHandler databaseHandler;
    public boolean logAs(String user) throws NoSuchUserException {
        return databaseHandler.logAs(user);
    }

    public void createUser(String username){
        if(!databaseHandler.createUser(username))
            throw new RuntimeException("Could not create user!");
    }
}
