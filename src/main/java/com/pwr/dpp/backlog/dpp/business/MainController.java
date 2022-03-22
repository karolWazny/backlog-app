package com.pwr.dpp.backlog.dpp.business;

import com.pwr.dpp.backlog.dpp.business.models.BoardModel;
import com.pwr.dpp.backlog.dpp.business.models.CreateTaskModel;
import com.pwr.dpp.backlog.dpp.business.models.LogInModel;
import com.pwr.dpp.backlog.dpp.business.models.TaskDetailsModel;
import com.pwr.dpp.backlog.dpp.business.orm.Task;

/**
 * Used to access data in database.
 */
public class MainController {
    private DatabaseHandler databaseHandler;
    private LoggedUserRepository loggedUserRepository;
    private BoardModel boardModel;
    private final LogInModel logInModel = new LogInModel();
    private final CreateTaskModel createTaskModel = new CreateTaskModel();

    /**
     * Returns {@link BoardModel}
     */
    public BoardModel getBoardModel(){
        return boardModel;
    }
    /**
     * Returns {@link LogInModel}
     */
    public LogInModel getLogInModel(){
        logInModel.setLoggedUserRepository(loggedUserRepository);
        return logInModel;
    }
    /**
     * Returns {@link CreateTaskModel}
     */
    public CreateTaskModel getCreateTaskModel(){
        return createTaskModel;
    }

    /**
     * Returns {@link LoggedUserRepository}
     */
    public LoggedUserRepository getLoggedUserRepository(){
        return loggedUserRepository;
    }

    /**
     * Returns {@link TaskDetailsModel}
     */
    public TaskDetailsModel getTaskDetailsModel(Task task){
        TaskDetailsModel taskDetailsModel = new TaskDetailsModel(task);
        taskDetailsModel.setDatabaseHandler(databaseHandler);
        taskDetailsModel.setLoggedUserRepository(loggedUserRepository);
        return taskDetailsModel;
    }

    /**
     * Returns {@link DatabaseHandler}
     */
    public DatabaseHandler getDatabaseHandler(){
        return databaseHandler;
    }

    /**
     * Sets given {@link DatabaseHandler}
     * @param databaseHandler database handler to set.
     */
    public void setDatabaseHandler(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
        logInModel.setDatabaseHandler(databaseHandler);
        createTaskModel.setDatabaseHandler(databaseHandler);
        boardModel = new BoardModel(databaseHandler);
    }

    /**
     * Sets given {@link LoggedUserRepository}
     * @param loggedUserRepository logged user repository.
     */
    public void setLoggedUserRepository(LoggedUserRepository loggedUserRepository) {
        this.loggedUserRepository = loggedUserRepository;
        logInModel.setLoggedUserRepository(loggedUserRepository);
    }
}
