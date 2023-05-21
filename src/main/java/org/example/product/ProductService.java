package org.example.product;

import org.example.dto.ResponseOfBasket;
import org.example.entities.Product;
import org.example.service.BaseService;

import java.sql.SQLException;
import java.util.List;

public interface ProductService extends BaseService<Product> {
    boolean addToBasket(int userId, int orderId) throws SQLException;
    ResponseOfBasket get(int userId,String name) throws SQLException;
    Product get(String name) throws SQLException;
    List<ResponseOfBasket> getProductsFromBasket(int userId) throws SQLException;
    double getSumOfPriceFromBasket(int userId) throws SQLException;
    Product getProductWithName(String name) throws SQLException;
    List<Integer> getOrderIdFromBasket(int userId) throws SQLException;
}
