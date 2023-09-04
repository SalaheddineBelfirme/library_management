package copies;

import db.DatabaseManager;

import java.sql.*;



public class copiesService {

    public static void AddCopies(int numberCopies,String ISBN) throws SQLException {

        for (int i = 0; i < numberCopies; i++) {

            try (Connection connection = DatabaseManager.getConnection();) {
                String requet = "insert into copies (ISBN,status) values (?,?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(requet)) {
//
                    preparedStatement.setString(1, ISBN);
                    preparedStatement.setString(2, "disponbl");
                    preparedStatement.executeUpdate();
                    System.out.println("ur copis is add ");

                } catch (SQLException e) {
                    e.printStackTrace();

                }

            } catch (SQLException e) {
                e.printStackTrace();


            }


        }


    }
    }
