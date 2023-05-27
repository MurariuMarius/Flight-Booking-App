package com.fis.project.flightbookingapp.services;

import com.fis.project.flightbookingapp.exceptions.FlightAlreadyExistsException;
import com.fis.project.flightbookingapp.exceptions.NotInDatabaseException;
import com.fis.project.flightbookingapp.model.Airport;
import com.fis.project.flightbookingapp.model.Flight;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;

import java.time.DayOfWeek;
import java.util.List;

public class FlightService {
    private static ObjectRepository<Flight> flightObjectRepository =
            DatabaseService.getDatabase().getRepository(Flight.class);

    public static void addFlight(Flight flight) throws FlightAlreadyExistsException {
        checkFlightDoesNotAlreadyExist(flight);
        flightObjectRepository.insert(flight);
    }

    public static void updateFlight(Flight flight) {
        flightObjectRepository.update(flight);
    }

    public static void removeFlight(Flight flight) {
        flightObjectRepository.remove(flight);
    }

    public static void removeFlight(String flightNumber) {
        try {
            flightObjectRepository.remove(getFlightByNumber(flightNumber));
        } catch (NotInDatabaseException e) {
            System.out.println("Flight not in database");
        }
    }

    public static Flight getFlightByNumber(String flightNumber) throws NotInDatabaseException {
        Cursor<Flight> cursor = flightObjectRepository.find(ObjectFilters.eq("flightNumber", flightNumber));
        if (cursor.totalCount() != 1) {
            throw new NotInDatabaseException(String.format("Flight %s is not in the database", flightNumber));
        }
        return cursor.firstOrDefault();
    }

    public static List<Flight> getFlightsByDepartureAirport(String departureAirportCode) {

        // Verify airport in database
        try {
            AirportService.getAirportByCode(departureAirportCode);
        } catch (NotInDatabaseException e) {
            System.out.println(e);
        }

        Cursor<Flight> cursor = flightObjectRepository.find(
                ObjectFilters.eq("departureAirportCode", departureAirportCode)
        );

        return cursor.toList();
    }

    public static List<Flight> getFlightsByDepartureAirport(Airport airport) {
        return getFlightsByDepartureAirport(airport.getAirportCode());
    }

    public static List<Flight> getFlightsOnRoute(Airport departureAirport, Airport arrivalAirport, DayOfWeek dayOfWeek) {
        Cursor<Flight> cursor = flightObjectRepository.find(
                ObjectFilters.and(
                        ObjectFilters.eq("departureAirportCode", departureAirport.getAirportCode()),
                        ObjectFilters.eq("arrivalAirportCode", arrivalAirport.getAirportCode())
                )
        );
        return cursor.toList().stream().filter(f -> f.getOperatingWeekDays().contains(dayOfWeek)).toList();
    }

    static void checkFlightDoesNotAlreadyExist(Flight flight) throws FlightAlreadyExistsException {
        for (Flight f : flightObjectRepository.find()) {
            if (f.getFlightNumber().equals(flight.getFlightNumber())) {
                throw new FlightAlreadyExistsException(flight.getFlightNumber());
            }
        }
    }

    public static List<Flight> getFlights() {
        return flightObjectRepository.find().toList();
    }
}
