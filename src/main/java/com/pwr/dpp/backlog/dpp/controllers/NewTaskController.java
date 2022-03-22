package com.pwr.dpp.backlog.dpp.controllers;

import com.pwr.dpp.backlog.dpp.SceneController;
import com.pwr.dpp.backlog.dpp.business.ApplicationSetup;
import com.pwr.dpp.backlog.dpp.business.MainController;
import com.pwr.dpp.backlog.dpp.business.models.CreateTaskInfo;
import com.pwr.dpp.backlog.dpp.business.models.CreateTaskModel;
import com.pwr.dpp.backlog.dpp.business.orm.Category;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewTaskController {
    private MainController mainController;

    @FXML
    private TextField titleField;

    @FXML
    private TextArea descriptionField;

    public NewTaskController(){
        if(SceneController.getMainController()!=null) {
            this.mainController = SceneController.getMainController();
        }else{
            this.mainController = ApplicationSetup.setup();
            SceneController.setMainController(this.mainController);
        }
    }

    /**
     * Method that changes the current view back to the team board.
     * If an exception occurs, nothing happens.
     * @param event Event fired when user clicks on the 'Go back' button or the 'Cancel' button
     */
    public void goBack(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            SceneController.switchToBoardScene(stage);
        } catch (Exception e) {
            System.out.println("An error occurred when trying to go back to the board.");
        }
    }

    /**
     * Method responsible for creating the task.
     * Reads data from the form and, if all fields are filled in correctly, creates a new Task object based on it.
     * All new tasks are assigned to the OPEN category.
     * @param event Event fired when clicking on the 'Save' button
     */
    public void createTaskClick(ActionEvent event){
        checkForObligatoryFields();
        CreateTaskModel model = mainController.getCreateTaskModel();
        CreateTaskInfo info = new CreateTaskInfo();
        info.setTitle(titleField.getText());
        info.setDescription(descriptionField.getText());
        info.setCategory(Category.OPEN);
        info.setUsername("unassigned");
        model.createTask(info);
        goBack(event);
    }

    /**
     * Method that checks if all required fields are filled in.
     * If not, throws a RuntimeException.
     */
    private void checkForObligatoryFields(){
        if(titleField.textProperty().get().equals("") || titleField.textProperty().isEmpty().get()){
            throw new RuntimeException("Title field cannot be empty!");
        }
    }
}
