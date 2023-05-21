package com.fis.project.flightbookingapp.services;

import com.fis.project.flightbookingapp.exceptions.UserAlreadyExistsException;
import com.fis.project.flightbookingapp.model.User;
import org.dizitart.no2.objects.ObjectRepository;

import java.util.List;
import java.util.Objects;

public class UserService {
    private static ObjectRepository<User> userRepository = DatabaseService.getDatabase().getRepository(User.class);

    public static void addUser(User user) throws UserAlreadyExistsException {
        checkUserDoesNotAlreadyExist(user);
        userRepository.insert(user);
    }

    private static void checkUserDoesNotAlreadyExist(User user) throws UserAlreadyExistsException {
        for (User u : userRepository.find()) {
            if (Objects.equals(user.getUsername(), u.getUsername())) {
                throw new UserAlreadyExistsException(user.getUsername());
            }
        }
    }

    public static List<User> getUsers() {
        return userRepository.find().toList();
    }
}
