package com.pwr.dpp.backlog.dpp.business;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SQLiteHandler {
    ResultSet getTasks() throws SQLException;
    ResultSet getComments() throws SQLException;
    void addNewTask(String Title, String Name, String Category, String Description, String Date) throws SQLException;
    void deleteTask(int Task_id) throws SQLException;
    void changeTaskCategory(int Task_id, String Category) throws SQLException;
    void changeUserTask(int Task_id, String Name) throws SQLException;
    void deleteUserFromTask(int Task_id) throws SQLException;
    void createNewUser(String Name) throws SQLException;
}
