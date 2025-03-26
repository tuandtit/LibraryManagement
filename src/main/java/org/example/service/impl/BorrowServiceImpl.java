package org.example.service.impl;

import org.example.dao.BookDao;
import org.example.dao.BorrowedBookDao;
import org.example.dao.UserDao;
import org.example.dao.impldb.BookDaoImpl;
import org.example.dao.impldb.BorrowedBookDaoImpl;
import org.example.dao.impldb.UserDaoImpl;
import org.example.dao.implfile.BookDaoImplFile;
import org.example.dao.implfile.BorrowedBookDaoImplFile;
import org.example.dao.implfile.UserDaoImplFile;
import org.example.model.Book;
import org.example.model.BorrowedBook;
import org.example.model.User;
import org.example.service.BorrowService;

import java.time.LocalDate;
import java.util.List;

public class BorrowServiceImpl implements BorrowService {
    private final BorrowedBookDao borrowedBookDao;
    private final UserDao userDao;
    private final BookDao bookDao;

    public BorrowServiceImpl(int choice) {
        if (choice == 1) {
            borrowedBookDao = new BorrowedBookDaoImpl();
            userDao = new UserDaoImpl();
            bookDao = new BookDaoImpl();
        } else {
            borrowedBookDao = new BorrowedBookDaoImplFile();
            userDao = new UserDaoImplFile();
            bookDao = new BookDaoImplFile();
        }
    }

    @Override
    public BorrowedBook borrowBook(BorrowedBook newBorrowedBook) {
        User user = existingUser(newBorrowedBook.getUserId());
        Book book = existingBook(newBorrowedBook.getBookId());

        if (book.getAvailableCopies() < 1) {
            System.out.println("Hết sách, không mượn được");
            return null;
        }
        bookDao.updateBookCopies(book.getId(), book.getAvailableCopies() - 1);

        BorrowedBook borrowedBook = BorrowedBook.builder()
                .userId(user.getId())
                .bookId(book.getId())
                .borrowDate(LocalDate.now())
                .depositAmount(newBorrowedBook.getDepositAmount())
                .returned(false)
                .build();

        return borrowedBookDao.borrowBook(borrowedBook);
    }

    @Override
    public boolean returnBook(Long borrowId) {
        BorrowedBook borrowedBook = borrowedBookDao.findById(borrowId);
        Book book = existingBook(borrowedBook.getBookId());
        bookDao.updateBookCopies(book.getId(), book.getAvailableCopies() + 1);

        return borrowedBookDao.returnBook(borrowId);
    }

    @Override
    public List<BorrowedBook> getAllBorrowedBooks() {
        return borrowedBookDao.getAllBorrowedBooks();
    }

    @Override
    public List<BorrowedBook> getBorrowedBooksByUser(Long userId) {
        existingUser(userId);
        return borrowedBookDao.getBorrowedBooksByUserId(userId);
    }

    private Book existingBook(Long bookId) {
        Book book = bookDao.findBookById(bookId);
        if (book == null)
            throw new IllegalArgumentException("Book not found");
        return book;
    }

    private User existingUser(Long userId) {
        User user = userDao.findUserById(userId);
        if (user == null)
            throw new IllegalArgumentException("User not found");
        return user;
    }
}
