package org.example.dao;

import org.example.model.Category;

import java.util.List;

public interface CategoryDao {
    Category addCategory(Category category);
    boolean updateCategory(Long id, String newName);
    Category findActiveCategoryById(Long id);
    Category findActiveCategoryByName(String name);
    boolean deleteCategory(Long id);
    List<Category> getAllCategories();
}
