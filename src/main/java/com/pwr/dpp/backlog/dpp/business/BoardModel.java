package com.pwr.dpp.backlog.dpp.business;

import com.pwr.dpp.backlog.dpp.business.orm.Category;
import com.pwr.dpp.backlog.dpp.business.orm.Task;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BoardModel implements Observable {
    private final List<Task> tasks;
    private final DatabaseHandler databaseHandler;
    private final List<InvalidationListener> listeners = new LinkedList<>();

    public BoardModel(DatabaseHandler handler){
        this.databaseHandler = handler;
        tasks = databaseHandler.getAllTasks();
    }

    public List<Task> getToDo(){
        return listTasksWithCategory(Category.TODO);
    }

    public List<Task> getToDo(String user){
        return listTasksWithCategoryAndUser(Category.TODO, user);
    }

    public List<Task> getOpen(){
        return listTasksWithCategory(Category.OPEN);
    }

    public List<Task> getOpen(String user){
        return listTasksWithCategoryAndUser(Category.OPEN, user);
    }

    public List<Task> getDoing(){
        return listTasksWithCategory(Category.DOING);
    }

    public List<Task> getDoing(String user){
        return listTasksWithCategoryAndUser(Category.DOING, user);
    }

    public List<Task> getClosed(){
        return listTasksWithCategory(Category.CLOSED);
    }

    public List<Task> getClosed(String user){
        return listTasksWithCategoryAndUser(Category.CLOSED, user);
    }

    public void moveOnTodo(Task task){
        moveToCategory(task, Category.TODO);
    }

    public void moveOnDoing(Task task){
        moveToCategory(task, Category.DOING);
    }

    public void moveOnClosed(Task task){
        moveToCategory(task, Category.CLOSED);
    }

    public void moveOnOpen(Task task){
        moveToCategory(task, Category.OPEN);
    }

    public void moveToCategory(Task task, Category category){
        task.setCategory(category);
        databaseHandler.saveTask(task);
        invalidate();
    }

    private List<Task> listTasksWithCategory(Category category){
        return tasksWithCategory(category)
                .collect(Collectors.toList());
    }

    private List<Task> listTasksWithCategoryAndUser(Category category, String user){
        return tasksWithCategory(category)
                .filter(task -> task.getUser().equals(user))
                .collect(Collectors.toList());
    }

    private Stream<Task> tasksWithCategory(Category category){
        return tasks.stream()
                .filter(task -> task.getCategory().equals(category));
    }

    @Override
    public void addListener(InvalidationListener invalidationListener) {
        listeners.add(invalidationListener);
    }

    @Override
    public void removeListener(InvalidationListener invalidationListener) {
        listeners.remove(invalidationListener);
    }

    private void invalidate(){
        for (InvalidationListener listener:
             listeners) {
            listener.invalidated(this);
        }
    }
}
