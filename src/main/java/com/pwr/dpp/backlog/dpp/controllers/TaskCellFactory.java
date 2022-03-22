package com.pwr.dpp.backlog.dpp.controllers;

import com.pwr.dpp.backlog.dpp.business.orm.Task;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.net.URL;

/**
 *  Used to create {@link Task} graphic representation.
 */
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
                    setGraphic(getTaskCell(task));
                    setText(null);
                }
            }
        };
    }
    VBox getTaskCell(Task task) {
        VBox taskPane = new VBox();
        taskPane.setId("taskBox");

        AnchorPane secondRow = new AnchorPane();
        secondRow.setId("secondRow");

        Label taskName = new Label();
        ImageView userPictureImageView = new ImageView();
        Label taskAssignedUser = new Label();
        ImageView commentsIconImageView = new ImageView();
        Label commentsLabel = new Label();

        taskName.setText(task.getName());
        taskName.setId("taskName");

        URL url = this.getClass().getResource("/com/pwr/dpp/backlog/dpp/icons/icon-user.png");
        Image image = new Image(url.toString());
        userPictureImageView.setImage(image);
        userPictureImageView.setId("assignedaUserImage");
        userPictureImageView.setFitWidth(12);
        userPictureImageView.setFitHeight(12);

        taskAssignedUser.setText(task.getUser());
        taskAssignedUser.setId("taskAssignedUser");

        url = this.getClass().getResource("/com/pwr/dpp/backlog/dpp/icons/icon-comment.png");
        Image commentIcon = new Image(url.toString());
        commentsIconImageView.setImage(commentIcon);
        commentsIconImageView.setId("commentIcon");
        commentsIconImageView.setFitWidth(10);
        commentsIconImageView.setFitHeight(10);

        commentsLabel.setText("0");
        commentsLabel.setId("commentNumber");

        userPictureImageView.setX(0);
        userPictureImageView.setY(6);
        taskAssignedUser.setLayoutX(20);
        taskAssignedUser.setLayoutY(5);
        commentsIconImageView.setX(170);
        commentsIconImageView.setY(7);
        commentsLabel.setLayoutX(185);
        commentsLabel.setLayoutY(5);

        secondRow.getChildren().add(userPictureImageView);
        secondRow.getChildren().add(taskAssignedUser);
        secondRow.getChildren().add(commentsIconImageView);
        secondRow.getChildren().add(commentsLabel);

        taskPane.getChildren().add(taskName);
        taskPane.getChildren().add(secondRow);

        return taskPane;
    }
}
