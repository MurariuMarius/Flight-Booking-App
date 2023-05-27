package com.fis.project.flightbookingapp.exceptions;

import com.fis.project.flightbookingapp.model.Booking;

public class BookingAlreadyExistsException extends Exception {
    public BookingAlreadyExistsException(Booking booking) {
        super(String.format("%s has already booked flight %s on %s", booking.getUsername(),
                booking.getFlightNumber(), booking.getDate()));
    }
}
