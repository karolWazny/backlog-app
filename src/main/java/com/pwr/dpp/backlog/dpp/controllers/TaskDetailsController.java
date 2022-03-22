package com.pwr.dpp.backlog.dpp.controllers;

import com.pwr.dpp.backlog.dpp.SceneController;
import com.pwr.dpp.backlog.dpp.business.ApplicationSetup;
import com.pwr.dpp.backlog.dpp.business.MainController;
import com.pwr.dpp.backlog.dpp.business.models.TaskDetailsModel;
import com.pwr.dpp.backlog.dpp.business.orm.Category;
import com.pwr.dpp.backlog.dpp.business.orm.Comment;
import com.pwr.dpp.backlog.dpp.business.orm.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Controller for task details scene.
 */
public class TaskDetailsController {
    private MainController mainController;
    private TaskDetailsModel task;

    @FXML
    public AnchorPane titlePane;
    @FXML
    public Label taskTitleLabel;
    @FXML
    public Label createdOnLabel;
    @FXML
    public AnchorPane assigneePane;
    @FXML
    public Label assignedUserLabel;
    @FXML
    public ImageView assigneeAvatar;
    @FXML
    public AnchorPane statusPane;
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
    @FXML
    public TextArea titleTextArea;
    @FXML
    public ComboBox<String> assigneeComboBox;
    @FXML
    public ComboBox<String> statusComboBox;

    public TaskDetailsController() {
        if(SceneController.getMainController()!=null) {
            this.mainController = SceneController.getMainController();
        }else{
            this.mainController = ApplicationSetup.setup();
            SceneController.setMainController(this.mainController);
        }
    }
    @FXML
    public void initialize(){
        taskDescriptionTextArea.setManaged(false);
        titleTextArea.setManaged(false);
        assigneeComboBox.setManaged(false);
        statusComboBox.setManaged(false);
        Tooltip editTooltip = new Tooltip("Double click to edit.");
        Tooltip submitTextFieldTooltip = new Tooltip("Click anywhere or click ENTER to save.\nESC to discard changes.");
        Tooltip chooseUserTooltip = new Tooltip("Click on username to assign task.\nClick anywhere to close without changes.");
        Tooltip setStatusTooltip = new Tooltip("Click on status to assign.\nClick anywhere to close without changes.");
        Arrays.asList(editTooltip, submitTextFieldTooltip, chooseUserTooltip, setStatusTooltip).forEach(tooltip -> tooltip.setFont(Font.font("Segoe UI", 12)));
        taskTitleLabel.setTooltip(editTooltip);
        titleTextArea.setTooltip(submitTextFieldTooltip);

        assignedUserLabel.setTooltip(editTooltip);

        assigneeComboBox.setTooltip(chooseUserTooltip);

        statusLabel.setTooltip(editTooltip);

        statusComboBox.setTooltip(setStatusTooltip);

        descriptionLabel.setTooltip(editTooltip);
        taskDescriptionTextArea.setTooltip(submitTextFieldTooltip);
    }

    @FXML
    private void onTaskTitleEditKeyPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            String userInput = titleTextArea.getText();
            if(!event.isShiftDown()){
                taskTitleLabel.setText(userInput);
                task.getTask().setName(userInput);
                saveTask();
                swapVisibility(taskTitleLabel, titleTextArea);
            } else {
                taskDescriptionTextArea.setText(userInput + "\n");
                taskDescriptionTextArea.end();
            }
        } else if(event.getCode() == KeyCode.ESCAPE) {
            taskTitleLabel.setText(task.getTaskTitle());
            swapVisibility(taskTitleLabel, titleTextArea);
        }

    }

    @FXML
    private void onTaskDescriptionEditKeyPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            String newContent = taskDescriptionTextArea.getText();
            if(!event.isShiftDown()){
                task.setDescription(newContent);
                taskDescriptionTextArea.managedProperty().set(false);
                taskDescriptionTextArea.visibleProperty().set(false);
                descriptionLabel.setText(task.getDescription());
                descriptionLabel.managedProperty().set(true);
                descriptionLabel.visibleProperty().set(true);
            } else {
                taskDescriptionTextArea.setText(newContent + "\n");
                taskDescriptionTextArea.end();
            }
        } else if(event.getCode() == KeyCode.ESCAPE) {
            taskDescriptionTextArea.managedProperty().set(false);
            taskDescriptionTextArea.visibleProperty().set(false);
            descriptionLabel.setText(task.getDescription());
            descriptionLabel.managedProperty().set(true);
            descriptionLabel.visibleProperty().set(true);
        }
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
            if (mouseEvent.getClickCount() == 1) {
                if (taskDescriptionTextArea.isManaged() || taskDescriptionTextArea.isVisible()) {
                    swapVisibility(descriptionLabel, taskDescriptionTextArea);
                    descriptionLabel.setText(taskDescriptionTextArea.getText());
                    task.setDescription(taskDescriptionTextArea.getText());
                   // saveTask();
                }
                if (titleTextArea.isManaged() || titleTextArea.isVisible()){
                    String userInput = titleTextArea.getText();
                    taskTitleLabel.setText(userInput);
                    task.getTask().setName(userInput);
                    saveTask();
                    swapVisibility(taskTitleLabel, titleTextArea);
                }
                if (assigneeComboBox.isManaged() || assigneeComboBox.isVisible()){
                    assigneeAvatar.setVisible(true);
                    swapVisibility(assignedUserLabel, assigneeComboBox);
                }
                if (statusComboBox.isManaged() || statusComboBox.isVisible()){
                    swapVisibility(statusLabel, statusComboBox);
                }
            }
        }
    }

    @FXML
    public void onAssigneeChange(){
        if(assigneeComboBox.isVisible()) {
            String selectedUser = assigneeComboBox.getSelectionModel().getSelectedItem();
            assignedUserLabel.setText(selectedUser);
            task.getTask().setUser(selectedUser);
            saveTask();
            assigneeAvatar.setVisible(true);
            swapVisibility(assignedUserLabel, assigneeComboBox);
        }
    }

    @FXML
    public void onStatusChange(){
        if(statusComboBox.isVisible()) {
            String selectedStatus = statusComboBox.getSelectionModel().getSelectedItem();
            statusLabel.setText(selectedStatus);
            Category category;
            switch(selectedStatus){
                case "To Do":
                    category = Category.TODO;
                    break;
                case "Doing":
                    category = Category.DOING;
                    break;
                case "Closed":
                    category = Category.CLOSED;
                    break;
                default:
                    category = Category.OPEN;
            }
            task.getTask().setCategory(category);
            saveTask();
            swapVisibility(statusLabel, statusComboBox);
        }
    }

    private void saveTask() {
        mainController.getDatabaseHandler().saveTask(task.getTask());
    }

    @FXML
    public void onTitlePaneClick(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if (mouseEvent.getClickCount() == 2) {
                titleTextArea.setText(taskTitleLabel.getText());
                swapVisibility(taskTitleLabel, titleTextArea);
            }
        }
    }

    @FXML
    public void onAssigneePaneClick(MouseEvent mouseEvent) {

        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if (mouseEvent.getClickCount() == 2) {
                assigneeAvatar.setVisible(false);
                assigneeAvatar.setManaged(false);
                assigneeComboBox.setValue(assignedUserLabel.getText());
                assigneeComboBox.setItems(FXCollections.observableList(mainController.getDatabaseHandler().getUsers()));
                swapVisibility(assignedUserLabel, assigneeComboBox);
            }
        }
    }

    @FXML
    public void onStatusPaneClick(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if (mouseEvent.getClickCount() == 2) {
                statusComboBox.setValue(statusLabel.getText());
                statusComboBox.setItems(FXCollections.observableList(Arrays.asList("Open", "To Do", "Doing", "Closed")));
                swapVisibility(statusLabel, statusComboBox);
            }
        }
    }

    @FXML
    public void onDescriptionClick(MouseEvent mouseEvent) {
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
            if(mouseEvent.getClickCount() == 2){
                taskDescriptionTextArea.setText(descriptionLabel.getText());
                swapVisibility(descriptionLabel, taskDescriptionTextArea);
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

    private <A extends Node, B extends Node> void swapVisibility(A node1, B node2){
        if(node1 !=null && node2!=null) {
            if (node1.isVisible()) {
                node1.setVisible(false);
                node1.setManaged(false);
                node2.setVisible(true);
                node2.setManaged(true);
            } else {
                node2.setVisible(false);
                node2.setManaged(false);
                node1.setVisible(true);
                node1.setManaged(true);
            }
        }
    }
}
