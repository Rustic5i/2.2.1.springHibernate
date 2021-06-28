package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
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
        String HQL2 = "FROM Car car WHERE car.model = :model AND car.series = :series";
        Car findCarQuery = sessionFactory.getCurrentSession().createQuery(HQL2, Car.class)
                .setParameter("model", model)
                .setParameter("series", series)
                .uniqueResult();
        try {
            return findCarQuery.getUser();
        } catch (NullPointerException e) {
            System.out.println("User не найден");
            return null;
        }
    }
}
