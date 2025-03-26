package org.example.dao;

import org.example.model.User;

import java.util.List;

public interface UserDao {
    User addUser(User newUser);
    Boolean updateUserById(Long userId, User updateUser);
    List<User> findUserByName(String name);
    User findUserById(Long id);
    Boolean deleteById(Long id);
    List<User> getAllUsers();
}
