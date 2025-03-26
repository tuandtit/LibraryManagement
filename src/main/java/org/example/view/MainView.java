package org.example.view;

import org.example.controller.BookController;
import org.example.controller.BorrowController;
import org.example.controller.CategoryController;
import org.example.controller.UserController;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainView {
    private static void printUserMenu() {
        System.out.println("\nSelect an option:");
        System.out.println("1. Add new user");
        System.out.println("2. Search user by name");
        System.out.println("3. Update user by ID");
        System.out.println("4. Delete user by ID");
        System.out.println("5. Display user list");
        System.out.println("6. Back");
        System.out.print("Enter your choice: ");
    }

    public static int getUserMenu() {
        Scanner scanner = new Scanner(System.in);
        printUserMenu();

        int choice = 0;
        try {
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.print("Invalid format, please choose menu again.");
            return getUserMenu();
        }

        if (choice < 1 || choice > 6) {
            System.out.println("Invalid choice. Please enter between 1 and 6");
            return getUserMenu();
        }
        System.out.println();
        return choice;
    }

    private static void printDataStorageMenu() {
        System.out.println("Select the data storage you want to use:");
        System.out.println("1. Database");
        System.out.println("2. File");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }

    public static int getDataStorageChoice() {
        Scanner scanner = new Scanner(System.in);
        printDataStorageMenu();

        int choice = 0;
        try {
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.print("Invalid format, please choose data storage again.");
            return getDataStorageChoice();
        }

        if (choice < 1 || choice > 3) {
            System.out.println("Invalid choice. Please enter 1 for Database, 2 for File.");
            return getDataStorageChoice();
        }

        return choice;
    }

    public void run() {
        while (true) {
            switch (getDataStorageChoice()) {
                case 1 -> manageLibrary(1);
                case 2 -> manageLibrary(2);
                default -> {
                    System.out.println("Exit!");
                    return;
                }
            }
        }
    }

    private void manageLibrary(int choice) {
        UserController userController = new UserController(choice);
        CategoryController categoryController = new CategoryController(choice);
        BookController bookController = new BookController(choice);
        BorrowController borrowController = new BorrowController(choice);
        while (true) {
            switch (getMenuManage()) {
                case 1 -> manageUser(userController);
                case 2 -> manageCategory(categoryController);
                case 3 -> manageBook(bookController);
                case 4 -> manageBorrowBook(borrowController);
                default -> {
                    System.out.println("Back");
                    return;
                }
            }
        }
    }

    private void manageBorrowBook(BorrowController borrowController) {
        int choice = 0;
        while (true) {
            choice = getBorrowMenu();
            try {
                switch (choice) {
                    case 1 -> borrowController.borrowBook();
                    case 2 -> borrowController.returnBook();
                    case 3 -> borrowController.getBorrowedBooksByUser();
                    case 4 -> borrowController.getAllBorrowedBooks();
                    default -> {
                        System.out.println("Back to home!");
                        return;
                    }
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    private int getBorrowMenu() {
        Scanner scanner = new Scanner(System.in);
        printBorrowMenu();

        int choice = 0;
        try {
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.print("Invalid format, please choose menu again.");
            return getBorrowMenu();
        }

        if (choice < 1 || choice > 5) {
            System.out.println("Invalid choice. Please enter between 1 and 6");
            return getBorrowMenu();
        }
        System.out.println();
        return choice;
    }

    private void printBorrowMenu() {
        System.out.println("\nSelect an option:");
        System.out.println("1. Borrow Book");
        System.out.println("2. Return Book");
        System.out.println("3. Display borrow book by User");
        System.out.println("4. Display all borrow book list");
        System.out.println("5. Back");
        System.out.print("Enter your choice: ");
    }

    private void manageBook(BookController bookController) {
        int choice = 0;
        while (true) {
            choice = getBookMenu();
            try {
                switch (choice) {
                    case 1 -> bookController.addBook();
                    case 2 -> bookController.searchBookByTitle();
                    case 3 -> bookController.updateBookById();
                    case 4 -> bookController.deleteBookById();
                    case 5 -> bookController.showAllBooks();
                    default -> {
                        System.out.println("Back to home!");
                        return;
                    }
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    private int getBookMenu() {
        Scanner scanner = new Scanner(System.in);
        printBookMenu();

        int choice = 0;
        try {
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.print("Invalid format, please choose menu again.");
            return getBookMenu();
        }

        if (choice < 1 || choice > 6) {
            System.out.println("Invalid choice. Please enter between 1 and 6");
            return getBookMenu();
        }
        System.out.println();
        return choice;
    }

    private void printBookMenu() {
        System.out.println("\nSelect an option:");
        System.out.println("1. Add new book");
        System.out.println("2. Search book by title");
        System.out.println("3. Update book by ID");
        System.out.println("4. Delete book by ID");
        System.out.println("5. Display book list");
        System.out.println("6. Back");
        System.out.print("Enter your choice: ");
    }

    private void manageCategory(CategoryController categoryController) {

        int choice = 0;
        while (true) {
            choice = getCategoryMenu();
            try {
                switch (choice) {
                    case 1 -> categoryController.addCategory();
                    case 2 -> categoryController.searchCategoryById();
                    case 3 -> categoryController.updateCategoryById();
                    case 4 -> categoryController.deleteCategoryById();
                    case 5 -> categoryController.showAllCategories();
                    default -> {
                        System.out.println("Back to home!");
                        return;
                    }
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    private static void printCategoryMenu() {
        System.out.println("\nSelect an option:");
        System.out.println("1. Add new category");
        System.out.println("2. Search category by ID");
        System.out.println("3. Update category by ID");
        System.out.println("4. Delete category by ID");
        System.out.println("5. Display category list");
        System.out.println("6. Back");
        System.out.print("Enter your choice: ");
    }

    public static int getCategoryMenu() {
        Scanner scanner = new Scanner(System.in);
        printCategoryMenu();

        int choice = 0;
        try {
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.print("Invalid format, please choose menu again.");
            return getCategoryMenu();
        }

        if (choice < 1 || choice > 6) {
            System.out.println("Invalid choice. Please enter between 1 and 6");
            return getCategoryMenu();
        }
        System.out.println();
        return choice;
    }

    private void manageUser(UserController userController) {
        int choice = 0;
        while (true) {
            choice = getUserMenu();
            try {
                switch (choice) {
                    case 1 -> userController.addUser();
                    case 2 -> userController.searchUserByName();
                    case 3 -> userController.updateUser();
                    case 4 -> userController.deleteUser();
                    case 5 -> userController.showAllUser();
                    default -> {
                        System.out.println("Back to home!");
                        return;
                    }
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    private int getMenuManage() {
        Scanner scanner = new Scanner(System.in);
        printMenuManage();

        int choice = 0;
        try {
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.print("Invalid format, please choose menu again.");
            return getMenuManage();
        }

        if (choice < 1 || choice > 5) {
            System.out.println("Invalid choice. Please enter between 1 and 5");
            return getMenuManage();
        }
        System.out.println();
        return choice;
    }

    private void printMenuManage() {
        System.out.println("\nSelect an option:");
        System.out.println("1. Manage User");
        System.out.println("2. Manage Category");
        System.out.println("3. Manage Book");
        System.out.println("4. Manage Borrow Book");
        System.out.println("5. Back");
        System.out.print("Enter your choice: ");
    }
}
