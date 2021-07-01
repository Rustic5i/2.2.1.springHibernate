package hiber.dao;

import hiber.model.Car;
import hiber.model.User;

import java.util.List;

public interface UserDao {
   void add(User user);
   List<User> listUsers();
   User getUserByModelCarSeries(String model,int series);
   User getUserByModelCarSeries(Car car);
}
