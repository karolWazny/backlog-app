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

    public BoardModel boardModel(){
        return boardModel;
    }

    public LogInModel logInModel(){
        logInModel.setLoggedUserRepository(loggedUserRepository);
        return logInModel;
    }

    public CreateTaskModel createTaskModel(){
        return createTaskModel;
    }

    public LoggedUserRepository loggedUserRepository(){
        return loggedUserRepository;
    }

    public TaskDetailsModel taskDetailsModel(Task task){
        TaskDetailsModel taskDetailsModel = new TaskDetailsModel(task);
        taskDetailsModel.setDatabaseHandler(databaseHandler);
        taskDetailsModel.setLoggedUserRepository(loggedUserRepository);
        return taskDetailsModel;
    }

    public DatabaseHandler databaseHandler(){
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
