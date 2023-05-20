package org.example.service.categoryService;

import org.example.model.Category;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryServiceImp implements CategoryService{
@Override
public int create(Category category) throws SQLException {
	var connection = getConnection();
	String query = "select add_category(?)";
	var preparedStatement = connection.prepareStatement(query);
	preparedStatement.setString(1, category.getName());
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
public boolean update(Category category) throws SQLException {
	var connection = getConnection();
	String query = "update category set name = ? where id = ?";
	var preparedStatement = connection.prepareStatement(query);
	preparedStatement.setString(1, category.getName());
	preparedStatement.setInt(2, category.getId());
	int result = preparedStatement.executeUpdate();
	preparedStatement.close();
	connection.close();
	return result>0;
}

@Override
public boolean delete(Category category) throws SQLException {
	var connection = getConnection();
	String query = "delete from category where id = ?";
	var preparedStatement = connection.prepareStatement(query);
	preparedStatement.setInt(1, category.getId());
	int result = preparedStatement.executeUpdate();
	preparedStatement.close();
	connection.close();
	return result > 0;
}

@Override
public Category get(int id) throws SQLException {
	var connection = getConnection();
	String query = "select * from category where id = ?";
	var preparedStatement = connection.prepareStatement(query);
	preparedStatement.setInt(1, id);
	var resultSet = preparedStatement.executeQuery();
	Category category = null;
	if (resultSet.next()) {
		category = Category.builder()
				.id(resultSet.getInt("id"))
				.name(resultSet.getString("name"))
				.build();

	}
	resultSet.close();
	preparedStatement.close();
	connection.close();
	return category;
}

@Override
public List<Category> getAll() throws SQLException {
	var connection = getConnection();
	String query = "select * from category";
	var preparedStatement = connection.prepareStatement(query);
	var resultSet = preparedStatement.executeQuery();
	List<Category> categories = new ArrayList<>();
	while (resultSet.next()) {
		var category = Category.builder()
				.id(resultSet.getInt("id"))
				.name(resultSet.getString("name"))
				.build();
		categories.add(category);
	}
	resultSet.close();
	preparedStatement.close();
	connection.close();
	return categories;
}
}
