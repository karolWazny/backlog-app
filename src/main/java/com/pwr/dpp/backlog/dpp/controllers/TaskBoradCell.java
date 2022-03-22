package com.pwr.dpp.backlog.dpp.controllers;

import com.pwr.dpp.backlog.dpp.business.orm.Task;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;


public class TaskBoradCell extends ListCell<Task> {

    private Label taskNameLabel = new Label();
    private Label taskAssignedUser = new Label();
    private VBox taskBox = new VBox();

    public TaskBoradCell() {

        taskBox.setMinWidth(204.0);
        taskBox.setPrefWidth(204.0);
        taskBox.setMaxWidth(204.0);

        taskBox.setMinHeight(52.0);
        taskBox.setPrefHeight(52.0);

        taskBox.setStyle("-fx-background-color: #5E6366");
        taskBox.setStyle("-fx-background-radius: 10");

        taskNameLabel.setText("[TASK_NAME]");
        taskAssignedUser.setText("[USER]");

        taskBox.getChildren().addAll(taskNameLabel, taskAssignedUser);
    }

    @Override
    protected void updateItem(Task task, boolean empty) {
        super.updateItem(task, empty);
        setText(null);
        if (empty) {
            setGraphic(null);
            setStyle("-fx-background-color: transparent");
            setStyle("-fx-background: transparent");
        } else {
            setGraphic(taskBox);
        }
    }
}
