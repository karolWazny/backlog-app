import java.sql.*;
import java.util.ArrayList;

public class Database {
    private static Connection con;
    //funkcje do komunikacji z bazą
    public static void Database() throws ClassNotFoundException, SQLException {
        Connection con = null;
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:C:/Users/Tomek/Desktop/test/test_db_connection/test.db"); //po jdbc:sqlite:C: wpisujemy scieżkę do bazy
    }

    public static ResultSet getStatementValues(String sql) throws SQLException{
        Statement statement = con.prepareStatement(sql);
        return statement.executeQuery(sql);

    }

    //funkcje wywolujące procedury
    public static ResultSet getTasks() throws SQLException { //-zwraca zawartość tabel typu ResultSet
        String procedure = "SELECT * FROM Tasks;";
        return getStatementValues(procedure);
    }

    public static ResultSet getCommentary() throws SQLException{ //-zwraca zawartość tabel typu ResultSet
        String sql = "SELECT * FROM Commentary;";
        return getStatementValues(sql);
    }

    public static void createNewTask(String Title, String Name, String Category, String Description, String Date) throws SQLException {
        String sql = "INSERT INTO Tasks(Title, Name, Category, Description, Date)" +
                     "VALUES(?, ?, ?, ?, ?);";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, Title);
        statement.setString(2, Name);
        statement.setString(3, Category);
        statement.setString(4, Description);
        statement.setString(5, Date);
        statement.executeQuery();
    }

    public static void deleteTask(String Task_id) throws SQLException {
        String sql = "DELETE FROM Commentary WHERE Task_id = ?" +
                     "DELETE FROM Categories WHERE Task_id = ?" +
                     "DELETE FROM Tasks WHERE Task_id = ?;";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, Task_id);
        statement.setString(2, Task_id);
        statement.setString(3, Task_id);
        statement.executeQuery();
    }

    public static void changeTaskCategory(String Task_id, String Category) throws SQLException {
        String sql = "UPDATE Tasks SET Category = ? WHERE Task_id = ?" +
                     "UPDATE Categories SET Category = ? WHERE Task_id = ?;";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, Category);
        statement.setString(2, Task_id);
        statement.setString(3, Category);
        statement.setString(4, Task_id);
        statement.executeQuery();
    }

    public static void changeUserTask(String Task_id, String Name) throws SQLException {
        String sql = "UPDATE Tasks SET Name = ? WHERE Task_id = ?;";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, Name);
        statement.setString(2, Task_id);
        statement.executeQuery();
    }

    public static void deleteUserFromTask(String Task_id) throws SQLException {
        String sql = "UPDATE Tasks SET Name = ? WHERE Task_id = ?;";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, null);
        statement.setString(2, Task_id);
        statement.executeQuery();
    }

    public static void createNewUser(String Name) throws SQLException {
        String sql = "INSERT INTO Users(Name) VALUES(?);";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, Name);
        statement.executeQuery();
    }
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Database();
    }
}
