package org.example.common;

import java.time.LocalDate;

import static org.example.common.Constants.*;

public class Validate {
    public static boolean isValidName(String name) {
        if (name == null || name.isBlank() || name.length() > MAX_NAME_LENGTH) {
            System.out.println("Name must not be blank and must be less than " + MAX_NAME_LENGTH + " characters.");
            return true;
        }
        return false;
    }

    public static boolean isValidPublishedDate(LocalDate publishedDate) {
        if (publishedDate == null || publishedDate.getYear() < MIN_PUBLISH_DATE || publishedDate.getYear() > MAX_PUBLISH_DATE) {
            System.out.println("Birthdate must be between " + MIN_PUBLISH_DATE + " and " + MAX_PUBLISH_DATE);
            return false;
        }
        return true;
    }

    public static boolean isValidTitle(String title) {
        if (title == null || title.isBlank() || title.length() > MAX_TITLE_LENGTH) {
            System.out.println("Title must not be blank and must be less than " + MAX_TITLE_LENGTH + " characters.");
            return true;
        }
        return false;
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank() || phoneNumber.length() > MAX_PHONE_NUMBER || phoneNumber.length() < 8) {
            System.out.println("Phone number must not be blank and must be between 8 and 10 characters.");
            return false;
        }
        return true;
    }

    public static boolean isValidAddress(String address) {
        if (address.length() > MAX_ADDRESS_LENGTH || address.isBlank()) {
            System.out.println("Address must be not blank and less than " + MAX_ADDRESS_LENGTH + " characters");
            return false;
        }
        return true;
    }
}
