package com.pwr.dpp.backlog.dpp.business;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interface for methods which using SQLite database.
 *
 * @author tgorniak_252697
 */
public interface SQLiteHandler {
    /**
     * Get all tasks from database.
     *
     * @return all records from Tasks table in ResultSet type
     *
     * @throws SQLException;
     *
     * @author tgorniak_252697
     */
    ResultSet getTasks() throws SQLException;

    /**
     * Get all comments from database.
     *
     * @return all records from Commentary table in ResultSet type
     *
     * @throws SQLException;
     *
     * @author tgorniak_252697
     */
    ResultSet getComments() throws SQLException;

    /**
     * Adding new task to database.
     *
     * @param Title the title of the task in type String
     * @param Name the name of the user assigned to the task in type String
     * @param Category the name of task's category in type String
     * @param Description task description in type String
     * @param Date creation date of task in type String (date format was not specified yet)
     *
     * @throws SQLException;
     *
     * @author tgorniak_252697
     */
    void addNewTask(String Title, String Name, String Category, String Description, String Date) throws SQLException;

    /**
     * Deleting task by it's index.
     *
     * @param Task_id index of task
     *
     * @throws SQLException;
     *
     * @author tgorniak_252697
     */
    void deleteTask(int Task_id) throws SQLException;

    /**
     * Changing task's category.
     *
     * @param Task_id index of task
     * @param Category name of new category
     *
     * @throws SQLException;
     *
     * @author tgorniak_252697
     */
    void changeTaskCategory(int Task_id, String Category) throws SQLException;

    /**
     * Assigning another user to the task.
     *
     * @param Task_id index of task
     * @param Name new name of user
     *
     * @throws SQLException;
     *
     * @author tgorniak_252697
     */
    void changeUserTask(int Task_id, String Name) throws SQLException;

    /**
     * Deleting user from task.
     *
     * @param Task_id index of task
     *
     * @throws SQLException;
     *
     * @author tgorniak_252697
     */
    void deleteUserFromTask(int Task_id) throws SQLException;

    /**
     * Creating new user.
     *
     * @param Name nome of new user
     *
     * @throws SQLException
     *
     * @author tgorniak_252697
     */
    void createNewUser(String Name) throws SQLException;
}
