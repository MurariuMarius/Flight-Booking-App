package com.fis.project.flightbookingapp.services;

import java.util.regex.Pattern;

// https://www.baeldung.com/java-email-validation-regex
public class EmailValidationService {

    private static final String REGEX = "^[\\p{L}0-9!#$%&'*+\\/=?^_`{|}~-][\\p{L}0-9.!#$%&'*+\\/=?^_`{|}~-]{0,63}@[\\p{L}0-9-]+(?:\\.[\\p{L}0-9-]{2,7})*$";
    public static boolean isValid(String emailAddrToValidate) {
        return Pattern.compile(REGEX)
                .matcher(emailAddrToValidate)
                .matches();
    }
}
