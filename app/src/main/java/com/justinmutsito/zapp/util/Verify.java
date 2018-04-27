package com.justinmutsito.zapp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Verify {
    public static boolean checkEmail(String email) {
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(email);

        return mat.matches();

    }

    public static boolean checkPassword(String password) {
        if (password.length() < 8) {
            return false;
        } else {
            char c;
            for (int i = 0; i < password.length(); i++) {
                c = password.charAt(i);
                if (!Character.isLetterOrDigit(c)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean checkUsername(String name, String surname) {
        return name.length() >= 3 && surname.length() >= 3;
    }
}
