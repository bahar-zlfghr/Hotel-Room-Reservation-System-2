package com.maktab.models;

import com.maktab.entities.ReserveRoom;
import com.maktab.entities.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class HibernateUtil {
    public static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(ReserveRoom.class);
            configuration.addAnnotatedClass(User.class);
            configuration.setProperties(new Properties() {
                {
                    put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
                    put("hibernate.connection.url", "jdbc:mysql://localhost:3306/reserve_room");
                    put("hibernate.connection.username", "root");
                    put("hibernate.connection.password", "79682435@Bahar");
                    put("hibernate.hbm2ddl.auto", "update");
                    put("hibernate.show_sql", "true");
                }
            });

            return configuration.buildSessionFactory(new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
