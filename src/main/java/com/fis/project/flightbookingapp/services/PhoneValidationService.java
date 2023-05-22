package com.fis.project.flightbookingapp.services;

import java.util.regex.Pattern;

// https://www.baeldung.com/java-regex-validate-phone-numbers
public class PhoneValidationService {

    private static final String REGEX =
            "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";

    public static boolean isValid(String phoneNumber) {
        return Pattern.compile(REGEX).matcher(phoneNumber).matches();
    }
}
