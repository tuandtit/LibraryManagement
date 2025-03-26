package org.example.service;

import org.example.model.Book;

import java.util.List;

public interface BookService {
    Book addBook(Book newBook);

    Boolean updateBook(Long bookId, Book updateBook);

    Book findBookById(Long id);

    List<Book> findBookByTitle(String title);

    List<Book> showAllBooks();

    Boolean deleteBookById(Long id);
}
