package com.pwr.dpp.backlog.dpp.business;

import com.pwr.dpp.backlog.dpp.business.models.BoardModel;
import com.pwr.dpp.backlog.dpp.business.models.CreateTaskModel;
import com.pwr.dpp.backlog.dpp.business.models.LogInModel;
import com.pwr.dpp.backlog.dpp.business.models.TaskDetailsModel;
import com.pwr.dpp.backlog.dpp.business.orm.Task;

public class MainController {
    private DatabaseHandler databaseHandler;
    private LoggedUserRepository loggedUserRepository;
    private BoardModel boardModel;
    private final LogInModel logInModel = new LogInModel();
    private final CreateTaskModel createTaskModel = new CreateTaskModel();

    public BoardModel getBoardModel(){
        return boardModel;
    }

    public LogInModel getLogInModel(){
        logInModel.setLoggedUserRepository(loggedUserRepository);
        return logInModel;
    }

    public CreateTaskModel getCreateTaskModel(){
        return createTaskModel;
    }

    public LoggedUserRepository getLoggedUserRepository(){
        return loggedUserRepository;
    }

    public TaskDetailsModel getTaskDetailsModel(Task task){
        TaskDetailsModel taskDetailsModel = new TaskDetailsModel(task);
        taskDetailsModel.setDatabaseHandler(databaseHandler);
        taskDetailsModel.setLoggedUserRepository(loggedUserRepository);
        return taskDetailsModel;
    }

    public DatabaseHandler getDatabaseHandler(){
        return databaseHandler;
    }

    public void setDatabaseHandler(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
        logInModel.setDatabaseHandler(databaseHandler);
        createTaskModel.setDatabaseHandler(databaseHandler);
        boardModel = new BoardModel(databaseHandler);
    }

    public void setLoggedUserRepository(LoggedUserRepository loggedUserRepository) {
        this.loggedUserRepository = loggedUserRepository;
        logInModel.setLoggedUserRepository(loggedUserRepository);
    }
}
