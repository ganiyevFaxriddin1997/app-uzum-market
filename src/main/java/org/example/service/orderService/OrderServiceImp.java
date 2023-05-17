package org.example.service.orderService;

import org.example.model.Order;

import java.sql.SQLException;
import java.util.List;

public class OrderServiceImp implements OrderService{

@Override
public int create(Order order) throws SQLException {
	return 0;
}

@Override
public boolean update(Order order) throws SQLException {
	return false;
}

@Override
public boolean delete(Order order) throws SQLException {
	return false;
}

@Override
public Order get(int id) throws SQLException {
	return null;
}

@Override
public List<Order> getAll() throws SQLException {
	return null;
}
}
