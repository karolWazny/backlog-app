package com.pwr.dpp.backlog.dpp.controllers;

import com.pwr.dpp.backlog.dpp.SceneController;
import com.pwr.dpp.backlog.dpp.business.ApplicationSetup;
import com.pwr.dpp.backlog.dpp.business.MainController;
import com.pwr.dpp.backlog.dpp.business.orm.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class BoardController {
    private MainController mainController;
    private SceneController sceneController;

    private ObservableList<Task> openTasks;
    private ObservableList<Task> toDoTasks;
    private ObservableList<Task> inProgressTasks;
    private ObservableList<Task> closedTasks;

    @FXML
    private ListView<Task> openTasksList;
    @FXML
    private ListView<Task> toDoTasksList;
    @FXML
    private ListView<Task> inProgressTasksList;
    @FXML
    private ListView<Task> closedTasksList;

    @FXML
    public void initialize() {
        this.mainController = ApplicationSetup.setup();
        this.sceneController = new SceneController();

        assert openTasksList != null;
        assert toDoTasksList != null;
        assert inProgressTasksList != null;
        assert closedTasksList != null;

        this.openTasks = FXCollections.observableArrayList(mainController.getBoardModel().getOpen());
        this.toDoTasks = FXCollections.observableArrayList(mainController.getBoardModel().getToDo());
        this.inProgressTasks = FXCollections.observableArrayList(mainController.getBoardModel().getDoing());
        this.closedTasks = FXCollections.observableArrayList(mainController.getBoardModel().getClosed());

        this.openTasksList.setItems(this.openTasks);
        this.toDoTasksList.setItems(this.toDoTasks);
        this.inProgressTasksList.setItems(this.inProgressTasks);
        this.closedTasksList.setItems(this.closedTasks);

        TaskCellFactory cellFactory = new TaskCellFactory();

        this.openTasksList.setCellFactory(cellFactory);
        this.toDoTasksList.setCellFactory(cellFactory);
        this.inProgressTasksList.setCellFactory(cellFactory);
        this.closedTasksList.setCellFactory(cellFactory);

        this.openTasksList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                    Task selectedTask = openTasksList.getSelectionModel().getSelectedItem();
                    System.out.println(selectedTask);
                    if (selectedTask != null) {
                        SceneController.switchToTaskDetailsScene(stage, selectedTask);
                    }
                } catch (Exception e) {
                    System.out.println("2Error when selecting task");
                    System.out.println(e);
                }
            }
        });
    }

    @FXML
    public void handleMouseClick(MouseEvent arg0) {
        try {
            Stage stage = (Stage) ((Node) arg0.getSource()).getScene().getWindow();
            Task selectedTask = openTasksList.getSelectionModel().getSelectedItem();
            System.out.println(selectedTask);
            if (selectedTask != null) {
                SceneController.switchToTaskDetailsScene(stage, selectedTask);
            }
        } catch (Exception e) {
            System.out.println("1Error when selecting task");
            System.out.println(e);
        }
    }
}
