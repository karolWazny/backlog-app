package com.pwr.dpp.backlog.dpp.controllers;

import com.pwr.dpp.backlog.dpp.business.LogInModel;
import com.pwr.dpp.backlog.dpp.business.NoSuchUserException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    private LogInModel loginModel;
    private SceneController sceneController;

    @FXML
    private TextField usernameField;

    public LoginController() {
        this.loginModel = new LogInModel();
        this.sceneController = new SceneController();
        usernameField = new TextField();
    }

    public String getUsername() {
        return this.usernameField.getText();
    }

    @FXML
    public void signIn(ActionEvent event) throws Exception {
        String username = this.getUsername();
        System.out.println("Trying to log in");
        try {
            boolean loginResult = this.loginModel.logAs(username);
            if (loginResult) {
                System.out.println("SUCCESS!");
                // TODO: redirect to board view
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                this.sceneController.switchToLoginScene(stage);
                // System.out.println((Stage)((Node)event.getSource()).getScene().getWindow());
                // this.sceneController.switchToLoginScene(stage);
            } else {
                System.out.println("could not log in");
                throw new RuntimeException("Could not log in");
            }
        } catch (Exception exception) {
            System.out.println("Exception caught");
            System.out.println(exception);
            if (exception instanceof NoSuchUserException) {
                System.out.println("Was No such user exc");
                this.loginModel.createUser(username);
                this.loginModel.logAs(username);
            }
        }
    }

}
