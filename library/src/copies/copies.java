package copies;
import db.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class copies {
    private  int id;
    private  String ISBN;
    private String  status;

    public copies(int id ,String isbn, String status) {
        this.id=id;
        ISBN = isbn;
        this.status = status;
    }


    public int getId() {
        return id;
    }

    public String getISBN() {
        return ISBN;
    }


@Override
    public  String toString(){
        return  "Id :"+id+
               " \n ISBN :"+ISBN+
               " \n Stsatu :"+status;

}


}
