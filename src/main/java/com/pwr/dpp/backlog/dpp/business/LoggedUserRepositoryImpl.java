package com.pwr.dpp.backlog.dpp.business;

public class LoggedUserRepositoryImpl implements LoggedUserRepository {
    private static final LoggedUserRepository instance = new LoggedUserRepositoryImpl();
    private String loggedUser;

    public static LoggedUserRepository getInstance(){
        return instance;
    }

    @Override
    public String getLoggedUser(){
        return loggedUser;
    }

    @Override
    public boolean setLoggedUser(String user){
        loggedUser = user;
        return true;
    }
}
