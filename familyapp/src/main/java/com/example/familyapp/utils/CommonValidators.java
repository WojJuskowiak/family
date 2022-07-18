package com.example.familyapp.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonValidators {
    public static boolean isNameValid(String name) {
        Pattern pattern = Pattern.compile("^[A-ZĄĆĘŁŃÓŚŹŻ][a-z,ąćęłńóśźż]{1,19}$");
        Matcher matcher = pattern.matcher(name);
        return matcher.find();
    }
}
