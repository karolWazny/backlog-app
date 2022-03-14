package com.pwr.dpp.backlog.dpp.business;

public class LoggedUserRepository {
    private static final LoggedUserRepository instance = new LoggedUserRepository();
    private String loggedUser;

    public static LoggedUserRepository getInstance(){
        return instance;
    }

    public String getLoggedUser(){
        return loggedUser;
    }

    public boolean logAs(String user){
        loggedUser = user;
        return true;
    }
}
