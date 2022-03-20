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

    public void goBack(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            SceneController.switchToBoardScene(stage);
        } catch (Exception e) {
            System.out.println("An error occurred when trying to go back to the board.");
        }
    }

    public void createTaskClick(ActionEvent event){
        checkForObligatoryFields();
        CreateTaskModel model = mainController.getCreateTaskModel();
        CreateTaskInfo info = new CreateTaskInfo();
        info.setTitle(titleField.getText());
        info.setDescription(titleField.getText());
        info.setCategory(Category.OPEN);
        info.setUsername("unassigned");
        model.createTask(info);
        goBack(event);
    }

    private void checkForObligatoryFields(){
        if(titleField.textProperty().get().equals("") || titleField.textProperty().isEmpty().get()){
            throw new RuntimeException("Title field cannot be empty!");
        }
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

}
