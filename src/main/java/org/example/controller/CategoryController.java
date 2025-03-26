package org.example.controller;

import org.example.common.Validate;
import org.example.model.Category;
import org.example.service.CategoryService;
import org.example.service.impl.CategoryServiceImpl;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CategoryController {
    private final Scanner scanner = new Scanner(System.in);
    private final CategoryService categoryService;

    public CategoryController(int choice) {
        categoryService = new CategoryServiceImpl(choice);
    }

    public void addCategory() {
        System.out.println("->Add new category");
        System.out.println(categoryService.addCategory(inputCategoryFromKeyboard()));
    }

    private Category inputCategoryFromKeyboard() {
        String name = inputName();
        return Category.builder()
                .name(name)
                .build();
    }

    private String inputName() {
        String name;
        do {
            System.out.print("Enter name: ");
            name = scanner.nextLine();
        }
        while (Validate.isValidName(name));
        scanner.nextLine();
        return name;
    }

    public void updateCategoryById() {
        System.out.println("->Update Category");
        Long id = null;
        while (id == null) {
            try {
                System.out.print("Enter ID: ");
                id = scanner.nextLong();
            } catch (InputMismatchException e) {
                System.out.println("Invalid format, please enter again.");
            }
            scanner.nextLine();
        }
        if (categoryService.findCategoryById(id) == null) {
            System.out.println("No category found to update with ID: " + id);
            return;
        }
        String newName = inputName();
        if (categoryService.updateCategory(id, newName)) {
            System.out.println("update category successfully");
        } else System.out.println("update category fail");
    }

    public void searchCategoryById() {
        System.out.println("->Search category by id");
        Long id = null;
        id = inputId(id);

        Category category = categoryService.findCategoryById(id);
        if (category != null) {
            System.out.println("Category found: ");
            System.out.println(category);
        } else System.out.println("No found category with id: " + id);
    }


    public void deleteCategoryById() {
        System.out.println("->Delete Category By ID");
        Long id = null;
        id = inputId(id);
        if (categoryService.deleteCategory(id)) {
            System.out.println("Xóa thành công");
        } else System.out.println("Xóa thất bại");
    }

    private Long inputId(Long id) {
        while (id == null) {
            try {
                System.out.print("Enter ID: ");
                id = scanner.nextLong();
            } catch (InputMismatchException e) {
                System.out.println("Invalid format, please enter again.");
            }
        }
        return id;
    }

    public void showAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        if (categories.isEmpty()) {
            System.out.println("Không có category nào");
        }
        categories.forEach(System.out::println);
    }
}
