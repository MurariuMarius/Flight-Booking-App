package com.fis.project.flightbookingapp.services;

import com.fis.project.flightbookingapp.exceptions.UserAlreadyExistsException;
import com.fis.project.flightbookingapp.model.Client;
import org.dizitart.no2.objects.ObjectRepository;

import java.util.List;
import java.util.Objects;

public class ClientUserService {
    private static ObjectRepository<Client> clientUserRepository = DatabaseService.getDatabase().getRepository(Client.class);

    public static void addUser(Client user) throws UserAlreadyExistsException {
        checkUserDoesNotAlreadyExist(user);
        clientUserRepository.insert(user);
    }

    private static void checkUserDoesNotAlreadyExist(Client user) throws UserAlreadyExistsException {
        for (Client u : clientUserRepository.find()) {
            if (Objects.equals(user.getUsername(), u.getUsername())) {
                throw new UserAlreadyExistsException(user.getUsername());
            }
        }
    }

    public static List<Client> getClientUsers() {
        return clientUserRepository.find().toList();
    }
}
