package com.pwr.dpp.backlog.dpp.business;

import com.pwr.dpp.backlog.dpp.business.orm.Comment;
import com.pwr.dpp.backlog.dpp.business.orm.Task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class jsonDatabaseHandler implements DatabaseHandler{
    private Path commentsPath;
    private Path tasksPath;

    public jsonDatabaseHandler(String databaseDirectoryPath, String tasksFilename, String commentsFilename){
        commentsPath = Paths.get(databaseDirectoryPath + File.separator + (tasksFilename.strip().contains(".json") ? tasksFilename : tasksFilename+ ".json"));

    }


    @Override
    public List<Task> getAllTasks() {
        return null;
    }

    @Override
    public void saveTask(Task task) {

    }

    @Override
    public void deleteTask(Task task) {

    }

    @Override
    public void saveTasks(List<Task> tasks) {

    }

    @Override
    public List<Comment> getCommentsForTask(Integer taskId) {
        return null;
    }

    @Override
    public List<Comment> getAllComments() {
        return null;
    }

    @Override
    public void saveComment(Comment comment) {

    }

    @Override
    public void saveComments() {

    }

    @Override
    public List<String> getAllUsers() {
        return null;
    }

    @Override
    public boolean createUser(String username) {
        return false;
    }

    @Override
    public boolean logAs(String username) throws NoSuchUserException {
        return false;
    }
}
