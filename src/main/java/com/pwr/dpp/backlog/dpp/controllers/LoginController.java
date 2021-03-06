package com.pwr.dpp.backlog.dpp.controllers;

import com.pwr.dpp.backlog.dpp.SceneController;
import com.pwr.dpp.backlog.dpp.business.ApplicationSetup;
import com.pwr.dpp.backlog.dpp.business.MainController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.security.Key;

public class LoginController {
    private MainController mainController;

    @FXML
    private TextField usernameField;

    public LoginController() {
        if(SceneController.getMainController()!=null) {
            this.mainController = SceneController.getMainController();
        }else{
            this.mainController = ApplicationSetup.setup();
            SceneController.setMainController(this.mainController);
        }
        usernameField = new TextField();
    }

    public String getUsername() {
        return this.usernameField.getText();
    }

    @FXML
    public void initialize(){
        usernameField.setOnKeyPressed((KeyEvent event)->{
            if(event.getCode() == KeyCode.ENTER){
                signIn(event);
            }
        });
    }

    @FXML
    public void signIn(Event event) {
        String username = this.getUsername();
        System.out.println("username: ");
        System.out.println(username);
        System.out.println("Trying to log in");
        try {
            boolean loginResult = this.mainController.getLogInModel().logAs(username);
            if (loginResult) {
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                SceneController.switchToBoardScene(stage);
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
