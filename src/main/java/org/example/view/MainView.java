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

        if (choice < 1 || choice > 2) {
            System.out.println("Invalid choice. Please enter 1 for Database, 2 for File.");
            return getDataStorageChoice();
        }

        return choice;
    }

    public void run() {
        int dataStorageChoice = getDataStorageChoice();
        UserController userController = new UserController(dataStorageChoice);
        CategoryController categoryController = new CategoryController(dataStorageChoice);
        BookController bookController = new BookController(dataStorageChoice);
        BorrowController borrowController = new BorrowController(dataStorageChoice);

        while (true) {
            switch (getMenuManage()) {
                case 1:
                    manageUser(userController);
                    break;
                case 2:
                    manageCategory(categoryController);
                    break;
                case 3:
                    manageBook(bookController);
                    break;
                case 4:
                    manageBorrowBook(borrowController);
                    break;
                case 5:
                    System.out.println("Exit");
                    return;
            }
        }
    }

    private void manageBorrowBook(BorrowController borrowController) {
        int choice = 0;
        while (true) {
            choice = getBorrowMenu();
            try {
                switch (choice) {
                    case 1:
                        borrowController.borrowBook();
                        break;
                    case 2:
                        borrowController.returnBook();
                        break;
                    case 3:
                        borrowController.getBorrowedBooksByUser();
                        break;
                    case 4:
                        borrowController.getAllBorrowedBooks();
                        break;
                    case 5:
                        System.out.println("Back to home!");
                        return;
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
                    case 1:
                        bookController.addBook();
                        break;
                    case 2:
                        bookController.searchBookByTitle();
                        break;
                    case 3:
                        bookController.updateBookById();
                        break;
                    case 4:
                        bookController.deleteBookById();
                        break;
                    case 5:
                        bookController.showAllBooks();
                        break;
                    case 6:
                        System.out.println("Back to home!");
                        return;
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
                    case 1:
                        categoryController.addCategory();
                        break;
                    case 2:
                        categoryController.searchCategoryById();
                        break;
                    case 3:
                        categoryController.updateCategoryById();
                        break;
                    case 4:
                        categoryController.deleteCategoryById();
                        break;
                    case 5:
                        categoryController.showAllCategories();
                        break;
                    case 6:
                        System.out.println("Back to home!");
                        return;
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
                    case 1:
                        userController.addUser();
                        break;
                    case 2:
                        userController.searchUserByName();
                        break;
                    case 3:
                        userController.updateUser();
                        break;
                    case 4:
                        userController.deleteUser();
                        break;
                    case 5:
                        userController.showAllUser();
                        break;
                    case 6:
                        System.out.println("Back to home!");
                        return;
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
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }
}
