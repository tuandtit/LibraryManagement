package org.example.dao;

import org.example.model.BorrowedBook;

import java.util.List;

public interface BorrowedBookDao {
    BorrowedBook borrowBook(BorrowedBook newBorrowedBook);

    Boolean returnBook(Long borrowId);

    List<BorrowedBook> getAllBorrowedBooks();

    List<BorrowedBook> getBorrowedBooksByUserId(Long userId);

    BorrowedBook findById(Long borrowId);
}
