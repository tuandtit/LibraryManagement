package org.example.dao.impldb;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.example.dao.BookDao;
import org.example.database.DBContext;
import org.example.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookDaoImpl implements BookDao {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    @Override
    public Book addBook(Book newBook) {
        String query = "INSERT INTO book (title, author, published_date, available_copies, category_id) VALUES (?, ?, ?, ?, ?)";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, newBook.getTitle());
            ps.setString(2, newBook.getAuthor());
            ps.setDate(3, Date.valueOf(newBook.getPublishedDate()));
            ps.setInt(4, newBook.getAvailableCopies());
            ps.setLong(5, newBook.getCategoryId());
            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    newBook.setId(rs.getLong(1));
                    newBook.setActive(true);
                }
            }
            return newBook;
        } catch (Exception e) {
            System.out.println("Có lỗi kết nối csdl");
        }
        return null;
    }

    @Override
    public Book findBookById(Long id) {
        String query = "SELECT id, title, author, published_date, available_copies, category_id, active  FROM book WHERE id = ? and available_copies > 0 and active = true";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return Book.builder()
                        .id(rs.getLong("id"))
                        .title(rs.getString("title"))
                        .author(rs.getString("author"))
                        .publishedDate(rs.getDate("published_date").toLocalDate())
                        .availableCopies(rs.getInt("available_copies"))
                        .categoryId(rs.getLong("category_id"))
                        .active(rs.getBoolean("active"))
                        .build();
            }
        } catch (Exception e) {
            System.out.println("Có lỗi kết nối csdl");
        }
        return null;
    }

    @Override
    public List<Book> findBookByTitle(String title) {
        String query = "SELECT id, title, author, published_date, available_copies, category_id, active  FROM book WHERE title like ? and active = true";
        List<Book> books = new ArrayList<>();
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, "%" + title + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                books.add(Book.builder()
                        .id(rs.getLong("id"))
                        .title(rs.getString("title"))
                        .author(rs.getString("author"))
                        .publishedDate(rs.getDate("published_date").toLocalDate())
                        .availableCopies(rs.getInt("available_copies"))
                        .categoryId(rs.getLong("category_id"))
                        .active(rs.getBoolean("active"))
                        .build());
            }
            return books;
        } catch (Exception e) {
            System.out.println("Có lỗi kết nối csdl");
        }
        return List.of();
    }

    @Override
    public Boolean updateBookById(Long id, Book updatedBook) {
        String query = "update book "
                + " set title = ?, author = ?, published_date = ?, available_copies = ?, category_id = ? where id = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);

            ps.setString(1, updatedBook.getTitle());
            ps.setString(2, updatedBook.getAuthor());
            ps.setDate(3, Date.valueOf(updatedBook.getPublishedDate()));
            ps.setInt(4, updatedBook.getAvailableCopies());
            ps.setLong(5, updatedBook.getCategoryId());
            ps.setLong(6, id);

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            System.out.println("Có lỗi kết nối csdl");
        }
        return false;
    }

    @Override
    public Boolean deleteById(Long id) {
        String query = "update book set active = ? where id = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setBoolean(1, false);
            ps.setLong(2, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Có lỗi kết nối csdl");
        }
        return false;
    }

    @Override
    public Boolean updateBookCopies(Long bookId, int change) {
        String query = "UPDATE book SET available_copies = available_copies + ? WHERE id = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, change);
            ps.setLong(2, bookId);
            int affectedRows = ps.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Có lỗi kết nối csdl");
        }
        return false;
    }

    @Override
    public List<Book> getAllBooks() {
        String query = "SELECT id, title, author, published_date, available_copies, category_id, active FROM book where active = true";

        List<Book> books = new ArrayList<>();
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);

            rs = ps.executeQuery();
            while (rs.next()) {
                books.add(
                        Book.builder()
                                .id(rs.getLong("id"))
                                .title(rs.getString("title"))
                                .author(rs.getString("author"))
                                .publishedDate(rs.getDate("published_date").toLocalDate())
                                .availableCopies(rs.getInt("available_copies"))
                                .categoryId(rs.getLong("category_id"))
                                .active(rs.getBoolean("active"))
                                .build()
                );
            }
            return books;
        } catch (Exception e) {
            System.out.println("Có lỗi kết nối csdl");
        }
        return List.of();
    }
}
