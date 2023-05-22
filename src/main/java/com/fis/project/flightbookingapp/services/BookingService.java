package com.fis.project.flightbookingapp.services;

import com.fis.project.flightbookingapp.exceptions.BookingAlreadyExistsException;
import com.fis.project.flightbookingapp.model.Booking;
import org.dizitart.no2.objects.ObjectRepository;

import java.util.List;
import java.util.Objects;

public class BookingService {
    private static ObjectRepository<Booking> bookingRepository = DatabaseService.getDatabase().getRepository(Booking.class);

    public static void addBooking(Booking booking) throws BookingAlreadyExistsException {
        checkBookingDoesNotAlreadyExist(booking);
        bookingRepository.insert(booking);
    }

    private static void checkBookingDoesNotAlreadyExist(Booking booking) throws BookingAlreadyExistsException {
        for (Booking b : bookingRepository.find()) {
            if (Objects.equals(booking, b)) {
                throw new BookingAlreadyExistsException(booking);
            }
        }
    }

    public static List<Booking> getBookings() {
        return bookingRepository.find().toList();
    }
}
