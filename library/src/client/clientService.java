package client;

import copies.copies;
import db.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class clientService {

    public  static int addClient(int memberNumber,String name){
       int res=0;
        try (Connection connection = DatabaseManager.getConnection()) {
            String query = "insert into  client(memberNumber,name) values (?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, memberNumber);
                preparedStatement.setString(2, name);
                 res = preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;

    }
    public static client searchClientByMemberNumber(int memberNumber){
        client client=null;

        try (Connection connection = DatabaseManager.getConnection()) {
            String query = "SELECT * FROM client WHERE memberNumber = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, memberNumber);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String name= resultSet.getString("name");
                     client = new client(memberNumber,name);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



        return client;
    };

    public static List<java.util.Date> checkClientInExternal(int memberNumber){
        List<Date> dates=new ArrayList<>();

        try (Connection connection = DatabaseManager.getConnection()) {
            String query = "SELECT * FROM externalns WHERE memberNumber = ? limit 1";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, memberNumber);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()){
                 dates.add(resultSet.getDate("startDate"));
                 dates.add(resultSet.getDate("endDate"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



        return dates;
    };

}
