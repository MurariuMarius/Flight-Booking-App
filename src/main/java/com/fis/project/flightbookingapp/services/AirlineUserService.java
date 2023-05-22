package com.fis.project.flightbookingapp.services;

import com.fis.project.flightbookingapp.exceptions.InvalidCredentialsException;
import com.fis.project.flightbookingapp.exceptions.UserAlreadyExistsException;
import com.fis.project.flightbookingapp.model.Airline;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;

import java.util.List;
import java.util.Objects;

public class AirlineUserService {
    private static ObjectRepository<Airline> airlineUserRepository = DatabaseService.getDatabase().getRepository(Airline.class);

    public static void addUser(Airline user) throws UserAlreadyExistsException {
        checkUserDoesNotAlreadyExist(user);
        airlineUserRepository.insert(user);
    }

    private static void checkUserDoesNotAlreadyExist(Airline user) throws UserAlreadyExistsException {
        for (Airline u : airlineUserRepository.find()) {
            if (Objects.equals(user.getUsername(), u.getUsername())) {
                throw new UserAlreadyExistsException(user.getUsername());
            }
        }
    }

    public static void checkCredentials(String username, String password) throws InvalidCredentialsException {
        Cursor<Airline> cursor = airlineUserRepository.find(ObjectFilters.and(
                ObjectFilters.eq("username", username),
                ObjectFilters.eq("password", PasswordEncodingService.encodePassword(username, password)))
        );
        if (cursor.totalCount() != 1) {
            throw new InvalidCredentialsException();
        }
    }

    public static List<Airline> getAirlineUsers() {
        return airlineUserRepository.find().toList();
    }
}
