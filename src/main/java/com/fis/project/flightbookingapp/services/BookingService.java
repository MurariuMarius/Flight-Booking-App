package com.fis.project.flightbookingapp.services;

import com.fis.project.flightbookingapp.exceptions.BookingAlreadyExistsException;
import com.fis.project.flightbookingapp.exceptions.FlightAlreadyExistsException;
import com.fis.project.flightbookingapp.exceptions.NotInDatabaseException;
import com.fis.project.flightbookingapp.model.Booking;
import org.dizitart.no2.objects.ObjectRepository;

import java.util.List;
import java.util.Objects;

import static com.fis.project.flightbookingapp.services.FlightService.checkFlightDoesNotAlreadyExist;

public class BookingService {
    private static ObjectRepository<Booking> bookingRepository = DatabaseService.getDatabase().getRepository(Booking.class);

    public static void addBooking(Booking booking)
            throws BookingAlreadyExistsException, FlightAlreadyExistsException, NotInDatabaseException {
        checkBookingDoesNotAlreadyExist(booking);
        checkFlightDoesNotAlreadyExist(FlightService.getFlightByNumber(booking.getFlight()));
        bookingRepository.insert(booking);
    }

    public static void updateBooking(Booking booking) {
        bookingRepository.update(booking);
    }

    public static void removeBooking(Booking booking) {
        bookingRepository.remove(booking);
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
