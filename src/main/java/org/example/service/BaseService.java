package org.example.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public interface BaseService<T> {


default Connection getConnection() throws SQLException{
	String url = "jdbc:postgresql://localhost:5432/uzum_market";
	String userName = "postgres";
	String password = "329743346";
	return DriverManager.getConnection(url, userName, password);
}

default Connection getConnections() throws SQLException{
	String url = "";
	String userName = "";
	String password = "" ;
	return DriverManager.getConnection(url, userName, password);
}

int create(T t) throws SQLException;
	boolean update(T t) throws SQLException;
	boolean delete(T t) throws SQLException;
	T get(int id) throws SQLException;
	List<T> getAll() throws SQLException;
	
	

}
