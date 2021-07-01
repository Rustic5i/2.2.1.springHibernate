package hiber;

import hiber.config.AppConfig;
import hiber.dao.UserDaoImp;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);

        userService.add(new User("User1", "Lastname1", "user1@mail.ru"));
        userService.add(new User("User2", "Lastname2", "user2@mail.ru"));
        userService.add(new User("User3", "Lastname3", "user3@mail.ru"));
        userService.add(new User("User4", "Lastname4", "user4@mail.ru"));

        List<User> users1 = userService.listUsers();
        for (User forUser1 : users1) {
            System.out.println("Id = " + forUser1.getId());
            System.out.println("First Name = " + forUser1.getFirstName());
            System.out.println("Last Name = " + forUser1.getLastName());
            System.out.println("Email = " + forUser1.getEmail());
            System.out.println();
        }

        Car car1 = new Car("Porsche", 1111);
        User user1 = new User("Петя", "Сергеев", "petia@mail.ru");
        user1.setCar(car1);
        userService.add(user1);

        Car car2 = new Car("volvo", 2222);
        User user2 = new User("Руслан", "Баратов", "rus2@mail.ru");
        user2.setCar(car2);
        userService.add(user2);

        Car car3 = new Car("bmw x5", 333);
        User user3 = new User("Макс", "Швалин", "max3@mail.ru");
        user3.setCar(car3);
        userService.add(user3);

        List<User> users = userService.listUsers();
        for (User user : users) {
            System.out.println(user);
        }

        User user = userService.getUserByModelCarSeries("volvo", 2222);
        System.out.println("User найден " + user);

        user = userService.getUserByModelCarSeries(car2);
        System.out.println("User найден " +user);
        context.close();
    }
}
