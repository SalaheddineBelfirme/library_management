package copies;

import db.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


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

                if (rowsDeleted > 0) {
                    bol=1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bol;
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







}
