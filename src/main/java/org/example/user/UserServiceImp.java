package org.example.user;


import org.example.entities.User;
import org.example.enums.Gender;
import org.example.enums.Role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImp implements UserService {
    @Override
    public int create(User user) throws SQLException {
        var connection = getConnection();
        String query = "select add_user(?, ?, ?, ?)";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getPhoneNumber());
        preparedStatement.setString(3, user.getRole().name());
        preparedStatement.setString(4, user.getGender().name());
        ResultSet resultSet = preparedStatement.executeQuery();
        var result = -1;
        if (resultSet.next())
            result = resultSet.getInt(1);
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return result;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        var connection = getConnection();
        String query = "delete from users where id = ?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        int result = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return result > 0;
    }

    @Override
    public User get(int id) throws SQLException {
        var connection = getConnection();
        String query = "select * from users where id = ?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            var user = User.builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .phoneNumber(resultSet.getString("phone"))
                    .role(Role.getRole(resultSet.getString("role")))
                    .gender(Gender.getGender(resultSet.getString("gender")))
                    .build();
            return user;
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return null;
    }

    @Override
    public List<User> getAll() throws SQLException {
        List<User> userList = new ArrayList<>();
        var connection = getConnection();
        String query = "select * from users";
        var preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            var user = User.builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .phoneNumber(resultSet.getString("phone"))
                    .role(Role.getRole(resultSet.getString("role")))
                    .gender(Gender.getGender(resultSet.getString("gender")))
                    .build();
            userList.add(user);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return userList;
    }

    @Override
    public boolean update(int id, User user) throws SQLException {
        var connection = getConnection();
        String query = "update users set name = ?, phone = ?, role = ?, gender = ? where id = ?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getPhoneNumber());
        preparedStatement.setString(3, user.getRole().name());
        preparedStatement.setString(4, user.getGender().name());
        preparedStatement.setInt(5, id);
        int result = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return result > 0;
    }

    @Override
    public User getByPhone(String phone) throws SQLException {
        var connection = getConnection();
        String query = "select * from users where phone = ?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, phone);
        var resultSet = preparedStatement.executeQuery();
        User user = null;
        if (resultSet.next()) {
            user = User.builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .phoneNumber(resultSet.getString("phone"))
                    .role(Role.getRole(resultSet.getString("role")))
                    .gender(Gender.getGender(resultSet.getString("gender")))
                    .build();
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return user;
    }

    @Override
    public boolean changeRole(String role, int id) throws SQLException {
        var connection = getConnection();
        String query = "update users set role = ? where id = ?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,role);
        preparedStatement.setInt(2,id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return true;
    }

    @Override
    public User getByRole(String roleName) throws SQLException {
        var connection = getConnection();
        String query = "select * from users where role = ?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, roleName);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            var user = User.builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .phoneNumber(resultSet.getString("phone"))
                    .role(Role.getRole(resultSet.getString("role")))
                    .gender(Gender.getGender(resultSet.getString("gender")))
                    .build();
            return user;
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return null;
    }
}
