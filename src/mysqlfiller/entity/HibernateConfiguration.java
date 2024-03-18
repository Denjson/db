package mysqlfiller.entity;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

public class HibernateConfiguration {
	private static SessionFactory sessionFactory;
	public static SessionFactory getSessionFactory() {
		try {
			Configuration configuration = new Configuration();
			Properties settings = new Properties();
			settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
			settings.put(Environment.URL, "jdbc:mysql://localhost:3306/den_db?useSSL=false&amp");
			settings.put(Environment.USER, "denuser");
			settings.put(Environment.PASS, "denuser");
			settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
			settings.put(Environment.SHOW_SQL, "true");
			settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
			configuration.setProperties(settings);
			configuration.addAnnotatedClass(Person.class);	// Entity class
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties()).build();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sessionFactory;
	}
}
