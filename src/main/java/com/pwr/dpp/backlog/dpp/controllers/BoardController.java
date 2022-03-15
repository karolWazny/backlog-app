package com.pwr.dpp.backlog.dpp.controllers;

import com.pwr.dpp.backlog.dpp.SceneController;
import com.pwr.dpp.backlog.dpp.business.ApplicationSetup;
import com.pwr.dpp.backlog.dpp.business.MainController;
import com.pwr.dpp.backlog.dpp.business.orm.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class BoardController {
    private MainController mainController;

    public BoardController() {
        if(SceneController.getMainController() == null){
            SceneController.setMainController(ApplicationSetup.setup());
        }
        this.mainController = SceneController.getMainController();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void onShowTestTask(ActionEvent event){
        Task t = mainController.getBoardModel().getClosed().get(0);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            SceneController.switchToTaskDetailsScene(stage, t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
