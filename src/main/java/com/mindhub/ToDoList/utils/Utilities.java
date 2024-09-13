package com.mindhub.ToDoList.utils;

public class Utilities {

    public static boolean isValidPassword(String password) {
        if (password == null || password.length() <= 6) {
            return false;
        }

        boolean hasUppercase = false;
        boolean hasDigitOrSpecialChar = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            }
            if (Character.isDigit(c) || !Character.isLetterOrDigit(c)) {
                hasDigitOrSpecialChar = true;
            }
        }

        return hasUppercase && hasDigitOrSpecialChar;
    }

}
