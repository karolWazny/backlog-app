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

/**
 * Provides methods which are used to access the data in board scene.
 */
public class BoardModel implements Observable {
    private final List<Task> tasks;
    private final List<Comment> comments;
    private final DatabaseHandler databaseHandler;
    private final List<InvalidationListener> listeners = new LinkedList<>();

    /**
     * Creates {@link BoardModel}
     * @param handler database handler which implements {@link DatabaseHandler}
     */
    public BoardModel(DatabaseHandler handler){
        this.databaseHandler = handler;
        tasks = databaseHandler.getTasks();
        comments = databaseHandler.getComments();
    }

    /**
     * Returns {@link List} of {@link Task} which contains tasks with {@link Category} "To Do".
     * @return {@link List} of {@link Task} which contains tasks with {@link Category} "To Do".
     */
    public List<Task> getToDo(){
        return listTasksWithCategory(Category.TODO);
    }
    /**
     * Returns {@link List} of {@link Task} which contains tasks with {@link Category} "To Do" which are assigned to given user.
     * @param user username of the user whose tasks we want to get.
     * @return {@link List} of {@link Task} which contains tasks with {@link Category} "To Do" which are assigned to given user.
     */
    public List<Task> getToDo(String user){
        return listTasksWithCategoryAndUser(Category.TODO, user);
    }
    /**
     * Returns {@link List} of {@link Task} which contains tasks with {@link Category} "Open".
     * @return {@link List} of {@link Task} which contains tasks with {@link Category} "Open".
     */
    public List<Task> getOpen(){
        return listTasksWithCategory(Category.OPEN);
    }
    /**
     * Returns {@link List} of {@link Task} which contains tasks with {@link Category} "Open" which are assigned to given user.
     * @param user username of the user whose tasks we want to get.
     * @return {@link List} of {@link Task} which contains tasks with {@link Category} "Open" which are assigned to given user.
     */
    public List<Task> getOpen(String user){
        return listTasksWithCategoryAndUser(Category.OPEN, user);
    }
    /**
     * Returns {@link List} of {@link Task} which contains tasks with {@link Category} "Doing".
     * @return {@link List} of {@link Task} which contains tasks with {@link Category} "Doing".
     */
    public List<Task> getDoing(){
        return listTasksWithCategory(Category.DOING);
    }
    /**
     * Returns {@link List} of {@link Task} which contains tasks with {@link Category} "Doing" which are assigned to given user.
     * @param user username of the user whose tasks we want to get.
     * @return {@link List} of {@link Task} which contains tasks with {@link Category} "Doing" which are assigned to given user.
     */
    public List<Task> getDoing(String user){
        return listTasksWithCategoryAndUser(Category.DOING, user);
    }
    /**
     * Returns {@link List} of {@link Task} which contains tasks with {@link Category} "Closed".
     * @return {@link List} of {@link Task} which contains tasks with {@link Category} "Closed".
     */
    public List<Task> getClosed(){
        return listTasksWithCategory(Category.CLOSED);
    }
    /**
     * Returns {@link List} of {@link Task} which contains tasks with {@link Category} "Closed" which are assigned to given user.
     * @param user username of the user whose tasks we want to get.
     * @return {@link List} of {@link Task} which contains tasks with {@link Category} "Closed" which are assigned to given user.
     */
    public List<Task> getClosed(String user){
        return listTasksWithCategoryAndUser(Category.CLOSED, user);
    }

    /**
     * Returns {@link List} of all {@link Comment} assigned to given task.
     * @param taskId id of the task for which comments we want to get.
     * @return {@link List} of all {@link Comment} assigned to given task.
     */
    public List<Comment> getCommentsForTask (Integer taskId) {return listCommentsUnderTask(taskId);}
    /**
     * Returns {@link List} of all {@link Comment} assigned to given task.
     * @param userName username of the user whose comments we want to get.
     * @return {@link List} of all {@link Comment} assigned to given task.
     */
    public List<Comment> getCommentsForUser (String userName) {return listCommentsUnderUser(userName);}

    /**
     * Moves given task to "To Do" {@link Category}.
     * @param task {@link Task} which we want to move to another category.
     */
    public void moveOnTodo(Task task){
        moveToCategory(task, Category.TODO);
    }
    /**
     * Moves given task to "Doing" {@link Category}.
     * @param task {@link Task} which we want to move to another category.
     */
    public void moveOnDoing(Task task){
        moveToCategory(task, Category.DOING);
    }
    /**
     * Moves given task to "Closed" {@link Category}.
     * @param task {@link Task} which we want to move to another category.
     */
    public void moveOnClosed(Task task){
        moveToCategory(task, Category.CLOSED);
    }
    /**
     * Moves given task to "Open" {@link Category}.
     * @param task {@link Task} which we want to move to another category.
     */
    public void moveOnOpen(Task task){
        moveToCategory(task, Category.OPEN);
    }

    /**
     * Moves given task to given category.
     * @param task {@link Task} which we want to move to another category.
     * @param category  destination {@link Category}
     */
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
}
