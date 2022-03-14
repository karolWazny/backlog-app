package com.pwr.dpp.backlog.dpp.business;

public class NoSuchUserException extends Exception {
    public NoSuchUserException(){
        super("There is no user with that username in the database!");
    }
}
