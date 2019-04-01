


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    private Connection c;
    private static ConnectionDB connectionDB = new ConnectionDB();
    private ConnectionDB(){
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:base.db");
            c.setAutoCommit(true);
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        return c;
    }

    public static ConnectionDB getConnectionDB() {
        return connectionDB;
    }

}
