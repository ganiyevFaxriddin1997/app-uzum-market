package org.example.service.category;

import org.example.entities.Category;
import org.example.service.BaseService;

import java.sql.SQLException;

public interface CategoryService extends BaseService<Category> {
    Category get (String name) throws SQLException;
}
