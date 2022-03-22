package com.pwr.dpp.backlog.dpp;

import com.pwr.dpp.backlog.dpp.business.MainController;
import com.pwr.dpp.backlog.dpp.business.models.TaskDetailsModel;
import com.pwr.dpp.backlog.dpp.business.orm.Task;
import com.pwr.dpp.backlog.dpp.controllers.BoardController;
import com.pwr.dpp.backlog.dpp.controllers.LoginController;
import com.pwr.dpp.backlog.dpp.controllers.NewTaskController;
import com.pwr.dpp.backlog.dpp.controllers.TaskDetailsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *  Used to switch between scenes.
 */
public class SceneController {
    private static Stage stage;
    private static Scene scene;
    private static Parent root;
    private static MainController mainController;
    private static FXMLLoader loader;

    /**
     * Switches scene in given stage to given scene.
     * @param s stage in which scene should be switched.
     * @param sceneFxml - FXML file with scene.
     * @throws Exception when FXML file cannot be read.
     */
    private static void switchScene(Stage s, String sceneFxml) throws Exception {
        loader = new FXMLLoader(SceneController.class.getResource(sceneFxml));
        root = loader.load();
        stage = s;
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Switches to login scene.
     * @param s stage in which scene should be switched.
     * @throws Exception when FXML file cannot be read.
     */
    public static void switchToLoginScene(Stage s) throws Exception {
        switchScene(s, "LoginScene.fxml");
        s.setTitle("Backlogger - Sign in");
    }
    /**
     * Switches to board scene.
     * @param s stage in which scene should be switched.
     * @throws Exception when FXML file cannot be read.
     */
    public static void switchToBoardScene(Stage s) throws Exception {
        switchScene(s, "BoardScene.fxml");
        s.setTitle("Backlogger - Team board");
    }
    /**
     * Switches to task details scene.
     * @param s stage in which scene should be switched.
     * @throws Exception when FXML file cannot be read.
     */
    public static void switchToTaskDetailsScene(Stage s, Task task) throws Exception {
        switchScene(s, "TaskDetailsScene.fxml");
        Object c = loader.getController();
        if(c instanceof TaskDetailsController){
            TaskDetailsController tc = (TaskDetailsController) c;
            tc.setTask(task);
        }
        s.setTitle("Backlogger - Task");
    }
    /**
     * Switches to new task scene.
     * @param s stage in which scene should be switched.
     * @throws Exception when FXML file cannot be read.
     */
    public static void switchToNewTaskScene(Stage s) throws Exception {
        switchScene(s, "NewTaskScene.fxml");
        s.setTitle("Backlogger - New task");
    }
    public static void setMainController(MainController mainController1){
        mainController = mainController1;
    }

    public static MainController getMainController() {
        return mainController;
    }
}
