package com.pwr.dpp.backlog.dpp;

import com.pwr.dpp.backlog.dpp.business.ApplicationSetup;
import com.pwr.dpp.backlog.dpp.business.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * Main application class. Used to start application.
 */
public class HelloApplication extends Application {
    /**
     * Starts application
     */
    @Override
    public void start(Stage stage) throws IOException {
        MainController mainController = ApplicationSetup.setup();
        SceneController.setMainController(mainController);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginScene.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setOnCloseRequest((WindowEvent windowEvent) -> {
                System.out.println("Saving changes.");
                mainController.getDatabaseHandler().save();
            }
        );
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}