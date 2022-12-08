package jm.task.core.jdbc;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(MysqlxDatatypes.Scalar.String[] args) {


        UserService userService = new UserServiceImpl();

        userService.dropUsersTable();

        userService.createUsersTable();

        userService.saveUser("Ivan", "Vitsin", (byte) 28);
        userService.saveUser("Dodik", "Dodikovich", (byte) 30);
        userService.saveUser("Valera", "Volkov", (byte) 31);
        userService.saveUser("Diana", "Kolmykova", (byte) 38);

        userService.getAllUsers();

        System.out.println(userService.getAllUsers());


        userService.cleanUsersTable();

    }

}
