package com.pwr.dpp.backlog.dpp.controllers;

import com.pwr.dpp.backlog.dpp.SceneController;
import com.pwr.dpp.backlog.dpp.business.ApplicationSetup;
import com.pwr.dpp.backlog.dpp.business.MainController;
import com.pwr.dpp.backlog.dpp.business.orm.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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
    }
}
