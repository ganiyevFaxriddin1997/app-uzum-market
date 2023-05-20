package org.example.service.productService;

import org.example.model.Product;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProductServiceImp implements ProductService{

@Override
public int create(Product product) throws SQLException {
	var connection = getConnection();
	String query = "select add_product(?,?,?,?,?,?,?,?,?)";
	var preparedStatement = connection.prepareStatement(query);
	preparedStatement.setString(1, product.getName());
	preparedStatement.setDouble(2, product.getPrice());
	preparedStatement.setInt(3, product.getSubCategoryId());
	preparedStatement.setString(4, product.getDescription());
	preparedStatement.setString(5, product.getColor());
	preparedStatement.setString(6, product.getSize());
	preparedStatement.setInt(7, product.getOwnerId());
	preparedStatement.setInt(8, product.getAmount());
	preparedStatement.setString(9, product.getBrand());
	var resultSet = preparedStatement.executeQuery();
	var result = -1;
	if (resultSet.next()) {
		result = resultSet.getInt(1);
	}
	resultSet.close();
	preparedStatement.close();
	connection.close();
	return result;
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
