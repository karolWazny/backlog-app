package com.pwr.dpp.backlog.dpp.business;

public interface LoggedUserRepository {
    String getLoggedUser();

    boolean setLoggedUser(String user);
}
