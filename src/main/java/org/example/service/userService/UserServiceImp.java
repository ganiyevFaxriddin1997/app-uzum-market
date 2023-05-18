package org.example.service.userService;

import org.example.enums.Gender;
import org.example.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImp implements UserService{
@Override
public User get(String phoneNumber) throws SQLException {
	Connection connection = getConnection();
	PreparedStatement preparedStatement = connection.prepareStatement("");
	ResultSet resultSet = preparedStatement.executeQuery();

	if (resultSet.next()) {

	}
	return null;
}

@Override
public int create(User user) throws SQLException {

	return 0;
}

@Override
public boolean update(User user) throws SQLException {
	return false;
}

@Override
public boolean delete(User user) throws SQLException {
	return false;
}

@Override
public User get(int id) throws SQLException {
	return null;
}

@Override
public List<User> getAll() throws SQLException {
	return null;
}
}
