package org.example.service.impl;

import org.example.dao.UserDao;
import org.example.dao.impldb.UserDaoImpl;
import org.example.dao.implfile.UserDaoImplFile;
import org.example.model.User;
import org.example.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl(int choice) {
        if (choice == 1) {
            userDao = new UserDaoImpl();
        } else {
            userDao = new UserDaoImplFile();
        }
    }

    @Override
    public User addUser(User user) {

        if (user == null || user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be empty.");
        }
        if (user.getPhoneNumber() == null || user.getPhoneNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number is required.");
        }

        user.setActive(true); // Mặc định user là active khi thêm mới
        return userDao.addUser(user);
    }

    @Override
    public Boolean updateUser(Long userId, User updateUser) {
        if (userId == null || updateUser == null) {
            throw new IllegalArgumentException("User ID and update data cannot be null.");
        }

        return userDao.updateUserById(userId, updateUser);
    }

    @Override
    public User findUserById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be empty.");
        }

        return userDao.findUserById(id);
    }

    @Override
    public List<User> findUserByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        List<User> users = userDao.findUserByName(name);

        if (!users.isEmpty())
            return users;
        else return List.of();
    }

    @Override
    public List<User> showAllUsers() {
        List<User> users = userDao.getAllUsers();
        if (!users.isEmpty())
            return users;
        else return List.of();
    }

    @Override
    public Boolean deleteUserById(Long id) {
        return userDao.deleteById(id);
    }
}
