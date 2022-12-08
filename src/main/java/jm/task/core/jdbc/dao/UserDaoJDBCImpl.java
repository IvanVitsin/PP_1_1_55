package jm.task.core.jdbc.dao;
import java.sql.*;
import java.util.ArrayList;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.util.List;



public class UserDaoJDBCImpl implements UserDao {

    private Connection connection;

    public void createUsersTable() {

        try (Connection connection = Util.getConnection();
             Statement statement =  connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS users(id INT AUTO_INCREMENT PRIMARY KEY,name CHAR(45),lastName CHAR(45), age INT)");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS users");
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(Connection connection = Util.getConnection();
            PreparedStatement pp = connection.prepareStatement("INSERT INTO users(name,lastName,age) VALUES(?,?,?)")) {
            pp.setString(1, name);
            pp.setString(2, lastName);
            pp.setByte(3, age);
            pp.executeUpdate();
            connection.commit();

        } catch (SQLException e) {

            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try(Connection connection = Util.getConnection();
            PreparedStatement pp = connection.prepareStatement("DELETE FROM users WHERE ID = ?")){
            pp.setLong(1, id);
            pp.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try(Connection connection = Util.getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while(resultSet.next()) {
                User user = new User();
                user.setAge(resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                userList.add(user);
                connection.commit();
                System.out.println(user.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }
        return userList;

    }

    public void cleanUsersTable() {

        try(Connection connection = Util.getConnection();
            Statement statement = connection.createStatement()){
            statement.execute("TRUNCATE TABLE users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
