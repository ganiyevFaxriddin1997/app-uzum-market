package org.example.subCategory;

import org.example.entities.SubCategory;
import org.example.service.BaseService;

import java.sql.SQLException;
import java.util.List;

public interface SubCategoryService extends BaseService<SubCategory> {
    SubCategory get(String name) throws SQLException;
    List<SubCategory> subCategoriesByCategoryName(String categoryName) throws SQLException;
}
