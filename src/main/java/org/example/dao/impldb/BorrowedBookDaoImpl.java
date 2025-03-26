package org.example.dao.impldb;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.example.dao.BorrowedBookDao;
import org.example.database.DBContext;
import org.example.model.BorrowedBook;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class BorrowedBookDaoImpl implements BorrowedBookDao {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    @Override
    public BorrowedBook borrowBook(BorrowedBook newBorrowedBook) {
        String query = "INSERT INTO borrowed_book (user_id, book_id, borrow_date, deposit_amount, returned) VALUES (?, ?, ?, ?, ?)";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, newBorrowedBook.getUserId());
            ps.setLong(2, newBorrowedBook.getBookId());
            ps.setDate(3, Date.valueOf(newBorrowedBook.getBorrowDate()));
            ps.setDouble(4, newBorrowedBook.getDepositAmount());
            ps.setBoolean(5, false);

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    newBorrowedBook.setId(rs.getLong(1)); // Lấy ID mới
                }
            }
            return newBorrowedBook;
        } catch (Exception e) {
            System.out.println("Có lỗi kết nối csdl");
        }
        return null;
    }

    @Override
    public Boolean returnBook(Long borrowId) {
        String query = "UPDATE borrowed_book SET return_date = ?, deposit_amount = ?, returned = TRUE WHERE id = ? AND returned = false";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setDate(1, Date.valueOf(LocalDate.now()));
            ps.setDouble(2, 0);
            ps.setLong(3, borrowId);

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                return true;
            }
            System.out.println("Không tìm thấy dữ liệu cho mượn sách!");
        } catch (Exception e) {
            System.out.println("Có lỗi kết nối csdl");
        }
        return false;
    }

    @Override
    public List<BorrowedBook> getAllBorrowedBooks() {
        String query = "SELECT id, user_id, book_id, borrow_date, return_date, returned FROM borrowed_book where returned = false";

        List<BorrowedBook> borrowedBooks = new ArrayList<>();
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);

            rs = ps.executeQuery();
            while (rs.next()) {
                Date returnDate = rs.getDate("return_date");
                BorrowedBook book = BorrowedBook.builder()
                        .id(rs.getLong("id"))
                        .userId(rs.getLong("user_id"))
                        .bookId(rs.getLong("book_id"))
                        .borrowDate(rs.getDate("borrow_date").toLocalDate())
                        .returnDate(returnDate != null ? returnDate.toLocalDate() : null)
                        .returned(rs.getBoolean("returned"))
                        .build();
                borrowedBooks.add(book);
            }
            return borrowedBooks;
        } catch (Exception e) {
            System.out.println("Có lỗi kết nối csdl");
        }
        return List.of();
    }

    @Override
    public List<BorrowedBook> getBorrowedBooksByUserId(Long userId) {
        String query = "SELECT id, user_id, book_id, borrow_date, return_date, returned FROM borrowed_book WHERE user_id = ? and returned = false";
        List<BorrowedBook> borrowedBooks = new ArrayList<>();
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);

            ps.setLong(1, userId);

            rs = ps.executeQuery();
            while (rs.next()) {
                Date returnDate = rs.getDate("return_date");
                BorrowedBook book = BorrowedBook.builder()
                        .id(rs.getLong("id"))
                        .userId(rs.getLong("user_id"))
                        .bookId(rs.getLong("book_id"))
                        .borrowDate(rs.getDate("borrow_date").toLocalDate())
                        .returnDate(returnDate != null ? returnDate.toLocalDate() : null)
                        .returned(rs.getBoolean("returned"))
                        .build();
                borrowedBooks.add(book);
            }
            return borrowedBooks;
        } catch (Exception e) {
            System.out.println("Có lỗi kết nối csdl");
        }
        return List.of();
    }

    @Override
    public BorrowedBook findById(Long borrowId) {
        String query = "SELECT id, user_id, book_id, borrow_date, return_date, returned FROM borrowed_book WHERE id = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setLong(1, borrowId);

            rs = ps.executeQuery();
            if (rs.next()) {
                Date returnDate = rs.getDate("return_date");
                return BorrowedBook.builder()
                        .id(rs.getLong("id"))
                        .userId(rs.getLong("user_id"))
                        .bookId(rs.getLong("book_id"))
                        .borrowDate(rs.getDate("borrow_date").toLocalDate())
                        .returnDate(returnDate != null ? returnDate.toLocalDate() : null)
                        .returned(rs.getBoolean("returned"))
                        .build();
            }
        } catch (Exception e) {
            System.out.println("Có lỗi kết nối csdl");
        }
        return null;
    }
}
