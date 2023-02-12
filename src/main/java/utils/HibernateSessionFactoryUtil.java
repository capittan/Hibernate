package utils;

import models.*;
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
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(UserRole.class);
                configuration.addAnnotatedClass(Category.class);
                configuration.addAnnotatedClass(Product.class);
                configuration.addAnnotatedClass(ProductImage.class);
                configuration.addAnnotatedClass(Basket.class);

                configuration.addAnnotatedClass(FilterNames.class);
                configuration.addAnnotatedClass(FilterValues.class);
                configuration.addAnnotatedClass(FilterNameGroups.class);
                configuration.addAnnotatedClass(Filters.class);

                configuration.addAnnotatedClass(OrderStatuses.class);
                configuration.addAnnotatedClass(Order.class);
                configuration.addAnnotatedClass(OrderItems.class);

                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory();
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
        return sessionFactory;
    }
}
