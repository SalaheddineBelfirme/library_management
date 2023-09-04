package copies;
import db.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class copies {
    private  int id;
    private  String ISBN;
    private String  status;

    public copies(String isbn, String status) {
        ISBN = isbn;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }


    public void   AddCopies(int numberCopies,String ISBN) throws SQLException {

        for (int i=0;i<numberCopies;i++){

            try(Connection connection=DatabaseManager.getConnection();) {
                String requet="insert into copies (id,ISBN,status) values (?,?,disponble)";
                try(PreparedStatement preparedStatement=connection.prepareStatement(requet)) {
                    preparedStatement.setString(1, ISBN);
                    preparedStatement.executeUpdate();

                }catch (SQLException e){
                    e.printStackTrace();

                }

            }catch (SQLException e){



            }



        }

    }

}
