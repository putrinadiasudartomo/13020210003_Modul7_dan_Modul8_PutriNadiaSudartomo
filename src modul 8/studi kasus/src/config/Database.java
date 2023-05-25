package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static Connection connection;

    public static Connection startConnection() {
        String url = "jdbc:mysql://localhost:3306/pbo_modul_8";
        String username = "root";
        String password = "";

        try {
            DriverManager.registerDriver(new com.mysql
                    .jdbc.Driver());
            connection = DriverManager.getConnection(url, username, password);

        } catch (SQLException exc) {
            System.out.println("Koneksi error : " + exc.getMessage());
        }
        return connection;
    }
    
    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException exc) {
            System.out.println("FAILED TO CLOSE DATABASE CONNECTION : " + exc.getMessage());
        }
    }
}
