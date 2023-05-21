package org.example.subCategory;

import org.example.category.CategoryService;
import org.example.category.CategoryServiceImp;
import org.example.entities.Category;
import org.example.entities.SubCategory;

import java.beans.PropertyEditorSupport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubCategoryServiceImp implements SubCategoryService {
    @Override
    public int create(SubCategory subCategory) throws SQLException {
        var connection = getConnection();
        String query = "select add_sub_category(?, ?)";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, subCategory.getName());
        preparedStatement.setInt(2, subCategory.getCategoryId());
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
    public boolean delete(int id) throws SQLException {
        var connection = getConnection();
        String query = "delete from sub_category where id = ?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        int result = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return result > 0;

    }

    @Override
    public SubCategory get(int id) throws SQLException {
        var connection = getConnection();
        String query = "select * from sub_category where id = ?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        var resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            var subCategory = SubCategory.builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .categoryId(resultSet.getInt("category_id"))
                    .build();
            return subCategory;
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return null;

    }

    @Override
    public List<SubCategory> getAll() throws SQLException {
        var connection = getConnection();
        String query = "select * from sub_category";
        var preparedStatement = connection.prepareStatement(query);
        var resultSet = preparedStatement.executeQuery();
        List<SubCategory> subCategories = new ArrayList<>();
        while (resultSet.next()) {
            var subCategory = SubCategory.builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .categoryId(resultSet.getInt("category_id"))
                    .build();
            subCategories.add(subCategory);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return subCategories;
    }

    @Override
    public boolean update(int id, SubCategory subCategory) throws SQLException {
        var connection = getConnection();
        String query = "update sub_category set name = ?, category_id = ? where id = ?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, subCategory.getName());
        preparedStatement.setInt(2, subCategory.getCategoryId());
        preparedStatement.setInt(3, id);
        int result = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return result > 0;
    }

    @Override
    public SubCategory get(String name) throws SQLException {
        var connection = getConnection();
        String query = "select * from sub_category where name = ?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,name);
        var resultSet = preparedStatement.executeQuery();
        SubCategory subCategory = null;
        if (resultSet.next()){
            subCategory = SubCategory.builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .categoryId(resultSet.getInt("category_id"))
                    .build();
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return subCategory;
    }

    CategoryService categoryService = new CategoryServiceImp();

    @Override
    public List<SubCategory> subCategoriesByCategoryName(String categoryName) throws SQLException {
        Category category = categoryService.get(categoryName);
        if (category == null) return null;
        Connection connection = getConnection();
        String query = "select * from sub_category where category_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,category.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        List<SubCategory> subCategories = new ArrayList<>();
        while (resultSet.next()){
            SubCategory subCategory = SubCategory.builder()
                    .name(resultSet.getString("name"))
                    .categoryId(category.getId())
                    .id(resultSet.getInt("id"))
                    .build();
            subCategories.add(subCategory);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return subCategories;
    }
}
