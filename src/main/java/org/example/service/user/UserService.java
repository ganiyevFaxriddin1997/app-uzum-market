package org.example.service.user;

import org.example.entities.User;
import org.example.service.BaseService;

import java.sql.SQLException;

public interface UserService extends BaseService<User> {
    User getByPhone (String phone) throws SQLException;
    boolean changeRole(String role, int id) throws SQLException;
    User getByRole(String roleName) throws SQLException;
}
