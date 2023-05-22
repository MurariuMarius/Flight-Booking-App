package com.fis.project.flightbookingapp.services;

import com.fis.project.flightbookingapp.exceptions.UserAlreadyExistsException;
import com.fis.project.flightbookingapp.model.Airline;
import com.fis.project.flightbookingapp.model.User;
import org.dizitart.no2.objects.ObjectRepository;

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

    public static List<Airline> getAirlineUsers() {
        return airlineUserRepository.find().toList();
    }
}
