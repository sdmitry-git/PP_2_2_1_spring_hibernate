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

//       userService.clearUsers();

      User user1 = new User("Дмитрий", "Иванов", "Dmitriy.Ivanov@mail.ru");
      user1.setCar(new Car("BMW", 135));

      User user2 = new User("Иван", "Фёдоров", "Ivan.Fyodorov@mail.ru");
      user2.setCar(new Car("Toyota", 156));

      User user3 = new User("Елена", "Петрова", "Elena.Petrova@mail.ru");
      user3.setCar(new Car("Ford", 194));

      User user4 = new User("Виктория", "Карпова", "Victoria.Karpova@mail.ru");
      user4.setCar(new Car("Jeep", 101));

      userService.add(user1);
      userService.add(user2);
      userService.add(user3);
      userService.add(user4);

      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         if (user.getCar() != null) {
             System.out.println("Car = " + user.getCar().getModel() + " " + user.getCar().getSeries());
         }
         System.out.println();
      }

      User found = userService.getUserByCarModelAndSeries("Toyota", 156);
      if (found != null)  {
          System.out.println("Владелец Toyota 156: " + found.getFirstName() + " " + found.getLastName());
      } else {
          System.out.println("Владелец Toyota 156 не найден");
      }

      context.close();
   }
}
