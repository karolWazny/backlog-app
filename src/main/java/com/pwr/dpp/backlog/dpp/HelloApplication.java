package com.pwr.dpp.backlog.dpp;

import com.pwr.dpp.backlog.dpp.business.JsonDatabaseHandler;
import com.pwr.dpp.backlog.dpp.business.ApplicationSetup;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginScene.fxml"));
        Parent root = fxmlLoader.load();
        SceneController.setMainController(ApplicationSetup.setup());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        JsonDatabaseHandler dh = new JsonDatabaseHandler(System.getProperty("user.dir") + File.separator + "jsonDatabase", "tasks", "comments", "users");
        dh.saveDefaultDataBase();
        launch();
    }
}