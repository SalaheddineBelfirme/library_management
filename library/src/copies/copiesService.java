package copies;

import db.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;


public class copiesService {

    public static void AddCopies(int numberCopies,String ISBN) throws SQLException {

        for (int i = 0; i < numberCopies; i++) {

            try (Connection connection = DatabaseManager.getConnection();) {
                String requet = "insert into copies (ISBN,status) values (?,?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(requet)) {
                    preparedStatement.setString(1, ISBN);
                    preparedStatement.setString(2, "available");
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();

                }

            } catch (SQLException e) {
                e.printStackTrace();


            }


        }


    }
    public static int deleteCopiesByID(int copyID)  {
        int bol=0;
        try (Connection connection = DatabaseManager.getConnection()) {

            String query = "DELETE FROM copies WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, copyID);
                int rowsDeleted = preparedStatement.executeUpdate();
                bol=rowsDeleted;
                if (rowsDeleted > 0) {
                    System.out.println("the number1 "+bol);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bol  ;
    }


    public static List<copies> searchCopiesByISBN(String ISBN) throws SQLException {
        List<copies> copies = new ArrayList<>();

        try (Connection connection = DatabaseManager.getConnection()) {
            String query = "SELECT * FROM copies WHERE ISBN = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, ISBN);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int copyID = resultSet.getInt("id");
                    String status = resultSet.getString("status");
                    copies copy = new copies(copyID,ISBN, status);
                    copies.add(copy);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return copies;
    }


    public static copies getAvailableCobyByISBN(String ISBN) throws SQLException {
        copies copie=null;

        try (Connection connection = DatabaseManager.getConnection()) {
            String query = "SELECT * FROM copies WHERE ISBN = ? and status=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, ISBN);
                preparedStatement.setString(2, "available");
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int copyID = resultSet.getInt("id");
                    String status = resultSet.getString("status");
                     copie = new copies(copyID,ISBN, status);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return copie;
    }


    public static int[] getStatistics () throws SQLException {
        int[] Results=new int[3];

        try (Connection connection = DatabaseManager.getConnection()) {
            Statement statement = connection.createStatement();

            // Execute the first query
            ResultSet availableResult = statement.executeQuery("SELECT COUNT(*) FROM copies WHERE status = 'available'");
            if (availableResult.next()) {
               Results[0]= availableResult.getInt(1);

            }

            // Execute the second query
            ResultSet unavailableResult = statement.executeQuery("SELECT COUNT(*) FROM copies WHERE status = 'unavailable'");
            if (unavailableResult.next()) {
                Results[1] = unavailableResult.getInt(1);
            }

            // Execute the third query
            ResultSet missingResult = statement.executeQuery("SELECT COUNT(*) FROM copies WHERE status = 'missing'");
            if (missingResult.next()) {
                Results[2]= missingResult.getInt(1);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return Results;

    }

    public  static  void  printStatistics(){

        File file = new File("C:\\\\Desktop\\\\sasjava\\\\library_management\\\\Statistics/data.txt"); // Specify the file path

        try (FileWriter fw = new FileWriter(file,true);
             BufferedWriter writer = new BufferedWriter(fw)) {

            int[] statistics = getStatistics();
            writer.newLine();
            writer.write("the number of Available books: " + statistics[0]);
            writer.newLine();
            writer.write("the number of unavailable books: " + statistics[1]);
            writer.newLine();
            writer.write("the number of missing books: " + statistics[2]);
            writer.newLine();

            System.out.println("Text has been written to the file.");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

