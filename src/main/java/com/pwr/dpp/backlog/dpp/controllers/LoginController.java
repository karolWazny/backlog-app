package com.pwr.dpp.backlog.dpp.controllers;

import com.pwr.dpp.backlog.dpp.SceneController;
import com.pwr.dpp.backlog.dpp.business.ApplicationSetup;
import com.pwr.dpp.backlog.dpp.business.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    private MainController mainController;
    private SceneController sceneController;

    @FXML
    private TextField usernameField;

    public LoginController() {
        this.mainController = ApplicationSetup.setup();
        this.sceneController = new SceneController();
        usernameField = new TextField();
    }

    public String getUsername() {
        return this.usernameField.getText();
    }

    @FXML
    public void signIn(ActionEvent event) throws Exception {
        String username = this.getUsername();
        System.out.println("username: ");
        System.out.println(username);
        System.out.println("Trying to log in");
        try {
            boolean loginResult = this.mainController.getLogInModel().logAs(username);
            if (loginResult) {
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                this.sceneController.switchToBoardScene(stage);
            } else {
                throw new RuntimeException("Could not log in");
            }
        } catch (Exception exception) {
            // TODO: replace this with a better mechanism
            // this.mainController.getLogInModel().createUser(username);
            // this.mainController.getLogInModel().logAs(username);
            System.out.println("User does not exist");
        }
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


}
