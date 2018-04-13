package com.amazing_gift.sql.sample;

import java.util.List;

public interface UserDao {

	public List<User> select(int offset, int count);

	public int create(User user);

	public int create(List<User> users);

}
