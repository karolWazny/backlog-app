package com.pwr.dpp.backlog.dpp.business;

/**
 * Handles user login.
 */
public class LoggedUserRepositoryImpl implements LoggedUserRepository {
    private static final LoggedUserRepository instance = new LoggedUserRepositoryImpl();
    private String loggedUser;

    /**
     * @return {@link LoggedUserRepository instance.}
     */
    public static LoggedUserRepository getInstance(){
        return instance;
    }

    /**
     * @return logged user represented by {@link String} value.
     */
    @Override
    public String getLoggedUser(){
        return loggedUser;
    }

    /**
     * Sets logged user
     * @param user user to login
     * @return true if operation succeeded.
     */
    @Override
    public boolean setLoggedUser(String user){
        loggedUser = user;
        return true;
    }
}
