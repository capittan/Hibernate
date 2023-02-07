package utils;

import models.Question;
import models.QuestionItem;
import models.Role;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryUtil {
    private static SessionFactory sessionFactory;

    public HibernateSessionFactoryUtil() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Role.class);
                configuration.addAnnotatedClass(Question.class);
                configuration.addAnnotatedClass(QuestionItem.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory();
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
        return sessionFactory;
    }
}
