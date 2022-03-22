package com.pwr.dpp.backlog.dpp.business;

import com.pwr.dpp.backlog.dpp.business.SQLiteHandler;

import java.sql.*;

/**
 * The class which implements SQLitehandler interface. Prepares and execute SQL statements.
 *
 * @author tgorniak_252697
 */
public class Database implements SQLiteHandler  {
    private static Connection connection;

    /**
     * Class constructor which setting connection to database.
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     *
     * * @author tgorniak_252697
     */
    public static void Database() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:" + System.getProperty("user.dir") + "db.db");
    }


    /**
     * Method that returns the result of SQL statements.
     *
     * @param sql SQL statement in type String
     *
     * @return result of SQL statement in ResultSet type
     *
     * @throws SQLException
     *
     * * @author tgorniak_252697
     */
    public static ResultSet getStatementValues(String sql) throws SQLException{
        Statement statement = connection.prepareStatement(sql);
        return statement.executeQuery(sql);
    }

    @Override
    public ResultSet getTasks() throws SQLException {
        String procedure = "SELECT * FROM Tasks;";
        return getStatementValues(procedure);
    }
    @Override
    public ResultSet getComments() throws SQLException{
        String sql = "SELECT * FROM Commentary;";
        return getStatementValues(sql);
    }

    @Override
    public void addNewTask(String Title, String Name, String Category, String Description, String Date) throws SQLException {
        String sql = "INSERT INTO Tasks(Title, Name, Category, Description, Date)" +
                "VALUES(?, ?, ?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, Title);
        statement.setString(2, Name);
        statement.setString(3, Category);
        statement.setString(4, Description);
        statement.setString(5, Date);
        statement.executeQuery();
    }

    @Override
    public void deleteTask(int Task_id) throws SQLException {
        String sql = "DELETE FROM Commentary WHERE Task_id = ?" +
                "DELETE FROM Categories WHERE Task_id = ?" +
                "DELETE FROM Tasks WHERE Task_id = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, Integer.toString(Task_id));
        statement.setString(2, Integer.toString(Task_id));
        statement.setString(3, Integer.toString(Task_id));
        statement.executeQuery();
    }

    @Override
    public void changeTaskCategory(int Task_id, String Category) throws SQLException {
        String sql = "UPDATE Tasks SET Category = ? WHERE Task_id = ?" +
                "UPDATE Categories SET Category = ? WHERE Task_id = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, Category);
        statement.setString(2, Integer.toString(Task_id));
        statement.setString(3, Category);
        statement.setString(4, Integer.toString(Task_id));
        statement.executeQuery();
    }

    @Override
    public void changeUserTask(int Task_id, String Name) throws SQLException {
        String sql = "UPDATE Tasks SET Name = ? WHERE Task_id = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, Name);
        statement.setString(2, Integer.toString(Task_id));
        statement.executeQuery();
    }

    @Override
    public void deleteUserFromTask(int Task_id) throws SQLException {
        String sql = "UPDATE Tasks SET Name = ? WHERE Task_id = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, null);
        statement.setString(2, Integer.toString(Task_id));
        statement.executeQuery();
    }

    @Override
    public void createNewUser(String Name) throws SQLException {
        String sql = "INSERT INTO Users(Name) VALUES(?);";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, Name);
        statement.executeQuery();
    }
}
