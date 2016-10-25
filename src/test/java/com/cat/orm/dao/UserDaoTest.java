package com.cat.orm.dao;

import com.cat.orm.entity.Role;
import com.cat.orm.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class UserDaoTest {

    private Session session;
    private Transaction transaction;

    @Before
    public void before() {
        session = SessionUtil.session();
        transaction = session.beginTransaction();
    }

    @After
    public void after() {
        transaction.commit();
        SessionUtil.close(session);
    }

    @Test
    public void save() throws Exception {
        User user = new User();
        user.setName("cjj");
//        user.setBirthday(LocalDate.of(1984, 3, 21));
        user.setBirthday(new Date());
        user.setPhone("123456");

        Set<Role> roles = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            Role role = new Role().setName("role" + i).setUser(user);
            roles.add(role);
        }
        user.setRoles(roles);
        session.save(user);
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
        User user = (User) session.get(User.class, 1L);
        System.out.println(user);
    }

}