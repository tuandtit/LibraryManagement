package org.example.service.impl;

import org.example.dao.BookDao;
import org.example.dao.CategoryDao;
import org.example.dao.impldb.BookDaoImpl;
import org.example.dao.impldb.CategoryDaoImpl;
import org.example.dao.implfile.BookDaoImplFile;
import org.example.dao.implfile.CategoryDaoImplFile;
import org.example.model.Book;
import org.example.model.Category;
import org.example.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final CategoryDao categoryDao;

    public BookServiceImpl(int choice) {
        if (choice == 1) {
            categoryDao = new CategoryDaoImpl();
            bookDao = new BookDaoImpl();
        } else {
            categoryDao = new CategoryDaoImplFile();
            bookDao = new BookDaoImplFile();
        }
    }

    @Override
    public Book addBook(Book newBook) {
        // Kiểm tra dữ liệu đầu vào
        if (newBook == null || newBook.getTitle() == null || newBook.getTitle().isEmpty()) {
            System.out.println("Tiêu đề sách không được để trống.");
            return null;
        }

        if (newBook.getAuthor() == null || newBook.getAuthor().isEmpty()) {
            System.out.println("Tác giả không được để trống.");
            return null;
        }

        if (newBook.getAvailableCopies() < 0) {
            System.out.println("Số lượng sách phải lớn hơn hoặc bằng 0.");
            return null;
        }

        existingCategory(newBook.getCategoryId());

        return bookDao.addBook(newBook);
    }

    @Override
    public Boolean updateBook(Long bookId, Book updateBook) {
        if (bookId == null || updateBook == null) {
            throw new IllegalArgumentException("Bk ID and update data cannot be null.");
        }

        if (updateBook.getAvailableCopies() < 0) {
            System.out.println("Số lượng sách không thể nhỏ hơn 0");
            return false;
        }

        existingCategory(updateBook.getCategoryId());

        return bookDao.updateBookById(bookId, updateBook);
    }

    private Book existingBook(Long bookId) {
        Book book = bookDao.findBookById(bookId);
        if (book == null)
            throw new IllegalArgumentException("Book not found");
        return book;
    }

    @Override
    public Book findBookById(Long id) {
        return existingBook(id);
    }

    @Override
    public List<Book> findBookByTitle(String title) {
        if (title == null || title.isEmpty()) {
            System.out.println("Tiêu đề sách không được để trống.");
            return List.of();
        }

        List<Book> books = bookDao.findBookByTitle(title);
        if (books.isEmpty()) {
            System.out.println("Không tìm thấy sách nào với tiêu đề: " + title);
        }
        return books;
    }

    @Override
    public List<Book> showAllBooks() {
        return bookDao.getAllBooks();
    }

    @Override
    public Boolean deleteBookById(Long id) {
        return bookDao.deleteById(id);
    }

    private Category existingCategory(Long categoryId) {
        Category category = categoryDao.findActiveCategoryById(categoryId);
        if (category == null)
            throw new IllegalArgumentException("Category not found");
        return category;
    }
}
