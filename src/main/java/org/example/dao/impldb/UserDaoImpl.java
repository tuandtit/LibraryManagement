package org.example.dao.impldb;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.example.dao.UserDao;
import org.example.database.DBContext;
import org.example.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDaoImpl implements UserDao {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    @Override
    public User addUser(User newUser) {
        String query = "INSERT INTO user (name, phone_number, address, active) VALUES (?, ?, ?, ?)";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, newUser.getName());
            ps.setString(2, newUser.getPhoneNumber());
            ps.setString(3, newUser.getAddress());
            ps.setBoolean(4, true);

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    newUser.setId(rs.getLong(1));
                    newUser.setActive(true);
                }
            }
            return newUser;
        } catch (Exception e) {
            System.out.println("Có lỗi kết nối csdl");
        }
        return null;
    }

    @Override
    public Boolean updateUserById(Long userId, User updateUser) {
        String query = "update user \r\n"
                + "set name = ?, phone_number = ?, address = ?, active = ? where id = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);

            ps.setString(1, updateUser.getName());
            ps.setString(2, updateUser.getPhoneNumber());
            ps.setString(3, updateUser.getAddress());
            ps.setBoolean(4, updateUser.getActive());
            ps.setLong(5, userId);
            int affectedRows = ps.executeUpdate();

            return affectedRows > 0;
        } catch (Exception e) {
            System.out.println("Có lỗi kết nối csdl");
        }
        return false;
    }

    @Override
    public List<User> findUserByName(String name) {
        String query = "SELECT id, name, address, phone_number, active FROM user WHERE name like ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, "%" + name + "%");
            rs = ps.executeQuery();
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(User.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .address(rs.getString("address"))
                        .phoneNumber(rs.getString("phone_number"))
                        .active(rs.getBoolean("active"))
                        .build());
            }
            return users;
        } catch (Exception e) {
            System.out.println("Có lỗi kết nối csdl");
        }
        return List.of();
    }

    @Override
    public User findUserById(Long id) {
        String query = "SELECT id, name, address, phone_number, active FROM user WHERE id = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setLong(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                return User.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .address(rs.getString("address"))
                        .phoneNumber(rs.getString("phone_number"))
                        .active(rs.getBoolean("active"))
                        .build();
            }
        } catch (Exception e) {
            System.out.println("Có lỗi kết nối csdl");
        }
        return null;
    }

    @Override
    public Boolean deleteById(Long id) {
        String query = "update user set active = ? where id = ?";
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
    public List<User> getAllUsers() {
        String query = "SELECT id, name, address, phone_number, active FROM user where active = true";

        List<User> users = new ArrayList<>();
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);

            rs = ps.executeQuery();
            while (rs.next()) {
                users.add(
                        User.builder()
                                .id(rs.getLong("id"))
                                .name(rs.getString("name"))
                                .address(rs.getString("address"))
                                .phoneNumber(rs.getString("phone_number"))
                                .active(rs.getBoolean("active"))
                                .build()
                );
            }
            return users;
        } catch (Exception e) {
            System.out.println("Có lỗi kết nối csdl");
        }
        return List.of();
    }
}
