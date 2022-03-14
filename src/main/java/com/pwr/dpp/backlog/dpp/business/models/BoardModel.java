package com.pwr.dpp.backlog.dpp.business.models;

import com.pwr.dpp.backlog.dpp.business.DatabaseHandler;
import com.pwr.dpp.backlog.dpp.business.LoggedUserRepository;
import com.pwr.dpp.backlog.dpp.business.orm.Category;
import com.pwr.dpp.backlog.dpp.business.orm.Comment;
import com.pwr.dpp.backlog.dpp.business.orm.Task;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BoardModel implements Observable {
    private final List<Task> tasks;
    private final List<Comment> comments;
    private final DatabaseHandler databaseHandler;
    private final List<InvalidationListener> listeners = new LinkedList<>();
    private LoggedUserRepository loggedUserRepository;

    public BoardModel(DatabaseHandler handler){
        this.databaseHandler = handler;
        tasks = databaseHandler.getTasks();
        comments = databaseHandler.getComments();
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

    private List<Comment> listCommentsUnderTask(Integer taskId) {
        return commentUnderTask(taskId).collect(Collectors.toList());
    }

    private Stream<Comment> commentUnderTask (Integer taskId) {
        return comments.stream().filter(comment -> comment.getId().equals(taskId));
    }

    private Integer countCommentsUnderTask (Integer taskId) {
        return listCommentsUnderTask(taskId).size();
    }

    private List<Comment> listCommentsUnderUser(String user) {
        return commentUnderUser(user).collect(Collectors.toList());
    }

    private Stream<Comment> commentUnderUser (String user) {
        return comments.stream().filter(comment -> comment.getAuthor().equals(user));
    }
    
    private List<Comment> listCommentsUnderCategory (Category category) {
        List<Task> tempList = listTasksWithCategory(category);
        List<Comment> result = new ArrayList<>();
        for (Task task: tempList) {
            result.addAll(listCommentsUnderTask(task.getId()));
        }
        return result;
    }

    @Override
    public void addListener(InvalidationListener invalidationListener) {
        listeners.add(invalidationListener);
    }

    @Override
    public void removeListener(InvalidationListener invalidationListener) {
        listeners.remove(invalidationListener);
    }

    public void invalidate(){
        for (InvalidationListener listener:
             listeners) {
            listener.invalidated(this);
        }
    }

    public void setLoggedUserRepository(LoggedUserRepository loggedUserRepository) {
        this.loggedUserRepository = loggedUserRepository;
    }
}
