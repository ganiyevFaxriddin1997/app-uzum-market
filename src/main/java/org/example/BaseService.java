package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;


public interface BaseService<T>{

    default Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/uzum_db2";
        String username = "postgres";
        String password = "123";
        return DriverManager.getConnection(url, username, password);
    }

    int create (T t) throws SQLException;

    boolean delete(int id) throws SQLException;

    T get(int id) throws SQLException;

    List<T> getAll() throws SQLException;

    boolean update(int id, T t) throws SQLException;
}
