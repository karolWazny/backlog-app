package com.pwr.dpp.backlog.dpp.business.models;

import com.pwr.dpp.backlog.dpp.business.DatabaseHandler;
import com.pwr.dpp.backlog.dpp.business.LoggedUserRepository;
/**
 * Provides methods which are used to access the data in log in scene.
 */
public class LogInModel {
    private DatabaseHandler databaseHandler;
    private LoggedUserRepository loggedUserRepository;

    /**
     * Logs given user in.
     * @param user username
     * @return true if login succeeded, false otherwise.
     */
    public boolean logAs(String user){
        boolean logInSuccessful =  databaseHandler.logAs(user);
        if(logInSuccessful){
            loggedUserRepository.setLoggedUser(user);
        }
        return logInSuccessful;
    }

    /**
     * Creates user with given username.
     * @param username username
     */
    public void createUser(String username){
        if(!databaseHandler.createUser(username))
            throw new RuntimeException("Could not create user!");
    }

    public DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }

    public void setDatabaseHandler(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }

    public LoggedUserRepository getLoggedUserRepository() {
        return loggedUserRepository;
    }

    public void setLoggedUserRepository(LoggedUserRepository loggedUserRepository) {
        this.loggedUserRepository = loggedUserRepository;
    }
}
