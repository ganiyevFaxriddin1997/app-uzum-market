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
	PreparedStatement preparedStatement = connection.prepareStatement("select table from users where phone_number = ?");
	preparedStatement.setString(1,phoneNumber);
	ResultSet resultSet = preparedStatement.executeQuery();
	User user = null;
	if (resultSet.next()) {
		user = User.builder()
				       .phoneNumber("phone_number")
				       .build();
	}
	resultSet.close();
	preparedStatement.close();
	connection.close();
	return user;
}

@Override
public int create(User user) throws SQLException {
	Connection connection = getConnection();
	PreparedStatement preparedStatement = connection.prepareStatement("select from add_user(?,?,?,?,?,?,?)");
	preparedStatement.setString(4, user.getPhoneNumber());
	preparedStatement.setString(5, user.getGender().name());
	ResultSet resultSet = preparedStatement.executeQuery();
	int result = -1;
	if (resultSet.next())
		result = resultSet.getInt(1);
		resultSet.close();
		connection.close();
		preparedStatement.close();
	return result;
}

@Override
public boolean update(User user) throws SQLException {
	Connection connection = getConnection();
	PreparedStatement preparedStatement = connection.prepareStatement(
			"update users set first_name = ?, last_name = ?, middle_name = ?, email = ?," +
					" gender = ?, phone_number = ?, birthday = ?, where id = ?");
	preparedStatement.setString(5, user.getGender().name());
	preparedStatement.setString(6, user.getPhoneNumber());
	int i = preparedStatement.executeUpdate();
	preparedStatement.close();
	connection.close();
	return i > 0;
}

@Override
public boolean delete(User user) throws SQLException {
	Connection connection = getConnection();
	PreparedStatement preparedStatement =
			connection.prepareStatement("delete users where id = ?");
	preparedStatement.setInt(1,user.getId());
	int i = preparedStatement.executeUpdate();
	preparedStatement.close();
	connection.close();
	return i > 0;
}

@Override
public User get(int id) throws SQLException {
	Connection connection = getConnection();
	PreparedStatement preparedStatement = connection.prepareStatement("select * from users where id = ?");
	preparedStatement.setInt(1, id);
	ResultSet resultSet = preparedStatement.executeQuery();
	User user = null;
	if (resultSet.next()) {
		user = User.builder()
				       .phoneNumber(resultSet.getString("phone_number"))
				       .build();
	}
	resultSet.close();
	preparedStatement.close();
	connection.close();
	return user;
}

@Override
public List<User> getAll() throws SQLException {
	Connection connection = getConnection();
	PreparedStatement preparedStatement = connection.prepareStatement("select * from users");
	ResultSet resultSet = preparedStatement.executeQuery();
	List<User> users = null;
	while (resultSet.next()) {
		User user = User.builder()
				        .phoneNumber(resultSet.getString("phone_number"))
				        .build();
		users.add(user);
	}
	resultSet.close();
	preparedStatement.close();
	connection.close();
	return users;
}
}
