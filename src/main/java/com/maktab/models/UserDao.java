package com.maktab.models;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDao {
    protected static SessionFactory sessionFactory = HibernateUtil.sessionFactory;

    public List fetch() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            List users = session.createQuery(
                    "from User"
            ).list();
            transaction.commit();
            System.out.println("COMPLETED...");
            return users;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return null;
    }

    /*public void save(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            System.out.println("COMPLETED...");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }*/

    /*public static void main(String[] args) {
        User user = new User();
        user.setUserName("0312182201");
        user.setPassword("1234");
        save(user);
        user.setUserName("0313900590");
        user.setPassword("5678");
        save(user);
    }*/
}
