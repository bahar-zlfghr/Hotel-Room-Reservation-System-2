package com.maktab.models;

import com.maktab.entities.ReserveRoom;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class ReserveRoomDao {
    protected static SessionFactory sessionFactory = HibernateUtil.sessionFactory;

    public List<ReserveRoom> fetch() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            List rooms = session.createQuery(
                    "from ReserveRoom"
            ).list();
            transaction.commit();
            System.out.println("COMPLETED...");
            return rooms;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return null;
    }

    /*public static void main(String[] args) {
        System.out.println(fetch().get(0).getFullName());
        getMaxReserveCode();
        getMaxRoomNumber();
    }*/

    public void save(ReserveRoom reserveRoom) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(reserveRoom);
            transaction.commit();
            System.out.println("COMPLETED...");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void update(ReserveRoom reserveRoom) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(reserveRoom);
            transaction.commit();
            System.out.println("COMPLETED...");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void delete(ReserveRoom reserveRoom) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(reserveRoom);
            transaction.commit();
            System.out.println("COMPLETED...");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public static int getMaxReserveCode() {
        Session session = null;
        Transaction transaction = null;
        int maxReserveCode = 10000;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            maxReserveCode = (int) session.createQuery(
                    "select max(r.reserveCode)" +
                            " from ReserveRoom r"
            ).getSingleResult();
            transaction.commit();
            if (maxReserveCode >= 10000) {
                return maxReserveCode;
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return maxReserveCode;
    }

    public static int getMaxRoomNumber() {
        Session session = null;
        Transaction transaction = null;
        int maxRoomNumber = 1;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            maxRoomNumber = (int) session.createQuery(
                    "select max(r.roomNumber)" +
                            " from ReserveRoom r"
            ).getSingleResult();
            transaction.commit();
            if (maxRoomNumber > 0) {
                return maxRoomNumber;
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return maxRoomNumber;
    }
}
