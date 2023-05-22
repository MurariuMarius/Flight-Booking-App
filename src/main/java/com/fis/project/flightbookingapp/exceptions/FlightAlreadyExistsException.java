package com.fis.project.flightbookingapp.exceptions;

public class FlightAlreadyExistsException extends Exception {
    public FlightAlreadyExistsException(String flightNumber) {
        super(String.format("A flight with the id %s already exists!", flightNumber));
    }
}
