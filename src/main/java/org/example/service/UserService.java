package org.example.service;

import org.example.model.User;

import java.util.List;

public interface UserService {
    User addUser(User user);

    Boolean updateUser(Long userId, User updateUser);

    User findUserById(Long id);

    List<User> findUserByName(String name);

    List<User> showAllUsers();

    Boolean deleteUserById(Long id);
}
