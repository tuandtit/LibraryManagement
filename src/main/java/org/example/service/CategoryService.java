package org.example.service;

import org.example.model.Category;

import java.util.List;

public interface CategoryService {
    Category addCategory(Category category);
    boolean updateCategory(Long id, String newName);
    Category findCategoryById(Long id);
    boolean deleteCategory(Long id);
    List<Category> getAllCategories();
}
