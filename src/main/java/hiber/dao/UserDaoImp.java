package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        String HQL = "FROM User user where user.email = :email";
        User user1 = sessionFactory.getCurrentSession().createQuery(HQL, User.class)
                .setParameter("email", user.getEmail()).uniqueResult();
        if (user1 == null) {
            sessionFactory.getCurrentSession().save(user);
        } else {
            System.out.println("User с таким email уже существует");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("FROM User");
        return query.getResultList();
    }


    @Transactional
    @Override
    public User getUserByModelCarSeries(String model, int series) {
        User user =sessionFactory.getCurrentSession().createQuery("SELECT user FROM User user " +
                "WHERE user.car.model = :model AND user.car.series = :series",User.class)
                .setParameter("model",model)
                .setParameter("series", series)
                .uniqueResult();
        if (user == null){
            System.out.println("User не найден");
            return null;
        }
        return user;
    }
    @Transactional
    @Override
    public User getUserByModelCarSeries(Car car) {
        User user =sessionFactory.getCurrentSession().createQuery("SELECT user FROM User user " +
                "WHERE user.car.model = :model AND user.car.series = :series",User.class)
                .setParameter("model",car.getModel())
                .setParameter("series",car.getSeries())
                .uniqueResult();
        if (user == null){
            System.out.println("User не найден");
            return null;
        }
        return user;
    }
}
