package org.example.service.impl;

import org.example.dao.CategoryDao;
import org.example.dao.impldb.CategoryDaoImpl;
import org.example.dao.implfile.CategoryDaoImplFile;
import org.example.model.Category;
import org.example.service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;

    public CategoryServiceImpl(int choice) {
        if (choice == 1) {
            categoryDao = new CategoryDaoImpl();
        } else {
            categoryDao = new CategoryDaoImplFile();
        }
    }

    @Override
    public Category addCategory(Category category) {
        if (category == null || category.getName() == null || category.getName().isEmpty()) {
            System.out.println("Tên danh mục sách không được để trống.");
            return null;
        }

        if (categoryDao.findActiveCategoryByName(category.getName()) != null) {
            System.out.println("Tên danh mục bị trùng");
            return null;
        }

        category.setActive(true);

        return categoryDao.addCategory(category);
    }

    @Override
    public boolean updateCategory(Long id, String newName) {
        existingCategory(id);

        if (newName == null || newName.isEmpty()) {
            System.out.println("Tên không được để trống");
            return false;
        }

        if (categoryDao.findActiveCategoryByName(newName) != null) {
            System.out.println("Tên bị trùng");
            return false;
        }

        return categoryDao.updateCategory(id, newName);
    }

    @Override
    public Category findCategoryById(Long id) {
        if (id == null) {
            System.out.println("Không được để trống id");
            return null;
        }
        return existingCategory(id);
    }

    @Override
    public boolean deleteCategory(Long id) {
        if (id == null) {
            System.out.println("Không được để trống id");
            return false;
        }
        return categoryDao.deleteCategory(id);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryDao.getAllCategories();
    }

    private Category existingCategory(Long categoryId) {
        if (categoryId == null) {
            System.out.println("Không được để trống categoryId");
            return null;
        }
        Category category = categoryDao.findActiveCategoryById(categoryId);
        if (category == null) {
            throw new IllegalArgumentException("Không tìm thấy category có id: " + categoryId);
        }
        return category;
    }
}
