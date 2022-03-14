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

    public void switchScene(Stage stage, String sceneFxml) throws Exception {
        try {
            this.root = FXMLLoader.load(getClass().getResource("../resources/com/pwr/dpp/backlog/dpp/" + sceneFxml));
        } catch (Exception e) {
            System.out.println("1");
            System.out.println(e);
        }
        this.stage = stage;
        try {
            this.scene = new Scene(root);
        } catch (Exception e) {
            System.out.println("2");
            System.out.println(e);
        }
        try {
            this.stage.setScene(scene);
        } catch (Exception e) {
            System.out.println("3");
            System.out.println(e);
        }
        try {
            this.stage.show();
        } catch (Exception e) {
            System.out.println("4");
            System.out.println(e);
        }
    }

    public void switchToLoginScene(Stage stage) throws Exception {
        this.switchScene(stage, "LoginScene.fxml");
    }

    public void switchToBoardScene(Stage stage) throws Exception {
        this.switchScene(stage, "BoardScene.fxml");
    }

    public void switchToTaskDetailsScene(ActionEvent event) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void switchToNewTaskScene(ActionEvent event) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
