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

    /**
     * Method for getting the username that was typed into the login field.
     * @return value of the username field
     */
    public String getUsername() {
        return this.usernameField.getText();
    }

    /**
     * Initializes the component
     */
    @FXML
    public void initialize(){
        usernameField.setOnKeyPressed((KeyEvent event)->{
            if(event.getCode() == KeyCode.ENTER){
                signIn(event);
            }
        });
    }

    /**
     * Sends an information about a login attempt to the MainController object.
     * If login is successful, changes the view to the team board.
     * If the login was not successful, user does not get logged in and the view is not switched.
     * @param event Event that is fired when user tries to sign in, either by pressing the ENTER key or clicking on the sign in button.
     */
    @FXML
    public void signIn(Event event) {
        String username = this.getUsername();
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
            System.out.println("User does not exist");
        }
    }
}
