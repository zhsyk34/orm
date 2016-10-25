package com.cat.orm.dao;

import com.cat.orm.entity.User;

import java.util.List;

public interface UserDao {

    boolean save();

    boolean update();

    int delete();

    List<User> find();
}
