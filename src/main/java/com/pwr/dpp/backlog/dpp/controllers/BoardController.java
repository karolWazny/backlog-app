package com.pwr.dpp.backlog.dpp.controllers;

import com.pwr.dpp.backlog.dpp.SceneController;
import com.pwr.dpp.backlog.dpp.business.ApplicationSetup;
import com.pwr.dpp.backlog.dpp.business.MainController;
import com.pwr.dpp.backlog.dpp.business.models.BoardModel;
import com.pwr.dpp.backlog.dpp.business.orm.Category;
import com.pwr.dpp.backlog.dpp.business.orm.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Date;
import java.util.List;

public class BoardController {
    private MainController mainController;
    private BoardModel model;

    private ObservableList<Task> openTasks;
    private ObservableList<Task> toDoTasks;
    private ObservableList<Task> inProgressTasks;
    private ObservableList<Task> closedTasks;

    private ListView<Task> previousDragAndDropList = null;
    private Task draggedTask = null;

    @FXML
    private ListView<Task> openTasksList;
    @FXML
    private ListView<Task> toDoTasksList;
    @FXML
    private ListView<Task> inProgressTasksList;
    @FXML
    private ListView<Task> closedTasksList;

    public BoardController(){
        if(SceneController.getMainController()!=null) {
            this.mainController = SceneController.getMainController();
        }else{
            this.mainController = ApplicationSetup.setup();
            SceneController.setMainController(this.mainController);
        }
        this.model = mainController.getBoardModel();
    }

    @FXML
    public void initialize() {
        initializeListViews();
        initializeDragAndDrop();
    }

    private void initializeListViews(){
        assertFieldsNotNull();

        createObservableArrays();
        setItemsInListViews();
        setCellFactoryInListViews();
    }

    private void initializeDragAndDrop() {
        initializeDragAndDropForList(openTasksList, Category.OPEN);
        initializeDragAndDropForList(toDoTasksList, Category.TODO);
        initializeDragAndDropForList(inProgressTasksList, Category.DOING);
        initializeDragAndDropForList(closedTasksList, Category.CLOSED);
    }

    private void initializeDragAndDropForList(ListView<Task> listView, Category category) {
        listView.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent mouseEvent) {
                Dragboard dragboard = listView.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                Task selectedTask = listView.getSelectionModel().getSelectedItem();
                content.putString(selectedTask.getName());
                dragboard.setContent(content);

                previousDragAndDropList = listView;
                draggedTask = selectedTask;
                System.out.println(draggedTask.getName());
            }
        });
        listView.setOnDragDone(new EventHandler<DragEvent>() {
            @Override
            public void handle (DragEvent dragEvent) {
                // Nothing to see here
            }
        });
        listView.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle (DragEvent dragEvent) {
                listView.setBlendMode(BlendMode.DIFFERENCE);
            }
        });
        listView.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle (DragEvent dragEvent) {
                listView.setBlendMode(null);
            }
        });
        listView.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle (DragEvent dragEvent) {
                dragEvent.acceptTransferModes(TransferMode.MOVE);
            }
        });
        listView.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle (DragEvent dragEvent) {

                if (previousDragAndDropList != listView) {
                    draggedTask.setCategory(category);
                    mainController.getDatabaseHandler().saveTask(draggedTask);
                    listView.getItems().addAll(draggedTask);
                    previousDragAndDropList.getItems().remove(draggedTask);
                }
                dragEvent.setDropCompleted(true);
                draggedTask = null;
                previousDragAndDropList = null;
            }
        });
    }


    private void setItemsInListViews(){
        this.openTasksList.setItems(this.openTasks);
        this.toDoTasksList.setItems(this.toDoTasks);
        this.inProgressTasksList.setItems(this.inProgressTasks);
        this.closedTasksList.setItems(this.closedTasks);
    }

    private void createObservableArrays(){
        this.openTasks = FXCollections.observableArrayList(model.getOpen());
        this.toDoTasks = FXCollections.observableArrayList(model.getToDo());
        this.inProgressTasks = FXCollections.observableArrayList(model.getDoing());
        this.closedTasks = FXCollections.observableArrayList(model.getClosed());
    }

    private void assertFieldsNotNull(){
        assert openTasksList != null;
        assert toDoTasksList != null;
        assert inProgressTasksList != null;
        assert closedTasksList != null;
    }

    private void setCellFactoryInListViews(){
        TaskCellFactory cellFactory = new TaskCellFactory();

        this.openTasksList.setCellFactory(cellFactory);
        this.toDoTasksList.setCellFactory(cellFactory);
        this.inProgressTasksList.setCellFactory(cellFactory);
        this.closedTasksList.setCellFactory(cellFactory);
    }

    @FXML
    public void handleMouseClick(MouseEvent arg0) {
        try {
            Stage stage = (Stage) ((Node) arg0.getSource()).getScene().getWindow();
            Task selectedTask = getSelectedItem();
            System.out.println(selectedTask);
            if (selectedTask != null) {
                SceneController.switchToTaskDetailsScene(stage, selectedTask);
            }
        } catch (Exception e) {
            System.out.println("1Error when selecting task");
            System.out.println(e);
        }
    }

    public void goToNewTaskView(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            SceneController.switchToNewTaskScene(stage);
        } catch (Exception e) {
            System.out.println("An error occurred when trying to go back to the board.");
        }
    }

    private Task getSelectedItem() {
        Task openTask = openTasksList.getSelectionModel().getSelectedItem();
        if (openTask != null) {
            return openTask;
        }
        Task toDoTask = toDoTasksList.getSelectionModel().getSelectedItem();
        if (toDoTask != null) {
            return toDoTask;
        }
        Task inProgressTask = inProgressTasksList.getSelectionModel().getSelectedItem();
        if (inProgressTask != null) {
            return inProgressTask;
        }
        Task closedTask = closedTasksList.getSelectionModel().getSelectedItem();
        if (closedTask != null) {
            return closedTask;
        }
        return null;
    }
}
