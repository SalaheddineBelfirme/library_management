package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost/libraries";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("connctio done");
        } catch (SQLException exception) {
            System.out.println("something went wrong");
            System.out.println(exception.getMessage());
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch(SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }
}