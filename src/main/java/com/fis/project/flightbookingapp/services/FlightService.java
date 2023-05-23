package com.fis.project.flightbookingapp.services;

import com.fis.project.flightbookingapp.exceptions.FlightAlreadyExistsException;
import com.fis.project.flightbookingapp.model.Flight;
import org.dizitart.no2.objects.ObjectRepository;

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

    private static void checkFlightDoesNotAlreadyExist(Flight flight) throws FlightAlreadyExistsException {
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
