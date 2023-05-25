package com.fis.project.flightbookingapp.services;

import com.fis.project.flightbookingapp.exceptions.AirportAlreadyExistsException;
import com.fis.project.flightbookingapp.exceptions.NotInDatabaseException;
import com.fis.project.flightbookingapp.model.Airport;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;

import java.util.List;

public class AirportService {
    private static ObjectRepository<Airport> airportObjectRepository =
            DatabaseService.getDatabase().getRepository(Airport.class);

    public static void addAirport(Airport airport) throws AirportAlreadyExistsException {
        checkAirportDoesNotAlreadyExist(airport);
        airportObjectRepository.insert(airport);
    }

    public static void addAirports(List<Airport> airports) throws AirportAlreadyExistsException {
        for (Airport a : airports) {
            addAirport(a);
        }
    }

    private static void checkAirportDoesNotAlreadyExist(Airport airport) throws AirportAlreadyExistsException {
        for (Airport a : airportObjectRepository.find()) {
            if (a.equals(airport)) {
                throw new AirportAlreadyExistsException(airport.getAirportCode());
            }
        }
    }

    public static Airport getAirportByCode(String airportCode) throws NotInDatabaseException {
        Cursor<Airport> cursor = airportObjectRepository.find(ObjectFilters.eq("airportCode", airportCode));
        if (cursor.totalCount() != 1) {
            throw new NotInDatabaseException(
                    String.format("There is no airport with the code %s in the database", airportCode)
            );
        } else {
            return cursor.firstOrDefault();
        }
    }

    public static List<Airport> getAirports() {
        return airportObjectRepository.find().toList();
    }
}
