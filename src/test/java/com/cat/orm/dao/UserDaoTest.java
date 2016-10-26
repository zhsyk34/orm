package com.cat.orm.dao;

import com.cat.orm.entity.Role;
import com.cat.orm.entity.User;
import com.cat.orm.entity.enums.Gender;
import com.cat.orm.kit.SessionKit;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDaoTest {

	private Session session;
	private Transaction transaction;

	@Before
	public void before() {
		session = SessionKit.current();
		transaction = session.beginTransaction();
	}

	@After
	public void after() {
		transaction.commit();
		if (session.isConnected()) {
			SessionKit.close(session);
		}
	}

	@Test
	public void init() {

	}

	@Test
	public void save() throws Exception {
		User user = new User();
		user.setName("cjj" + Math.random());
//		user.setBirthday(LocalDate.of(1984, 3, 21));
		user.setBirthday(new Date());
		user.setPhone("123456").setGender(Gender.MALE);

		Set<Role> roles = new HashSet<>();
		for (int i = 0; i < 3; i++) {
			Role role = new Role().setName("role" + i).setUser(user);
			roles.add(role);
		}
		user.setRoles(roles);
		session.persist(user);
		System.out.println(user.getId());
	}

	@Test
	public void update() throws Exception {

	}

	@Test
	public void delete() throws Exception {
		User user = (User) session.get(User.class, 4L);
		session.delete(user);
	}

	@Test
	public void find() throws Exception {
		User user = (User) session.get(User.class, 2L);
		System.out.println(user);
		System.out.println(user.getRoles());
	}

	@Test
	public void find2() throws Exception {
		Criteria criteria = session.createCriteria(User.class);
		List<User> list = criteria.list();
		list.forEach(System.out::println);
	}

	@Test
	public void find3() throws Exception {
		Criteria criteria = session.createCriteria(User.class);
//		criteria.add(Property.forName("id").eq(3L));
		criteria.setProjection(Projections.avg("id"));
//		criteria.add(Restrictions.eq("id", 4L));
		criteria.add(Restrictions.idEq(5L));
		List list = criteria.list();
		System.out.println(list.size());
		list.forEach(System.out::println);
	}

	@Test
	public void find4() throws Exception {
		Criteria criteria = session.createCriteria(User.class);
		criteria.setComment("search count");
		criteria.setProjection(Projections.count("id"));

		Object o = criteria.uniqueResult();
		System.out.println(o);
	}

	@Test
	public void find5() throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forEntityName(User.class.getName());
		criteria.setProjection(Projections.count("id"));
		System.out.println(criteria.getExecutableCriteria(session).uniqueResult());
	}

	@Test
	public void find6() throws Exception {
		Example example = Example.create(new User().setName("cjj")).ignoreCase().enableLike(MatchMode.ANYWHERE);
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(example);
		System.out.println(criteria.list());
	}

	@Test
	public void find8() throws Exception {
		DetachedCriteria detach = of(User.class);
		detach.addOrder(Order.desc("name"));
		detach.add(Restrictions.like("name", "c", MatchMode.ANYWHERE));
		Criteria criteria = of(session, detach);

		List<User> list = criteria.list();
		list.forEach(System.out::println);
	}

	@Test
	public void find9() throws Exception {
		DetachedCriteria detach = of(User.class);
		detach.add(Restrictions.like("name", "j", MatchMode.ANYWHERE));
		detach.setProjection(Projections.count("id"));
		Criteria criteria = of(session, detach);

		Object o = criteria.uniqueResult();
		System.out.println(o);

		detach.setProjection(null);
		System.out.println(criteria.list());
	}

	@Test
	public void find99() throws Exception {
//		Criteria criteria = session.createCriteria(User.class);
//		criteria.setProjection(Projections.avg(""));
//		criteria.addOrder();
//		criteria.add();
//		criteria.
//				criteria.
//				criteria.
//				criteria.
//				Restrictions.eq();
//		Projection pj;
//		Project project;
	}

	@Test
	public void find10() {
		Query query = session.getNamedQuery(User.class.getName() + ".findUser");
		//query.setResultTransformer(Transformers.aliasToBean(User.class));
		List list = query.list();
		System.out.println(list);

	}

	private static Criteria of(Session session, DetachedCriteria detachedCriteria) {
		return detachedCriteria.getExecutableCriteria(session);
	}

	private static DetachedCriteria of(Class clazz) {
		return DetachedCriteria.forClass(clazz);
	}

}