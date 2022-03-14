package com.pwr.dpp.backlog.dpp.business;

import com.pwr.dpp.backlog.dpp.business.models.BoardModel;
import com.pwr.dpp.backlog.dpp.business.models.CreateTaskModel;
import com.pwr.dpp.backlog.dpp.business.models.LogInModel;
import com.pwr.dpp.backlog.dpp.business.models.TaskDetailsModel;
import com.pwr.dpp.backlog.dpp.business.orm.Task;

public class MainController {
    private DatabaseHandler databaseHandler;
    private LoggedUserRepository loggedUserRepository;

    public BoardModel boardModel(){
        BoardModel boardModel = new BoardModel(databaseHandler);
        boardModel.setLoggedUserRepository(loggedUserRepository);
        return boardModel;
    }

    public LogInModel logInModel(){
        LogInModel output = new LogInModel();
        output.setLoggedUserRepository(loggedUserRepository);
        output.setDatabaseHandler(databaseHandler);
        return output;
    }

    public CreateTaskModel createTaskModel(){
        CreateTaskModel createTaskModel = new CreateTaskModel();
        createTaskModel.setDatabaseHandler(databaseHandler);
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
    }

    public void setLoggedUserRepository(LoggedUserRepository loggedUserRepository) {
        this.loggedUserRepository = loggedUserRepository;
    }
}
