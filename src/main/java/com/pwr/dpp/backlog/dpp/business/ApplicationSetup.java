package com.pwr.dpp.backlog.dpp.business;

import java.io.File;

/**
 * Used to setup {@link MainController}.
 */
public class ApplicationSetup {
    /**
     * Sets up {@link MainController}
     * @return {@link MainController} which is ready to use.
     */
    public static MainController setup(){
        MainController mainController = new MainController();
        mainController.setDatabaseHandler(new JsonDatabaseHandler(System.getProperty("user.dir") + File.separator + "jsonDatabase", "tasks", "comments", "users"));
        mainController.setLoggedUserRepository(LoggedUserRepositoryImpl.getInstance());
        return mainController;
    }
}
