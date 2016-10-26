package com.cat.orm.kit;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class CommonDao<E, K extends Serializable> {

	private final Class<E> clazz;

	public CommonDao() {
		Type type = this.getClass().getGenericSuperclass();
		if (type != null && type instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) type;
			clazz = (Class<E>) parameterizedType.getActualTypeArguments()[0];
		} else {
			throw new RuntimeException();
		}
	}

	protected final Session current() {
		return SessionKit.current();
	}

	protected final Session oepn() {
		return SessionKit.open();
	}

	public void save(E e) {
		Session session = this.current();
		Transaction transaction = session.beginTransaction();
		session.persist(e);
		transaction.commit();
	}

	public void delete(K k) {
		E e = this.find(k);
		Session session = this.current();
		Transaction transaction = session.beginTransaction();
		session.delete(e);
		transaction.commit();
	}

	public void update(E e) {
		Session session = this.current();
		Transaction transaction = session.beginTransaction();
		session.update(e);
		transaction.commit();
	}

	public void merge(E e) {
		Session session = this.current();
		Transaction transaction = session.beginTransaction();
		session.saveOrUpdate(e);
		transaction.commit();
	}

	public E find(K k) {
		/*Session session = oepn();
		return (E) session.get(clazz, k);*/
		Session session = this.current();
		Transaction transaction = session.beginTransaction();
		E e = (E) session.get(clazz, k);
		transaction.commit();
		return e;
	}

	public List<E> findList() {
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		return criteria.getExecutableCriteria(current()).list();
	}

}
