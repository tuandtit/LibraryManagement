package org.example.service;

import org.example.model.BorrowedBook;

import java.util.List;

public interface BorrowService {
    BorrowedBook borrowBook(BorrowedBook newBorrowedBook);

    boolean returnBook(Long borrowId);

    List<BorrowedBook> getAllBorrowedBooks();

    List<BorrowedBook> getBorrowedBooksByUser(Long userId);
}
