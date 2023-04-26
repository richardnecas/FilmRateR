package managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String url = "jdbc:sqlite:C:/Users/rikon/Documents/Code/DB/filmrater.db";
    private static Connection conn = null;
    public static void connect() {

        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        }
         catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Connection getConn(){
        return conn;
    }

    public static void disconnectConn(){
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
