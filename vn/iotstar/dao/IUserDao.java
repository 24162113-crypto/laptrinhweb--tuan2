package vn.iotstar.dao;

import vn.iotstar.entity.User;

public interface IUserDao {

	User findByUsername(String username);

	User findByEmail(String email);

	User findById(int id);

	void insert(User user);

	void update(User user);
}
