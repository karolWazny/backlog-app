package com.pwr.dpp.backlog.dpp.business;

import com.pwr.dpp.backlog.dpp.business.orm.Comment;
import com.pwr.dpp.backlog.dpp.business.orm.Task;

import java.util.List;

public class ApplicationSetup {
    public static MainController setup(){
        MainController mainController = new MainController();
        mainController.setDatabaseHandler(new DatabaseHandler() {
            @Override
            public List<Task> getAllTasks() {
                return null;
            }

            @Override
            public List<Comment> getAllComments() {
                return null;
            }

            @Override
            public List<Comment> getCommentsForTask(Integer taskId) {
                return null;
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
            public void saveTask(Task task) {

            }

            @Override
            public void deleteTask(Task task) {

            }

            @Override
            public void saveTasks(List<Task> tasks) {

            }

            @Override
            public boolean logAs(String username) throws NoSuchUserException {
                return false;
            }

            @Override
            public void saveComment(Comment comment) {

            }

            @Override
            public void saveComments() {

            }
        });
        mainController.setLoggedUserRepository(LoggedUserRepositoryImpl.getInstance());
        return mainController;
    }
}
