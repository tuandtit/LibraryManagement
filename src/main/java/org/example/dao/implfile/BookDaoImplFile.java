package org.example.dao.implfile;

import org.example.dao.BookDao;
import org.example.model.Book;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookDaoImplFile implements BookDao {
    private static final String FILE_PATH = "src/main/java/data/books.csv";
    private List<Book> books;

    public BookDaoImplFile() {
        this.books = loadBooksFromFile();
    }

    @Override
    public Book addBook(Book newBook) {
        newBook.setId(generateNewId());
        books.add(newBook);
        saveBooksToFile();
        return newBook;
    }

    @Override
    public Book findBookById(Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Book> findBookByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .collect(Collectors.toList());
    }

    @Override
    public Boolean updateBookById(Long id, Book updatedBook) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId().equals(id)) {
                books.set(i, updatedBook);
                saveBooksToFile();
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean deleteById(Long id) {
        boolean removed = books.removeIf(book -> book.getId().equals(id));
        if (removed) {
            saveBooksToFile();
        }
        return removed;
    }

    @Override
    public Boolean updateBookCopies(Long bookId, int change) {
        for (Book book : books) {
            if (book.getId().equals(bookId)) {
                book.setAvailableCopies(book.getAvailableCopies() + change);
                saveBooksToFile();
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    private List<Book> loadBooksFromFile() {
        List<Book> bookList = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return bookList;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 7) {
                    Book book = new Book(
                            Long.parseLong(data[0]),
                            data[1],
                            data[2],
                            LocalDate.parse(data[3]),
                            Integer.parseInt(data[4]),
                            Long.parseLong(data[5]),
                            Boolean.parseBoolean(data[6])
                    );
                    bookList.add(book);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    private void saveBooksToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Book book : books) {
                writer.write(book.getId() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getPublishedDate() + "," + book.getAvailableCopies() + "," + book.getCategoryId() + "," + book.getActive());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Long generateNewId() {
        return books.stream()
                .mapToLong(Book::getId)
                .max()
                .orElse(0) + 1;
    }
}