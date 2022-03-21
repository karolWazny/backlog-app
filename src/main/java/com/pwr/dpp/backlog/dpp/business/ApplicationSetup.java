package com.pwr.dpp.backlog.dpp.business;

import java.io.File;

public class ApplicationSetup {
    public static MainController setup(){
        MainController mainController = new MainController();
        mainController.setDatabaseHandler(new JsonDatabaseHandler(System.getProperty("user.dir") + File.separator + "jsonDatabase", "tasks", "comments", "users"));
        mainController.setLoggedUserRepository(LoggedUserRepositoryImpl.getInstance());
        return mainController;
    }
}
