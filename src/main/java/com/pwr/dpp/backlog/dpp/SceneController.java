package com.pwr.dpp.backlog.dpp;

import com.pwr.dpp.backlog.dpp.business.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchScene(Stage stage, String sceneFxml) throws Exception {
        this.root = FXMLLoader.load(getClass().getResource(sceneFxml));
        this.stage = stage;
        this.scene = new Scene(root);
        this.stage.setScene(scene);
        this.stage.show();
    }

    public void switchToLoginScene(Stage stage) throws Exception {
        this.switchScene(stage, "LoginScene.fxml");
        stage.setTitle("Backlogger - Sign in");
    }

    public void switchToBoardScene(Stage stage) throws Exception {
        this.switchScene(stage, "BoardScene.fxml");
        stage.setTitle("Backlogger - Team board");
    }

    public void switchToTaskDetailsScene(ActionEvent event) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void switchToNewTaskScene(ActionEvent event) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
