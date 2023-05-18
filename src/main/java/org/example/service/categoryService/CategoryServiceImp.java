package org.example.service.categoryService;

import org.example.model.Category;

import java.sql.SQLException;
import java.util.List;

public class CategoryServiceImp implements CategoryService{
@Override
public int create(Category category) throws SQLException {
	
	return 0;
}

@Override
public boolean update(Category category) throws SQLException {
	return false;
}

@Override
public boolean delete(Category category) throws SQLException {
	return false;
}

@Override
public Category get(int id) throws SQLException {
	return null;
}

@Override
public List<Category> getAll() throws SQLException {
	return null;
}
}
