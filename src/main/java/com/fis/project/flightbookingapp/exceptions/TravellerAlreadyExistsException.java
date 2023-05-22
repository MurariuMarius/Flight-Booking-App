package com.fis.project.flightbookingapp.exceptions;

public class TravellerAlreadyExistsException extends Throwable {
    public TravellerAlreadyExistsException(String name) {
        super(String.format("%s has already been added to your booking", name));
    }
}
