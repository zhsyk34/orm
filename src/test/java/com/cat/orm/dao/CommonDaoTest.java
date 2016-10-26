package com.cat.orm.dao;

import com.cat.orm.entity.Function;
import com.cat.orm.entity.Role;
import com.cat.orm.entity.User;
import com.cat.orm.entity.enums.Gender;
import org.hibernate.criterion.*;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class CommonDaoTest {

	private final UserDao userDao = new UserDao();

	@Test
	public void save() {
		User user = new User();
		user.setName("cjj" + Math.random());
//		user.setBirthday(LocalDate.of(1984, 3, 21));
		user.setBirthday(new Date());
		user.setPhone("123456").setGender(Gender.MALE);

		Set<Role> roles = new HashSet<>();
		for (int i = 0; i < 3; i++) {
			Role role = new Role().setName("role" + i).setUser(user);

			Set<Function> functions = new HashSet<>();
			for (int j = 0; j < 2; j++) {
				Function function = new Function().setName("fun" + j).setRole(role);
				functions.add(function);
			}
			role.setFunctions(functions);
			roles.add(role);
		}
		user.setRoles(roles);
		userDao.save(user);
		System.out.println(user.getId());
	}

	@Test
	public void find() {
		User user = userDao.find(1L);
		System.out.println(user);
		System.out.println("----------------");
		Set<Role> roles = user.getRoles();
		System.out.println(roles);
		System.out.println("----------------");
		roles.forEach(role -> System.out.println(role.getFunctions()));
	}

	@Test
	public void merge() {
		User user = new User().setBirthday(new Date()).setGender(Gender.FEMALE).setPhone("9999").setName("no" + Math.random());
		userDao.merge(user);
		System.out.println(user.getId());
	}

	@Test
	public void merge2() {
		User user = new User().setBirthday(new Date()).setGender(Gender.OTHER).setPhone("8888").setName("no" + Math.random());
		user.setId(4L);
		userDao.merge(user);
		System.out.println(user.getId());
	}

	@Test
	public void update() {
		User user = userDao.find(1L);
		user.setName("yes");
		System.out.println(user);
		userDao.update(user);
	}

	@Test
	public void delete() {
		userDao.delete(1L);
	}

	@Test
	public void find1() {
		Property property = Property.forName("");
		SimpleExpression eq = property.eq("");
		Projection avg = Projections.avg("");

		Example example;
	}
}