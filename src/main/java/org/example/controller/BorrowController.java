package org.example.controller;

import org.example.common.Validate;
import org.example.model.BorrowedBook;
import org.example.service.BorrowService;
import org.example.service.impl.BorrowServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class BorrowController {
    private final Scanner scanner = new Scanner(System.in);
    private final BorrowService borrowService;

    public BorrowController(int choice) {
        borrowService = new BorrowServiceImpl(choice);
    }

    public void borrowBook() {
        System.out.println("->Add new borrow");
        System.out.println(borrowService.borrowBook(inputBorrowBookFromKeyboard()));
    }

    private BorrowedBook inputBorrowBookFromKeyboard() {
        System.out.println("Enter User Id");
        Long userId = inputId();
        System.out.println("Enter Book Id");
        Long bookId = inputId();
        LocalDate borrowDate = inputBorrowDate();
        Double depositAmount = inputDepositAmount();

        return BorrowedBook.builder()
                .userId(userId)
                .bookId(bookId)
                .borrowDate(borrowDate)
                .depositAmount(depositAmount)
                .build();
    }

    private Double inputDepositAmount() {
        Double depositAmount = null;
        while (depositAmount == null) {
            System.out.print("Enter Deposit Amount: ");
            try {
                depositAmount = scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
            scanner.nextLine();
        }
        return depositAmount;
    }

    private LocalDate inputBorrowDate() {
        LocalDate borrowDate = null;
        do {
            System.out.print("Enter date (YYYY-MM-DD): ");
            try {
                borrowDate = LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter in YYYY-MM-DD format.");
            }
        } while (!Validate.isValidPublishedDate(borrowDate));
        return borrowDate;
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

    public void returnBook() {
        Long id = inputId();
        if (borrowService.returnBook(id)) {
            System.out.println("Trả thành công");
        } else System.out.println("Trả thất bại");
    }

    public void getAllBorrowedBooks() {
        List<BorrowedBook> borrowedBooks = borrowService.getAllBorrowedBooks();
        if (borrowedBooks.isEmpty()) {
            System.out.println("Không có phiếu mượn sách nào");
        } else borrowedBooks.forEach(System.out::println);
    }

    public void getBorrowedBooksByUser() {
        List<BorrowedBook> borrowedBooks = borrowService.getBorrowedBooksByUser(inputId());
        if (borrowedBooks.isEmpty()) {
            System.out.println("Không có phiếu mượn sách nào của người này");
        } else borrowedBooks.forEach(System.out::println);
    }
}
