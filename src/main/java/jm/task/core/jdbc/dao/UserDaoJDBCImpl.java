package jm.task.core.jdbc.dao;
import java.sql.*;
import java.util.ArrayList;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.util.List;



public class UserDaoJDBCImpl implements UserDao {

    private final Connection connection = Util.getConnection();

    public void createUsersTable() {

        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE users(id INT AUTO_INCREMENT PRIMARY KEY,name CHAR(45),lastName CHAR(45), age INT)");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE users");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(PreparedStatement pp = connection.prepareStatement("INSERT INTO users(name,lastName,age) VALUES(?,?,?)")) {
            pp.setString(1, name);
            pp.setString(2, lastName);
            pp.setByte(3, age);
            pp.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try(PreparedStatement pp = connection.prepareStatement("DELETE FROM users WHERE ID = ?")){
            pp.setLong(1, id);
            pp.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while(resultSet.next()) {
                User user = new User();
                user.setAge(resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                userList.add(user);
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;

    }

    public void cleanUsersTable() {
        try(Statement statement = connection.createStatement()){
            statement.execute("TRUNCATE TABLE users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
