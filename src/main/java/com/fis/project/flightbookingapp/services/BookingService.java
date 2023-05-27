package com.fis.project.flightbookingapp.services;

import com.fis.project.flightbookingapp.exceptions.BookingAlreadyExistsException;
import com.fis.project.flightbookingapp.exceptions.FlightAlreadyExistsException;
import com.fis.project.flightbookingapp.exceptions.NotInDatabaseException;
import com.fis.project.flightbookingapp.model.*;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static com.fis.project.flightbookingapp.services.FlightService.checkFlightDoesNotAlreadyExist;

public class BookingService {
    private static ObjectRepository<Booking> bookingRepository = DatabaseService.getDatabase().getRepository(Booking.class);

    public static void addBooking(Booking booking)
            throws BookingAlreadyExistsException, NotInDatabaseException {
        checkBookingDoesNotAlreadyExist(booking);
        try {
            checkFlightDoesNotAlreadyExist(FlightService.getFlightByNumber(booking.getFlightNumber()));
            throw new NotInDatabaseException(String.format("Flight % is not in the database"));
        } catch (FlightAlreadyExistsException e) {}
        bookingRepository.insert(booking);
    }

    public static void updateBooking(Booking booking) {
        bookingRepository.update(booking);
    }

    public static void removeBooking(Booking booking) {
        bookingRepository.remove(booking);
    }

    public static List<Booking> getBookingsByStatus(BookingStatus bookingStatus) {
        Cursor<Booking> cursor = bookingRepository.find(
                ObjectFilters.eq("bookingStatus", bookingStatus)
        );
        return cursor.toList();
    }

    public static List<Booking> getBookingsByStatus(BookingStatus bookingStatus, Airline airline) {
        return getBookingsByStatus(bookingStatus).stream().filter(
                b -> {
                    try {
                        return FlightService.getFlightByNumber(b.getFlightNumber()).getAirlineCode().equals(airline.getIcaoCode());
                    } catch (NotInDatabaseException e) {
                        System.out.println(e);
                    }
                    return false;
                }
        ).toList();
    }

    public static List<Booking> getBookingsForFlight(String flightNumber) {
        Cursor<Booking> cursor = bookingRepository.find(
                ObjectFilters.eq("flightNumber", flightNumber)
        );
        return cursor.toList();
    }


    public static List<Booking> getBookingsForFlight(Flight flight) {
        return getBookingsForFlight(flight.getFlightNumber());
    }

    public static List<Booking> getBookingsForFlight(String flightNumber, LocalDate date) {
        Cursor<Booking> cursor = bookingRepository.find(
                ObjectFilters.and(
                        ObjectFilters.eq("flightNumber", flightNumber),
                        ObjectFilters.eq("date", date.toString())
                )
        );
        return cursor.toList();
    }

    public static List<Booking> getBookingForFlight(Flight flight, LocalDate date) {
        return getBookingsForFlight(flight.getFlightNumber(), date);
    }

    public static List<Booking> getBookingsForClient(Client client) {
        Cursor<Booking> cursor = bookingRepository.find(
                ObjectFilters.eq("username", client.getUsername())
        );
        return cursor.toList();
    }

    public static List<Booking> getBookingsForClient(Client client, BookingStatus bookingStatus) {
        Cursor<Booking> cursor = bookingRepository.find(
                ObjectFilters.and(
                        ObjectFilters.eq("username", client.getUsername()),
                        ObjectFilters.eq("bookingStatus", bookingStatus)
                )
        );
        return cursor.toList();
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
