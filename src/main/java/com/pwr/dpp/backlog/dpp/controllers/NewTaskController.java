package com.pwr.dpp.backlog.dpp.controllers;

import com.pwr.dpp.backlog.dpp.SceneController;
import com.pwr.dpp.backlog.dpp.business.ApplicationSetup;
import com.pwr.dpp.backlog.dpp.business.MainController;

public class NewTaskController {
    private MainController mainController;

    public NewTaskController(){
        if(SceneController.getMainController() == null){
            SceneController.setMainController(ApplicationSetup.setup());
        }
        this.mainController = SceneController.getMainController();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

}
