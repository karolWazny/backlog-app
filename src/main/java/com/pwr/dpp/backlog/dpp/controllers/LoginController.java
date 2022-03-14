package com.pwr.dpp.backlog.dpp.controllers;

import com.pwr.dpp.backlog.dpp.business.LogInModel;
import com.pwr.dpp.backlog.dpp.business.NoSuchUserException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController {
    LogInModel loginModel;
    @FXML
    private TextField usernameField;

    public LoginController() {
        this.loginModel = new LogInModel();
        usernameField = new TextField();
    }

    public String getUsername() {
        return this.usernameField.getText();
    }

    @FXML
    public void signIn(ActionEvent event) throws Exception {
        event.consume();
        String username = this.getUsername();
        System.out.println("Trying to log in");
        try {
            boolean loginResult = this.loginModel.logAs(username);
            if (loginResult) {
                System.out.println("SUCCESS!");
                // TODO: redirect to board view
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
