package org.example.service.subCategoryService;

import org.example.model.SubCategory;

import java.sql.SQLException;
import java.util.List;

public class SubCategoryServiceImp implements SubCategoryService{
@Override
public int create(SubCategory subCategory) throws SQLException {
	return 0;
}

@Override
public boolean update(SubCategory subCategory) throws SQLException {
	return false;
}

@Override
public boolean delete(SubCategory subCategory) throws SQLException {
	return false;
}

@Override
public SubCategory get(int id) throws SQLException {
	return null;
}

@Override
public List<SubCategory> getAll() throws SQLException {
	return null;
}
}
