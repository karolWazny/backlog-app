package com.pwr.dpp.backlog.dpp.business;

import com.pwr.dpp.backlog.dpp.business.orm.Comment;
import com.pwr.dpp.backlog.dpp.business.orm.Task;

import java.io.File;
import java.util.List;

public class ApplicationSetup {
    public static MainController setup(){
        MainController mainController = new MainController();
        mainController.setDatabaseHandler(new JsonDatabaseHandler(System.getProperty("user.dir") + File.separator + "jsonDatabase", "tasks", "comments", "users"));
        mainController.setLoggedUserRepository(LoggedUserRepositoryImpl.getInstance());
        return mainController;
    }
}
