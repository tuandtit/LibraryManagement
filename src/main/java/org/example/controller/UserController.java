package org.example.controller;

import org.example.common.Validate;
import org.example.model.User;
import org.example.service.UserService;
import org.example.service.impl.UserServiceImpl;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UserController {
    private final Scanner scanner = new Scanner(System.in);
    private final UserService userService;

    public UserController(int choice) {
        userService = new UserServiceImpl(choice);
    }

    public void addUser() {
        System.out.println("->Add new user");
        System.out.println(userService.addUser(inputUserFromKeyboard()));
    }

    public void updateUser() {
        System.out.println("->Update User");
        Long id = null;
        while (id == null) {
            try {
                System.out.print("Enter ID: ");
                id = scanner.nextLong();
            } catch (InputMismatchException e) {
                System.out.println("Invalid format, please enter again.");
            }
            scanner.nextLine();
        }
        if (userService.findUserById(id) == null) {
            System.out.println("No user found to update with ID: " + id);
            return;
        }
        User updateUser = inputUpdateUser(id);
        if (userService.updateUser(id, updateUser)) {
            System.out.println("update user successfully");
        } else System.out.println("update user fail");
    }

    public void searchUserByName() {
        System.out.println("->Search user by name");
        String name = inputName();
        List<User> users = userService.findUserByName(name);
        if (!users.isEmpty()) {
            System.out.println("User found: ");
            users.forEach(System.out::println);
        } else System.out.println("No found user with name: " + name);
    }

    public void showAllUser() {
        List<User> users = userService.showAllUsers();
        if (users.isEmpty()) {
            System.out.println("Không có user nào");
        } else users.forEach(System.out::println);
    }

    public void deleteUser() {
        System.out.println("->Delete user By ID");
        Long id = null;
        while (id == null) {
            try {
                System.out.print("Enter ID: ");
                id = scanner.nextLong();
            } catch (InputMismatchException e) {
                System.out.println("Invalid format, please enter again.");
            }
            scanner.nextLine();
        }
        if (userService.deleteUserById(id)) {
            System.out.println("Xóa thành công");
        } else System.out.println("Xóa thất bại");
    }

    private User inputUpdateUser(Long id) {
        User updateUser = userService.findUserById(id);
        while (true) {
            int choice = getMenuUpdated();
            switch (choice) {
                case 1:
                    String name = inputName();
                    updateUser.setName(name);
                    break;
                case 2:
                    String phoneNumber = inputPhoneNumber();
                    updateUser.setPhoneNumber(phoneNumber);
                    break;
                case 3:
                    String address = inputAddress();
                    updateUser.setAddress(address);
                    break;
                case 4:
                    return updateUser;
            }
        }
    }

    private User inputUserFromKeyboard() {
        String name = inputName();
        String phoneNumber = inputPhoneNumber();
        String address = inputAddress();
        return User.builder()
                .name(name)
                .phoneNumber(phoneNumber)
                .address(address)
                .build();
    }

    private String inputAddress() {
        String address;
        do {
            System.out.print("Enter address: ");
            address = scanner.nextLine();
        } while (!Validate.isValidAddress(address));
        return address;
    }

    private String inputPhoneNumber() {
        String phoneNumber;
        do {
            System.out.print("Enter phoneNumber: ");
            phoneNumber = scanner.nextLine();
        }
        while (!Validate.isValidPhoneNumber(phoneNumber));
        return phoneNumber;
    }

    private String inputName() {
        String name;
        do {
            System.out.print("Enter name: ");
            name = scanner.nextLine();
        }
        while (Validate.isValidName(name));
        return name;
    }


    private int getMenuUpdated() {
        printMenuUpdated();

        int choice = 0;
        try {
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.print("Invalid format, please choose menu again.");
            return getMenuUpdated();
        }

        if (choice < 1 || choice > 4) {
            System.out.println("Invalid choice. Please enter between 1 and 4");
            return getMenuUpdated();
        }
        System.out.println();
        return choice;
    }

    private void printMenuUpdated() {
        System.out.println("\n->Choose the field that you want to update");
        System.out.println("1. Name");
        System.out.println("2. Phone number");
        System.out.println("3. Address");
        System.out.println("4. Done");
        System.out.print("Enter your choice:");
    }
}
