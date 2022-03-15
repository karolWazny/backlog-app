package com.pwr.dpp.backlog.dpp.controllers;

import com.pwr.dpp.backlog.dpp.business.orm.Task;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.util.Callback;

public class TaskCellFactory implements Callback<ListView<Task>, ListCell<Task>> {
    @Override
    public ListCell<Task> call(ListView<Task> param) {
        return new ListCell<>(){
            @Override
            public void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                if (empty || task == null) {
                    setText(null);
                    setGraphic(null);
                    setStyle("-fx-background-color: transparent");
                    setStyle("-fx-background: transparent");
                } else {
                    // Background color change not working
                    setStyle("-fx-control-inner-background: #5E6366");
                    setStyle("-fx-background-radius: 10");
                    setGraphic(getTaskCell(task));
                    setText(null);
                }
            }
        };
    }
    VBox getTaskCell(Task task) {
        VBox taskPane = new VBox();
        // Background color change not working
        taskPane.setStyle("-fx-background-color: #5E6366");
        taskPane.setStyle("-fx-background-radius: 10");

        Label taskName = new Label();
        Label taskAssignedUser = new Label();

        taskName.setText("Task: " + task.getName());
        taskAssignedUser.setText("User: " + task.getUser());

        taskPane.getChildren().add(taskName);
        taskPane.getChildren().add(taskAssignedUser);

        return taskPane;
    }
}
