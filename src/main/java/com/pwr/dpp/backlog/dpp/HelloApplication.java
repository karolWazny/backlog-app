package com.pwr.dpp.backlog.dpp;

import com.pwr.dpp.backlog.dpp.business.JsonDatabaseHandler;
import com.pwr.dpp.backlog.dpp.business.ApplicationSetup;
import com.pwr.dpp.backlog.dpp.business.MainController;
import com.pwr.dpp.backlog.dpp.controllers.LoginController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginScene.fxml"));
        Parent root = fxmlLoader.load();
        MainController mainController = ApplicationSetup.setup();
        SceneController.setMainController(mainController);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        //saving database on close request
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                System.out.println("Saving changes.");
                mainController.getDatabaseHandler().save();
            }
        });
        stage.show();
    }

    public static void main(String[] args) {
        JsonDatabaseHandler dh = new JsonDatabaseHandler(System.getProperty("user.dir") + File.separator + "jsonDatabase", "tasks", "comments", "users");
        launch();
    }
}