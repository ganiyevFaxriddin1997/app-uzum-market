package org.example.service.productService;

import org.example.model.Product;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProductServiceImp implements ProductService{

@Override
public int create(Product product) throws SQLException {
	return 0;
}

@Override
public boolean update(Product product) throws SQLException {
	return false;
}

@Override
public boolean delete(Product product) throws SQLException {
	return false;
}

@Override
public Product get(int id) throws SQLException {
	return null;
}

@Override
public List<Product> getAll() throws SQLException {
	return null;
}
}
