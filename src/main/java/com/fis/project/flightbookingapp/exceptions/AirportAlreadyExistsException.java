package com.fis.project.flightbookingapp.exceptions;

public class AirportAlreadyExistsException extends Exception {
    public AirportAlreadyExistsException(String airportCode) {
        super(String.format("Airport %s already in database", airportCode));
    }
}
