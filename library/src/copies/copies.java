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



}
