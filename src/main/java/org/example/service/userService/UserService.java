package org.example.service.userService;

import org.example.model.User;
import org.example.service.BaseService;

import java.sql.SQLException;

public interface UserService extends BaseService<User> {
	User get(String phoneNumber) throws SQLException;
	
	
}
