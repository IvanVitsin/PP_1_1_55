package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД


    private static final String USERNAME = "root";
    private static final String PASSWORD = "rootroot";
    private static final String URL = "jdbc:mysql://localhost:3306/preproj114";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static Connection connection;

    public static Connection getConnection() {
      //  try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD))
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            connection.setAutoCommit(false);
            Class.forName(DRIVER);
        } catch (SQLException | ClassNotFoundException e) {

            e.printStackTrace();
        }
        return connection;
    }


}



