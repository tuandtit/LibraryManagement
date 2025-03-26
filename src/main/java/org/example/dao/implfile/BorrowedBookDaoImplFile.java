package org.example.dao.implfile;

import org.example.dao.BorrowedBookDao;
import org.example.model.BorrowedBook;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BorrowedBookDaoImplFile implements BorrowedBookDao {
    private static final String FILE_PATH = "src/main/java/data/borrowed_books.csv";
    private List<BorrowedBook> borrowedBooks;

    public BorrowedBookDaoImplFile() {
        this.borrowedBooks = loadBorrowedBooksFromFile();
    }

    @Override
    public BorrowedBook borrowBook(BorrowedBook newBorrowedBook) {
        newBorrowedBook.setId(generateNewId());
        borrowedBooks.add(newBorrowedBook);
        saveBorrowedBooksToFile();
        return newBorrowedBook;
    }

    @Override
    public Boolean returnBook(Long borrowId) {
        for (BorrowedBook borrowedBook : borrowedBooks) {
            if (borrowedBook.getId().equals(borrowId) && !borrowedBook.getReturned()) {
                borrowedBook.setReturned(true);
                borrowedBook.setReturnDate(LocalDate.now());
                saveBorrowedBooksToFile();
                return true;
            }
        }
        return false;
    }

    @Override
    public List<BorrowedBook> getAllBorrowedBooks() {
        return borrowedBooks.stream()
                .filter(book -> !book.getReturned())
                .toList();
    }

    @Override
    public List<BorrowedBook> getBorrowedBooksByUserId(Long userId) {
        return borrowedBooks.stream()
                .filter(book -> book.getUserId().equals(userId) && !book.getReturned())
                .toList();
    }

    @Override
    public BorrowedBook findById(Long borrowId) {
        return borrowedBooks.stream()
                .filter(book -> book.getId().equals(borrowId))
                .findFirst()
                .orElse(null);
    }

    private List<BorrowedBook> loadBorrowedBooksFromFile() {
        List<BorrowedBook> bookList = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return bookList;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 7) {
                    BorrowedBook borrowedBook = new BorrowedBook(
                            Long.parseLong(data[0]),
                            Long.parseLong(data[1]),
                            Long.parseLong(data[2]),
                            LocalDate.parse(data[3]),
                            data[4].equals("null") ? null : LocalDate.parse(data[4]),
                            Double.parseDouble(data[5]),
                            Boolean.parseBoolean(data[6])
                    );
                    bookList.add(borrowedBook);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    private void saveBorrowedBooksToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (BorrowedBook borrowedBook : borrowedBooks) {
                writer.write(borrowedBook.getId() + "," +
                        borrowedBook.getUserId() + "," +
                        borrowedBook.getBookId() + "," +
                        borrowedBook.getBorrowDate() + "," +
                        (borrowedBook.getReturnDate() != null ? borrowedBook.getReturnDate() : "null") + "," +
                        borrowedBook.getDepositAmount() + "," +
                        borrowedBook.getReturned());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Long generateNewId() {
        return borrowedBooks.stream()
                .mapToLong(BorrowedBook::getId)
                .max()
                .orElse(0) + 1;
    }
}
