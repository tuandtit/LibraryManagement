package org.example.dao.implfile;

import org.example.dao.UserDao;
import org.example.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserDaoImplFile implements UserDao {
    private static final String FILE_PATH = "src/main/java/data/users.csv";
    private List<User> users;

    public UserDaoImplFile() {
        this.users = loadUsersFromFile();
    }

    @Override
    public User addUser(User newUser) {
        newUser.setId(generateNewId());
        users.add(newUser);
        saveUsersToFile();
        return newUser;
    }

    @Override
    public Boolean updateUserById(Long userId, User updateUser) {
        for (User user : users) {
            if (user.getId().equals(userId)) {
                user.setName(updateUser.getName());
                user.setPhoneNumber(updateUser.getPhoneNumber());
                user.setAddress(updateUser.getAddress());
                user.setActive(updateUser.getActive());
                saveUsersToFile();
                return true;
            }
        }
        return false;
    }

    @Override
    public List<User> findUserByName(String name) {
        return users.stream()
                .filter(user -> user.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    @Override
    public User findUserById(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Boolean deleteById(Long id) {
        boolean removed = users.removeIf(user -> user.getId().equals(id));
        if (removed) {
            saveUsersToFile();
        }
        return removed;
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    private List<User> loadUsersFromFile() {
        List<User> userList = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return userList;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) {
                    User user = new User(Long.parseLong(data[0]), data[1], data[2], data[3], Boolean.parseBoolean(data[4]));
                    userList.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userList;
    }

    private void saveUsersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : users) {
                writer.write(user.getId() + "," + user.getName() + "," + user.getPhoneNumber() + "," + user.getAddress() + "," + user.getActive());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Long generateNewId() {
        return users.stream()
                .mapToLong(User::getId)
                .max()
                .orElse(0) + 1;
    }
}
