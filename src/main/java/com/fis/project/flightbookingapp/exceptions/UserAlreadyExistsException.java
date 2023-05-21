package com.fis.project.flightbookingapp.exceptions;

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String username) {
        super(String.format("The username %s already exists!", username));
    }
}
