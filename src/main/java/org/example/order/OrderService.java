package org.example.order;

import org.example.entities.Order;
import org.example.service.BaseService;

import java.sql.SQLException;

public interface OrderService extends BaseService<Order> {
 int createOrder(int userId, int productId, int amount) throws SQLException;
 boolean changeOrder(int userId, int productId, int amount) throws SQLException;
 int checkOrderFromBasket(int userId, String name) throws SQLException;
 boolean cleanBasket(int userId) throws SQLException;
 boolean deleteOrderFromBasket(int userId, String name) throws SQLException;

}
