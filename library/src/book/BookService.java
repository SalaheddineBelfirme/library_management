package  book;
import copies.copiesService;
import  copies.copies;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.CallableStatement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;
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
                preparedStatement.setDouble(5, 0);
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
    public static int updateBook(String ISBN ,String title, String informations, String author) {
        int result=0;
        try (Connection connection = DatabaseManager.getConnection()) {
            String updateSql = "UPDATE `book` SET `title` = ?, `informations` = ?, `author` = ?  WHERE `ISBN` = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, informations);
                preparedStatement.setString(3, author);
                preparedStatement.setString(4, ISBN);

                result = preparedStatement.executeUpdate();
                System.out.println(result);

            }
        } catch (SQLException e) {
            // Handle database exceptions
            e.printStackTrace();
        }
        return result;
    }






    public static copies getUnavailableBook(String ISBN) {
        copies copies  =null;
        try (Connection connection = DatabaseManager.getConnection()) {
            String deleteSql = "select * from `copies` WHERE `ISBN` = ?  and status='unavailable'";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {
                preparedStatement.setString(1, ISBN);
                 ResultSet resultSet = preparedStatement.executeQuery();
                 if (resultSet.next()){
                     copies =new copies(resultSet.getInt("id"),resultSet.getString("isbn"),resultSet.getString("status"));

                 }
                 else {
                     System.out.println(resultSet);
                 }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return copies;
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



    public static int archivBook(String ISBN) {
        int res=0;
        if (getUnavailableBook(ISBN)==null){
            try (Connection connection = DatabaseManager.getConnection()) {
                String call = "{ call ArchiveBook(?) }";
                try (   CallableStatement callableStatement = connection.prepareCall(call);) {
                    callableStatement.setString(1, ISBN);
                    callableStatement.execute();
                    res=1;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            res= 2;
        }

        return res;
    }



    public static book getBookByISBNorTitle(String keyword) {
        try (Connection connection = DatabaseManager.getConnection()) {
            String selectSql = "SELECT * FROM `book` WHERE ISBN LIKE CONCAT('%', ?, '%') OR title LIKE CONCAT('%', ?, '%') OR author LIKE CONCAT('%', ?, '%') and archived=0;";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
                preparedStatement.setString(1, keyword);
                preparedStatement.setString(2, keyword);
                preparedStatement.setString(3, keyword);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Create a Book object from the retrieved data
                        String author = resultSet.getString("author");
                        String title = resultSet.getString("title");
                        String informations = resultSet.getString("informations");
                        int quantity = resultSet.getInt("quantity");
                        String ISBN = resultSet.getString("ISBN");
                        return new book(ISBN, author, title, informations, quantity);
                    }

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static   List<book> getAllBooks() {
        List<book> books = new ArrayList<>();

        try (Connection connection = DatabaseManager.getConnection()) {
            String selectSql = "SELECT * FROM `book` where archived=0";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String ISBN = resultSet.getString("ISBN");
                        String title = resultSet.getString("title");
                        String author = resultSet.getString("author");
                        String informations = resultSet.getString("informations");
                        int quantity = resultSet.getInt("quantity");
                        book book = new book(ISBN, title, author, informations, quantity);
                        books.add(book);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }





    public static int browBook(int idCopie, int memberNumber) {
        int Result=0;
        try (Connection connection = DatabaseManager.getConnection()) {
            String insertSql = "INSERT INTO `Externalns`(`idCopie`,`memberNumber`,`startDate`,`endDate`)  VALUES (?,?,?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
                Date utilStartDate = new Date(System.currentTimeMillis());
                java.sql.Date sqlStartDate = new java.sql.Date(utilStartDate.getTime());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(utilStartDate);
                calendar.add(Calendar.DATE, 10);
                java.sql.Date sqlEndDate = new java.sql.Date(calendar.getTime().getTime());


                preparedStatement.setInt(1,idCopie);
                preparedStatement.setInt(2, memberNumber);
                preparedStatement.setDate(3, sqlStartDate    );
                preparedStatement.setDate(4,sqlEndDate);
                Result=preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            // Handle database exceptions
            e.printStackTrace();
        }
        return Result;
    }

    public static int getCopeiByISBN(String ISBN,int memberNumber) {
        int res=0;

        try (Connection connection = DatabaseManager.getConnection()) {
            String selectSql = "SELECT copies.id from `externalns`,book,copies  WHERE book.ISBN=copies.ISBN and externalns.idCopie=copies.id and book.ISBN =? and externalns.memberNumber=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
                preparedStatement.setString(1, ISBN);
                preparedStatement.setInt(2,memberNumber );
                try (ResultSet resultSet = preparedStatement.executeQuery()) {

                    if (resultSet.next()) {
                        res= resultSet.getInt("id");
                    }

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static int returnBook(int idCopie ,int memberNumber) {
        int res=0;
        try (Connection connection = DatabaseManager.getConnection()) {
            String insertSql = "DELETE FROM `externalns` WHERE  idCopie = ? AND memberNumber =?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
                preparedStatement.setInt(1,idCopie);
                preparedStatement.setInt(2,memberNumber);


                res=preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }





}
