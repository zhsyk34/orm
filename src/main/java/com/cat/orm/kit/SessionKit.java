package com.cat.orm.kit;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class SessionKit {

	private static SessionFactory sessionFactory = init();
	private static final String DEFAULT_CONFIG = "hibernate.xml";

	//version 5?
	/*private static SessionFactory init() {
		StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().configure("/" + DEFAULT_CONFIG).build();

		Metadata metadata = new MetadataSources(standardRegistry).getMetadataBuilder().applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE).build();

		SessionFactoryBuilder sessionFactoryBuilder = metadata.getSessionFactoryBuilder();

		return sessionFactoryBuilder.build();
	}*/

	private static SessionFactory init() {
		try {
			if (sessionFactory == null) {
				Configuration configuration = new Configuration();
				configuration.configure(SessionKit.class.getResource("/" + DEFAULT_CONFIG));

				StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
				serviceRegistryBuilder.applySettings(configuration.getProperties());
				ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();

				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			}
			return sessionFactory;
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static Session current() {
		return sessionFactory.getCurrentSession();
	}

	public static Session open() {
		return sessionFactory.openSession();
	}

	public static void close(Session session) {
		session.close();
	}
}
