package com.pwr.dpp.backlog.dpp.business;

import com.pwr.dpp.backlog.dpp.business.orm.Task;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.util.LinkedList;
import java.util.List;

public class TaskDetailsModel implements Observable {
    private Task task;
    private DatabaseHandler databaseHandler;
    private final List<InvalidationListener> listeners = new LinkedList<>();
    private LoggedUserRepository loggedUserRepository;

    public void deleteTask(){
        databaseHandler.deleteTask(task);
        task = null;
        invalidate();
    }

    public void addComment(String comment){
        String user = loggedUserRepository.getLoggedUser();

        throw new UnsupportedOperationException();
    }

    public DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }

    public void setDatabaseHandler(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
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

    public void setTask(Task task) {
        this.task = task;
    }
}
