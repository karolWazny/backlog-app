package com.pwr.dpp.backlog.dpp.business;

import com.pwr.dpp.backlog.dpp.business.orm.Comment;
import com.pwr.dpp.backlog.dpp.business.orm.Task;

import java.util.List;

public interface DatabaseHandler {
    List<Task> getAllTasks();
    List<Comment> getAllComments();
    boolean createUser(String username);
    void saveTask(Task task);
    boolean logAs(String username) throws NoSuchUserException;
}
