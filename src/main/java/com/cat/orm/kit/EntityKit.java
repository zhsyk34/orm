package com.cat.orm.kit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityKit {

	private static final EntityManagerFactory FACTORY;

	static {
		try {
			FACTORY = Persistence.createEntityManagerFactory("example");
		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static EntityManagerFactory factory() {
		return FACTORY;
	}

	public static void close() {
		factory().close();
	}

	public static EntityManager manager() {
		return factory().createEntityManager();
	}
}