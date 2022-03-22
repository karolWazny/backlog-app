package com.pwr.dpp.backlog.dpp.business;

/**
 * Handles user login.
 */
public interface LoggedUserRepository {
    /**
     * Returns currently logged user username.
     * @return currently logged user username.
     */
    String getLoggedUser();

    /**
     * Sets currently logged user.
     * @param user user to set.
     * @return true if login succeeded, false otherwise.
     */
    boolean setLoggedUser(String user);
}
