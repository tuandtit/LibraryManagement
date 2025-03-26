package org.example.dao.impldb;

import org.example.dao.CategoryDao;
import org.example.database.DBContext;
import org.example.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    @Override
    public Category addCategory(Category newCategory) {
        String query = "INSERT INTO category (name, active) VALUES (?, ?)";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, newCategory.getName());
            ps.setBoolean(2, true);

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    newCategory.setId(rs.getLong(1));
                    newCategory.setActive(true);// Lấy ID mới
                }
            }
            return newCategory;
        } catch (Exception e) {
            System.out.println("Có lỗi kết nối csdl 2");
        }
        return null;
    }

    @Override
    public boolean updateCategory(Long id, String newName) {
        String query = "update category \r\n"
                + "set name = ? where id = ?;";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);

            ps.setString(1, newName);
            ps.setLong(2, id);
            int affectedRows = ps.executeUpdate();

            return affectedRows > 0;
        } catch (Exception e) {
            System.out.println("Có lỗi kết nối csdl");
        }
        return false;
    }

    @Override
    public Category findActiveCategoryById(Long id) {
        String query = "SELECT id, name, active FROM category WHERE id = ? AND active = true";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next())
                return Category.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .active(rs.getBoolean("active"))
                        .build();
        } catch (Exception e) {
            System.out.println("Có lỗi kết nối csdl");
        }
        return null;
    }

    @Override
    public Category findActiveCategoryByName(String name) {
        String query = "SELECT id, name, active FROM category WHERE name = ? AND active = true";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, name);
            rs = ps.executeQuery();
            if (rs.next()) {
                return Category.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .active(rs.getBoolean("active"))
                        .build();
            }
        } catch (Exception e) {
            System.out.println("Có lỗi kết nối csdl");
        }
        return null;
    }

    @Override
    public boolean deleteCategory(Long id) {
        String query = "update category \r\n"
                + "set active = ?" + "where id = ?;";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);

            ps.setBoolean(1, false);
            ps.setLong(2, id);
            int affectedRows = ps.executeUpdate();

            return affectedRows > 0;
        } catch (Exception e) {
            System.out.println("Có lỗi kết nối csdl");
        }
        return false;
    }

    @Override
    public List<Category> getAllCategories() {
        String query = "SELECT id, name, active FROM category where active = true";

        List<Category> categories = new ArrayList<>();
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);

            rs = ps.executeQuery();
            while (rs.next()) {
                categories.add(
                        Category.builder()
                                .id(rs.getLong("id"))
                                .name(rs.getString("name"))
                                .active(rs.getBoolean("active"))
                                .build()
                );
            }
            return categories;
        } catch (Exception e) {
            System.out.println("Có lỗi kết nối csdl");
        }
        return List.of();
    }
}
