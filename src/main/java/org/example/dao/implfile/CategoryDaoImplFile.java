package org.example.dao.implfile;

import org.example.dao.CategoryDao;
import org.example.model.Category;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImplFile implements CategoryDao {
    private static final String FILE_PATH = "src/main/java/data/categories.csv";
    private List<Category> categories;

    public CategoryDaoImplFile() {
        this.categories = loadCategoriesFromFile();
    }

    @Override
    public Category addCategory(Category category) {
        category.setId(generateNewId());
        categories.add(category);
        saveCategoriesToFile();
        return category;
    }

    @Override
    public boolean updateCategory(Long id, String newName) {
        for (Category category : categories) {
            if (category.getId().equals(id) && Boolean.TRUE.equals(category.getActive())) {
                category.setName(newName);
                saveCategoriesToFile();
                return true;
            }
        }
        return false;
    }

    @Override
    public Category findActiveCategoryById(Long id) {
        return categories.stream()
                .filter(category -> category.getId().equals(id) && category.getActive())
                .findFirst()
                .orElse(null);
    }

    @Override
    public Category findActiveCategoryByName(String name) {
        return categories.stream()
                .filter(category -> category.getName().equalsIgnoreCase(name) && category.getActive())
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean deleteCategory(Long id) {
        boolean removed = categories.removeIf(category -> category.getId().equals(id));
        if (removed) {
            saveCategoriesToFile();
        }
        return removed;
    }

    @Override
    public List<Category> getAllCategories() {
        return new ArrayList<>(categories);
    }

    private List<Category> loadCategoriesFromFile() {
        List<Category> categoryList = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return categoryList;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    Category category = new Category(Long.parseLong(data[0]), data[1], Boolean.parseBoolean(data[2]));
                    categoryList.add(category);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return categoryList;
    }

    private void saveCategoriesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Category category : categories) {
                writer.write(category.getId() + "," + category.getName() + "," + category.getActive());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Long generateNewId() {
        return categories.stream()
                .mapToLong(Category::getId)
                .max()
                .orElse(0) + 1;
    }
}
