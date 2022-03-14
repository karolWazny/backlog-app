package com.pwr.dpp.backlog.dpp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToLoginScene(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToBoardScene(ActionEvent event) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void switchToTaskDetailsScene(ActionEvent event) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void switchToNewTaskScene(ActionEvent event) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
