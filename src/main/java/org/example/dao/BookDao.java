package org.example.dao;

import org.example.model.Book;

import java.util.List;

public interface BookDao {
    Book addBook(Book newBook);

    Book findBookById(Long id);

    List<Book> findBookByTitle(String title);

    Boolean updateBookById(Long id, Book updatedBook);

    Boolean deleteById(Long id);

    Boolean updateBookCopies(Long bookId, int change);

    List<Book> getAllBooks();
}
