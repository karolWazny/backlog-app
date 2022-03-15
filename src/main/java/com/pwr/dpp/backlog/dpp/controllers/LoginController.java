package com.pwr.dpp.backlog.dpp.controllers;

import com.pwr.dpp.backlog.dpp.SceneController;
import com.pwr.dpp.backlog.dpp.business.ApplicationSetup;
import com.pwr.dpp.backlog.dpp.business.MainController;
import com.pwr.dpp.backlog.dpp.business.orm.Task;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    private MainController mainController;

    @FXML
    private TextField usernameField;

    public LoginController() {
        if(SceneController.getMainController() == null){
            SceneController.setMainController(ApplicationSetup.setup());
        }
        this.mainController = SceneController.getMainController();
        usernameField = new TextField();
    }

    public String getUsername() {
        return this.usernameField.getText();
    }

    @FXML
    public void signIn(ActionEvent event) throws Exception {
        String username = this.getUsername();
        System.out.println("Trying to log in");
        boolean loginResult = this.mainController.getLogInModel().logAs(username);
        if (loginResult) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            SceneController.switchToBoardScene(stage);
        } else {
            this.mainController.getLogInModel().createUser(username);
            this.mainController.getLogInModel().logAs(username);
        }
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


}
