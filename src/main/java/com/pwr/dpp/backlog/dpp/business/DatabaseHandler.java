package com.pwr.dpp.backlog.dpp.business;

import com.pwr.dpp.backlog.dpp.business.orm.Comment;
import com.pwr.dpp.backlog.dpp.business.orm.Task;

import java.util.List;

public interface DatabaseHandler {
    List<Task> getTasks();
    void saveTask(Task task);
    void deleteTask(Task task);
    List<Comment> getCommentsForTask(Integer taskId);
    List<Comment> getComments();
    void saveComment(Comment comment);
    List<String> getUsers();
    boolean createUser(String username);
    boolean logAs(String username);
    void save();
    void load();

}
