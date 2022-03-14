package com.pwr.dpp.backlog.dpp.business;

import com.pwr.dpp.backlog.dpp.business.orm.Comment;
import com.pwr.dpp.backlog.dpp.business.orm.Task;

import java.util.List;

public interface DatabaseHandler {
    List<Task> getAllTasks();
    void saveTask(Task task);
    void deleteTask(Task task);
    void saveTasks(List<Task> tasks);
    List<Comment> getCommentsForTask(Integer taskId);
    List<Comment> getAllComments();
    void saveComment(Comment comment);
    void saveComments(List<Comment>);
    List<String> getAllUsers();
    boolean createUser(String username);
    boolean logAs(String username) throws NoSuchUserException;

}
