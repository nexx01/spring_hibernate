package hiber;

import hiber.config.AppConfig;
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
        User user1 = new User("User1", "Lastname1", "user1@mail.ru");
        User user2 = new User("User2", "Lastname2", "user2@mail.ru");
        User user3 = new User("User3", "Lastname3", "user3@mail.ru");
        User user4 = new User("User4", "Lastname4", "user4@mail.ru");

        user1.setCar(new Car("Car1", 52, 3334));
        user2.setCar(new Car("Car2", 66, 1234));
        user3.setCar(new Car("Car3", 14, 4321));

        userService.add(user1);
        userService.add(user2);
        userService.add(user3);
        userService.add(user4);


        List<User> users = userService.listUsers();
        for (User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            if (user.getCar() != null) {
                System.out.println("Car: name = " + user.getCar().getName() + ", series = " + user.getCar().getSeries());
            }
            System.out.println();
        }

        getOwnerCar(52, 3334, userService);
        getOwnerCar(66, 1234, userService);

        context.close();
    }

    public static void getOwnerCar(int series, int number, UserService userService) {
        System.out.printf("\n");
        User userOwner = userService.getOwnerCar(series, number);
        if (userOwner != null) {
            System.out.println("Владелец машины: Серия " + userOwner.getCar().getSeries()
                    + " Номер " + userOwner.getCar().getNumber());
            System.out.println("First Name = " + userOwner.getFirstName());
            System.out.println("Last Name = " + userOwner.getLastName());
            System.out.println("Email = " + userOwner.getEmail());
        } else {
            System.out.println("Машины с такими данными нет в БД");
        }
    }
}
