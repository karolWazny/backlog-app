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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class TaskDetailsController {
    private MainController mainController;
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
    @FXML
    public TextArea commentTextArea;
    @FXML
    public Button addCommentButton;
    @FXML
    public Button goToBoardButton;
    @FXML
    public TextArea taskDescriptionTextArea;


    public TaskDetailsController() {
        if (SceneController.getMainController() == null) {
            SceneController.setMainController(ApplicationSetup.setup());
        }
        this.mainController = SceneController.getMainController();
    }
    @FXML
    public void initialize(){
        taskDescriptionTextArea.managedProperty().set(false);
        taskDescriptionTextArea.visibleProperty().set(false);
    }

    public void loadTask() {
        if (task != null) {
            taskTitleLabel.setText(task.getTaskTitle());
            createdOnLabel.setText(task.getTimeCreated());
            assignedUserLabel.setText(task.getAssignedUser());
            statusLabel.setText(task.getStatus().name());
            descriptionLabel.setText(task.getDescription());
            List<String> comments = new LinkedList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            for(Comment c:task.getComments()){
                comments.add(" ("+ sdf.format(c.getTimeCreated()) + ") " + c.getAuthor() + ": " + c.getContent());
            }
            commentsListView.setItems(FXCollections.observableList(comments));
            commentsListView.refresh();
        }
    }

    @FXML
    public void onAnywhereClick(MouseEvent mouseEvent){
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if (mouseEvent.getClickCount() == 2) {
                System.out.println("clicked");
                if (taskDescriptionTextArea.managedProperty().get() || taskDescriptionTextArea.visibleProperty().get()) {
                    descriptionLabel.visibleProperty().set(true);
                    descriptionLabel.managedProperty().set(true);
                    taskDescriptionTextArea.managedProperty().set(false);
                    taskDescriptionTextArea.visibleProperty().set(false);
                    descriptionLabel.setText(taskDescriptionTextArea.getText());
                    task.getTask().setDescription(taskDescriptionTextArea.getText());
                    mainController.getDatabaseHandler().saveTask(task.getTask());
                }
            }
        }
    }

    @FXML
    public void onDescriptionClick(MouseEvent mouseEvent) {
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
            if(mouseEvent.getClickCount() == 1){
                descriptionLabel.visibleProperty().set(false);
                descriptionLabel.managedProperty().set(false);
                taskDescriptionTextArea.managedProperty().set(true);
                taskDescriptionTextArea.visibleProperty().set(true);
                taskDescriptionTextArea.setText(descriptionLabel.getText());
            }
        }
    }

    @FXML
    public void onKeyTyped(){
        if(commentTextArea.getText().strip().length() !=0){
            addCommentButton.disableProperty().set(false);
        }else{
            addCommentButton.disableProperty().set(true);
        }
    }

    @FXML
    public void onAddCommentButtonClicked(ActionEvent event){
        String commentInput = commentTextArea.getText().strip();
        if(commentInput.length() !=0){
            task.addComment(commentInput);
            commentTextArea.setText("");
            loadTask();
        }
    }

    @FXML
    public void onGoToBoardButtonClicked(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            System.out.println("switched");
            SceneController.switchToBoardScene(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setTask(Task task) {
        this.task = mainController.getTaskDetailsModel(task);
        loadTask();
    }

}
