package  book;
import copies.copiesService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DatabaseManager;


public class BookService {


    public static int createBook(String ISBN, String title,String informations, String author, int quantity ) {
        int Result=0;
        try (Connection connection = DatabaseManager.getConnection()) {
            String insertSql = "INSERT INTO `book` (`ISBN`,`author`,`title`,`informations`, `quantity`)  VALUES (?,?,?,?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
                preparedStatement.setString(1, ISBN);
                preparedStatement.setString(2, author);
                preparedStatement.setString(3, title);
                preparedStatement.setString(4, informations);
                preparedStatement.setDouble(5, quantity);
                Result=preparedStatement.executeUpdate();
                if (Result>0){
                    //  add the copies of the new book
                    copiesService.AddCopies(quantity,ISBN);
                }
            }
        } catch (SQLException e) {
            // Handle database exceptions
            e.printStackTrace();
        }
        return Result;
    }
    public static int updateBook(String ISBN, String title, String informations, String author, int quantity) {
        int result=0;
        try (Connection connection = DatabaseManager.getConnection()) {
            String updateSql = "UPDATE `book` SET `title` = ?, `informations` = ?, `author` = ?, `quantity` = ? WHERE `ISBN` = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, informations);
                preparedStatement.setString(3, author);
                preparedStatement.setInt(4, quantity);
                preparedStatement.setString(5, ISBN);
                result = preparedStatement.executeUpdate();
                System.out.println(result);

            }
        } catch (SQLException e) {
            // Handle database exceptions
            e.printStackTrace();
        }
        return result;
    }






    public boolean deleteBook(String ISBN) {
        boolean result=false;
        try (Connection connection = DatabaseManager.getConnection()) {
            String deleteSql = "DELETE FROM `book` WHERE `ISBN` = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {
                preparedStatement.setString(1, ISBN);
                 result = preparedStatement.execute();
            }
        } catch (SQLException e) {
            // Handle database exceptions
            e.printStackTrace();
        }
        return  result;
    }

    public static book getBookByISBN(String ISBN) {
        try (Connection connection = DatabaseManager.getConnection()) {
            String selectSql = "SELECT * FROM `book` WHERE `ISBN` = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
                preparedStatement.setString(1, ISBN);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Create a Book object from the retrieved data
                        String author = resultSet.getString("author");
                        String title = resultSet.getString("title");
                        String informations = resultSet.getString("informations");
                        int quantity = resultSet.getInt("quantity");
                        return new book(ISBN, author, title, informations, quantity);
                    }
                }
            }
        } catch (SQLException e) {
            // Handle database exceptions
            e.printStackTrace();
        }
        return null; // Book not found or an error occurred
    }



    public static   List<book> getAllBooks() {
        List<book> books = new ArrayList<>();

        try (Connection connection = DatabaseManager.getConnection()) {
            String selectSql = "SELECT * FROM `book`"; // Assuming `book` is your table name
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String ISBN = resultSet.getString("ISBN");
                        String title = resultSet.getString("title");
                        String author = resultSet.getString("author");
                        String informations = resultSet.getString("informations");
                        int quantity = resultSet.getInt("quantity");

                        // Create a Book object from the database data
                        book book = new book(ISBN, title, author, informations, quantity);

                        // Add the book to the list
                        books.add(book);
                    }
                }
            }
        } catch (SQLException e) {
            // Handle database exceptions
            e.printStackTrace();
        }

        return books;
    }


}
