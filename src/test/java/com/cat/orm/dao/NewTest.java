package com.cat.orm.dao;

import com.cat.orm.entity.Role;
import com.cat.orm.entity.User;
import com.cat.orm.kit.SessionKit;
import com.google.gson.Gson;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class NewTest {

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
	public void find1() {
		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
		Root<User> root = criteriaQuery.from(User.class);

		Path<Long> id = root.get("id");
		Path<String> name = root.get("name");

		criteriaQuery.select(builder.array(id, name));

		List<Object[]> list = session.createQuery(criteriaQuery).getResultList();
		list.forEach((array) -> {
			for (int i = 0; i < array.length; i++) {
				System.out.println(array[i]);
			}
		});

	}

	@Test
	public void find2() {
		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<Tuple> criteria = builder.createQuery(Tuple.class);

		Root<User> userRoot = criteria.from(User.class);
		Root<Role> roleRoot = criteria.from(Role.class);

		criteria.multiselect(userRoot, roleRoot);

		Predicate userPredicate = builder.and(
				builder.like(userRoot.get("name"), "%c%")
				,
				builder.like(userRoot.get("phone"), "%1%")
		);

		Predicate roleRestriction = builder.lessThan(roleRoot.get("id"), 20);
		criteria.where(builder.and(userPredicate, roleRestriction));

		List<Tuple> list = session.createQuery(criteria).getResultList();
		list.forEach(tuple -> {
			System.out.print(tuple.get(0) + " ");
			System.out.print(tuple.get(1) + " ");
			System.out.println();
		});
	}

	/**
	 * CreateAlias 返回值还是当前的Criteria，但是CreateCriteria返回的新的Criteria
	 */
	@Test
	public void find3() {
//		Criteria criteria = session.createCriteria(User.class.getName());
		Criteria criteria = session.createCriteria(User.class, "user")
				.createAlias("user.roles", "role")
				.add(Restrictions.eq("id", 1L));
		//.add(Restrictions.like("name", "%c%"));

		List list = criteria.list();
		list.forEach(System.out::println);
	}

	@Test
	public void find4() {
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("user.id"));
		projectionList.add(Projections.property("user.name"));
		projectionList.add(Projections.property("role.id"));
		projectionList.add(Projections.property("role.name"));
		projectionList.add(Projections.property("function.id").as("roleId"));
		projectionList.add(Projections.property("function.name"));

		Criteria criteria = session.createCriteria(User.class, "user")
				.createCriteria("user.roles", "role", JoinType.INNER_JOIN)
				.createCriteria("role.functions", "function", JoinType.LEFT_OUTER_JOIN)
				.setProjection(projectionList);
				/*.setProjection(Projections.projectionList()
						.add(Projections.property("user.name"))
						.add(Projections.property("role.name"))
						.add(Projections.property("function.name"))
				);*/

		List<Object[]> list = criteria.list();
		list.forEach(array -> {
			for (Object anArray : array) {
				System.out.print(anArray + " ");
			}
			System.out.println();
		});
	}

	@Test
	public void find5() {
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("user.id"));
		projectionList.add(Projections.property("role.name"));
		projectionList.add(Projections.property("function.name").as("function"));

		Criteria criteria = session.createCriteria(User.class, "user")
				.createCriteria("user.roles", "role").add(Restrictions.like("name", "%2%"))
				.createCriteria("role.functions", "function").add(Restrictions.like("name", "%1%"))
				.setProjection(projectionList);

		List<Object[]> list = criteria.list();

		System.out.println(new Gson().toJson(list));
	}

	@Test
	public void find6() {
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("user.id").as("id"));
		projectionList.add(Projections.property("user.name").as("user"));
		projectionList.add(Projections.property("role.name").as("role"));
		//projectionList.add(Projections.property("function.name").as("function"));

		Criteria criteria = session.createCriteria(User.class, "user")
				.createAlias("user.roles", "role")
				.createAlias("user.roles.functions", "function")
				.add(Restrictions.like("user.name", "%2%"))
//				.createCriteria("role.functions", "function").add(Restrictions.like("name", "%1%"))
				.setProjection(projectionList);

//		Restrictions.disjunction()

		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map<String, ?>> list = criteria.list();

		System.out.println(new Gson().toJson(list));
	}

	@Test
	public void find99() {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		//User user = new User().setName("kkk").setPhone("1999").setGender(Gender.FEMALE).setBirthday(new Date());
		CriteriaUpdate<User> criteriaUpdate = builder.createCriteriaUpdate(User.class);

		Root<User> userRoot = criteriaUpdate.from(User.class);
		Path<String> name = userRoot.get("name");

		criteriaUpdate.set(name, "xyz" + new Random(100).nextInt());
		criteriaUpdate.where(builder.like(name, "%a%"));

		int i = session.createQuery(criteriaUpdate).executeUpdate();
		System.out.println(i);
	}
}
