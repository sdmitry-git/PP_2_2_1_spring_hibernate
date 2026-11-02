package hiber.dao;

import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUserByCarModelAndSeries(String model, int series) {
       TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("SELECT u FROM User u  WHERE u.car.model  = :model and u.car.series = :series", User.class);

       query.setParameter("model",  model);
       query.setParameter("series", series);

       try {
           return query.getSingleResult();
       } catch (NoResultException e) {
           System.out.println("Ничего не найдено");
           return null;
       }
   }

   @Override
   public void clearUsers(){
       Session session = sessionFactory.getCurrentSession();
       session.createQuery("DELETE FROM User").executeUpdate();
       session.createQuery("DELETE FROM Car").executeUpdate();
       session.createNativeQuery("ALTER TABLE users AUTO_INCREMENT = 1").executeUpdate();
       session.createNativeQuery("ALTER TABLE car AUTO_INCREMENT = 1").executeUpdate();
   }
}
