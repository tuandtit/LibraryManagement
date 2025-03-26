package org.example.controller;

import org.example.common.Validate;
import org.example.model.Book;
import org.example.service.BookService;
import org.example.service.impl.BookServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class BookController {
    private final Scanner scanner = new Scanner(System.in);
    private final BookService bookService;

    public BookController(int choice) {
        bookService = new BookServiceImpl(choice);
    }

    public void addBook() {
        System.out.println("->Add new book");
        System.out.println(bookService.addBook(inputBookFromKeyboard()));
    }

    private Book inputBookFromKeyboard() {
        String title = inputTitle();
        String author = inputAuthor();
        LocalDate publishedDate = inputPublishedDate();
        int availableCopies = inputAvailableCopies();
        Long categoryId = inputId();
        return Book.builder()
                .title(title)
                .author(author)
                .publishedDate(publishedDate)
                .availableCopies(availableCopies)
                .categoryId(categoryId)
                .build();
    }

    private Long inputId() {
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
        return id;
    }

    private int inputAvailableCopies() {
        Integer availableCopies = null;
        while (availableCopies == null) {
            System.out.print("Enter Available Copies: ");
            try {
                availableCopies = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
            scanner.nextLine();
        }
        return availableCopies;
    }

    private LocalDate inputPublishedDate() {
        LocalDate publishedDate = null;
        do {
            System.out.print("Enter date (YYYY-MM-DD): ");
            try {
                publishedDate = LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter in YYYY-MM-DD format.");
            }
        } while (!Validate.isValidPublishedDate(publishedDate));
        return publishedDate;
    }

    private String inputAuthor() {
        String author;
        do {
            System.out.print("Enter author: ");
            author = scanner.nextLine();
        }
        while (Validate.isValidName(author));
        return author;
    }

    private String inputTitle() {
        String title;
        do {
            System.out.print("Enter title: ");
            title = scanner.nextLine();
        }
        while (Validate.isValidTitle(title));
        return title;
    }

    public void searchBookByTitle() {
        String title = inputTitle();
        List<Book> books = bookService.findBookByTitle(title);
        if (books.isEmpty()) {
            System.out.println("Không có sách nào");
        }
        books.forEach(System.out::println);
    }

    public void updateBookById() {
        System.out.println("->Update Book");
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
        Book updateBook = inputUpdateBook(id);
        if (bookService.updateBook(id, updateBook)) {
            System.out.println("update book successfully");
        } else System.out.println("update book fail");
    }

    private Book inputUpdateBook(Long id) {
        Book updateBook = bookService.findBookById(id);
        while (true) {
            int choice = getMenuUpdated();
            switch (choice) {
                case 1:
                    String title = inputTitle();
                    updateBook.setTitle(title);
                    break;
                case 2:
                    String author = inputAuthor();
                    updateBook.setAuthor(author);
                    break;
                case 3:
                    LocalDate publishedDate = inputPublishedDate();
                    updateBook.setPublishedDate(publishedDate);
                    break;
                case 4:
                    int i = inputAvailableCopies();
                    updateBook.setAvailableCopies(i);
                    break;
                case 5:
                    Long categoryId = inputId();
                    updateBook.setCategoryId(categoryId);
                    break;
                case 6:
                    return updateBook;
            }
        }

    }

    private int getMenuUpdated() {
        printMenuUpdated();

        int choice = 0;
        try {
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.print("Invalid format, please choose menu again.");
            return getMenuUpdated();
        }

        if (choice < 1 || choice > 6) {
            System.out.println("Invalid choice. Please enter between 1 and 4");
            return getMenuUpdated();
        }
        System.out.println();
        return choice;
    }

    private void printMenuUpdated() {
        System.out.println("\n->Choose the field that you want to update");
        System.out.println("1. Title");
        System.out.println("2. Author");
        System.out.println("3. Publish Date");
        System.out.println("4. Available Copies");
        System.out.println("5. Category");
        System.out.println("6. Done");
        System.out.print("Enter your choice:");
    }

    public void deleteBookById() {
        System.out.println("->Delete Book By ID");
        Long id = inputId();
        if (bookService.deleteBookById(id)) {
            System.out.println("Xóa thành công");
        } else System.out.println("Xóa thất bại");
    }

    public void showAllBooks() {
        List<Book> books = bookService.showAllBooks();
        if (books.isEmpty())
            System.out.println("Không có sách nào");
        else {

            System.out.println("List book: ");
            books.forEach(System.out::println);
        }
    }
}
