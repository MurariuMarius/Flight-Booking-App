package com.fis.project.flightbookingapp.exceptions;

public class InvalidCredentialsException extends Exception {
    public InvalidCredentialsException() {
        super("The credentials you've entered are invalid");
    }
}
