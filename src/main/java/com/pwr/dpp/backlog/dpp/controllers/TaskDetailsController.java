package com.pwr.dpp.backlog.dpp.controllers;

import com.pwr.dpp.backlog.dpp.SceneController;
import com.pwr.dpp.backlog.dpp.business.ApplicationSetup;
import com.pwr.dpp.backlog.dpp.business.MainController;
import com.pwr.dpp.backlog.dpp.business.models.TaskDetailsModel;
import com.pwr.dpp.backlog.dpp.business.orm.Comment;
import com.pwr.dpp.backlog.dpp.business.orm.Task;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;

public class TaskDetailsController {
    private MainController mainController;
    private SceneController sceneController;

    private TaskDetailsModel task;
    @FXML
    public Label taskTitleLabel;
    @FXML
    public Label createdOnLabel;
    @FXML
    public Label assignedUserLabel;
    @FXML
    public Label statusLabel;
    @FXML
    public Label descriptionLabel;
    @FXML
    public ListView<String> commentsListView;

    public TaskDetailsController() {
        this.mainController = ApplicationSetup.setup();
        this.sceneController = new SceneController();
    }

    public void loadTask() {
        if (task != null) {
            taskTitleLabel.setText(task.getTaskTitle());
            createdOnLabel.setText(task.getTimeCreated());
            assignedUserLabel.setText(task.getAssignedUser());
            statusLabel.setText(task.getStatus().name());
            descriptionLabel.setText(task.getDescription());
            List<String> comments = new LinkedList<>();
            for(Comment c:task.getComments()){
                comments.add(" ("+ c.getTimeCreated() + ") " + c.getAuthor() + ": " + c.getContent());
            }
            comments.add("eeeeeeeeeeeeeeeeeeeeeee");
            comments.add("eeeeeeeeeeeeeeeeeeeeeee");
            commentsListView.setItems(FXCollections.observableList(comments));
            commentsListView.refresh();
        }
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setTask(Task task) {
        this.task = mainController.getTaskDetailsModel(task);
        loadTask();
    }

    public void goBack(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            SceneController.switchToBoardScene(stage);
        } catch (Exception e) {
            System.out.println("Error when going back to board.");
        }
    }
}
