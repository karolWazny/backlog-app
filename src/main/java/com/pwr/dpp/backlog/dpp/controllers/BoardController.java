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
    private ObservableList<Task> previousTaskList = null;
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

    /**
     * Initializes the Board view with data.
     */
    @FXML
    public void initialize() {
        initializeListViews();
        initializeDragAndDrop();
    }

    /**
     * Initializes ListView objects for all categories.
     * Creates observable arrays and assigns them to corresponding list views.
     * Sets cell factory to generate custom ListView cells.
     */
    private void initializeListViews(){
        assertFieldsNotNull();

        createObservableArrays();
        setItemsInListViews();
        setCellFactoryInListViews();
    }

    /**
     * Initializes drag and drop functionality for every ListView by adding event listeners.
     */
    private void initializeDragAndDrop() {
        initializeDragAndDropForList(openTasksList, openTasks, Category.OPEN);
        initializeDragAndDropForList(toDoTasksList, toDoTasks, Category.TODO);
        initializeDragAndDropForList(inProgressTasksList, inProgressTasks, Category.DOING);
        initializeDragAndDropForList(closedTasksList, closedTasks, Category.CLOSED);
    }

    /**
     * Initializes drag&drop listeners for a given ListView object:
     * - setOnDragDetected is fired when the user starts dragging the list item
     * - setOnDragDone is fired when the dragging process has ended
     * - setOnDragEntered is fired when user hovers over the list while dragging an item
     * - setOnDragExited is fired when user stops hovering over the list while dragging
     * - setOnDragOver is fired when
     * - setOnDragDropped is fired when user lets go of an item after dragging
     *
     * @param listView - ListView object that will have the event listeners added
     * @param taskList - ObservableList that contains a list of Tasks displayed within the listView
     * @param category - Category object to indicate which category is being handled (tasks will be moved to this category when user stops dragging over the given listView).
     */
    private void initializeDragAndDropForList(ListView<Task> listView, ObservableList<Task> taskList, Category category) {
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
                previousTaskList = taskList;
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
                listView.setStyle("-fx-background-color: #2D3E41");
            }
        });
        listView.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle (DragEvent dragEvent) {
                listView.setStyle("-fx-background-color: #2A2E2F");
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
                    taskList.add(draggedTask);
                    previousTaskList.remove(draggedTask);
                }
                dragEvent.setDropCompleted(true);
                draggedTask = null;
                previousDragAndDropList = null;
                previousTaskList = null;
            }
        });
    }

    /**
     * Initializes ListView objects with ObservableLists of Tasks.
     */
    private void setItemsInListViews(){
        this.openTasksList.setItems(this.openTasks);
        this.toDoTasksList.setItems(this.toDoTasks);
        this.inProgressTasksList.setItems(this.inProgressTasks);
        this.closedTasksList.setItems(this.closedTasks);
    }

    /**
     * Initializes ObservableLists with Tasks from every category.
     * Data is fetched from the database.
     */
    private void createObservableArrays(){
        this.openTasks = FXCollections.observableArrayList(model.getOpen());
        this.toDoTasks = FXCollections.observableArrayList(model.getToDo());
        this.inProgressTasks = FXCollections.observableArrayList(model.getDoing());
        this.closedTasks = FXCollections.observableArrayList(model.getClosed());
    }

    /**
     * Checks if ObservableLists of tasks for every category are not null.
     */
    private void assertFieldsNotNull(){
        assert openTasksList != null;
        assert toDoTasksList != null;
        assert inProgressTasksList != null;
        assert closedTasksList != null;
    }

    /**
     * Sets a custom cell factory for every task list.
     */
    private void setCellFactoryInListViews(){
        TaskCellFactory cellFactory = new TaskCellFactory();

        this.openTasksList.setCellFactory(cellFactory);
        this.toDoTasksList.setCellFactory(cellFactory);
        this.inProgressTasksList.setCellFactory(cellFactory);
        this.closedTasksList.setCellFactory(cellFactory);
    }

    /**
     * Method is fired when a Task is clicked on the board.
     * After mouse click, the user will be directed to the task details view of a selected task.
     * @param arg0 - mouse event that is fired when clicking on a Task cell
     */
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

    /**
     * Method responsible for switching view to New task form after clicking 'New task' button.
     * @param event - event fired when clicking on the button
     */
    public void goToNewTaskView(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            SceneController.switchToNewTaskScene(stage);
        } catch (Exception e) {
            System.out.println("An error occurred when trying to go back to the board.");
        }
    }

    /**
     * Method that returns a Task object that is currently selected in any of the lists.
     * Returns null if nothing is selected.
     * @return currently selected Task in any of the categories
     */
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
