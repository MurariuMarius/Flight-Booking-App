package com.fis.project.flightbookingapp.services;

import com.fis.project.flightbookingapp.exceptions.InvalidCredentialsException;
import com.fis.project.flightbookingapp.exceptions.UserAlreadyExistsException;
import com.fis.project.flightbookingapp.model.Client;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;

import java.util.List;
import java.util.Objects;

public class ClientUserService {
    private static ObjectRepository<Client> clientUserRepository = DatabaseService.getDatabase().getRepository(Client.class);

    public static void addUser(Client user) throws UserAlreadyExistsException {
        checkUserDoesNotAlreadyExist(user);
        clientUserRepository.insert(user);
    }

    public static void updateUser(Client user) {
        clientUserRepository.update(user);
    }

    private static void checkUserDoesNotAlreadyExist(Client user) throws UserAlreadyExistsException {
        for (Client u : clientUserRepository.find()) {
            if (Objects.equals(user.getUsername(), u.getUsername())) {
                throw new UserAlreadyExistsException(user.getUsername());
            }
        }
    }

    public static Client checkCredentials(String username, String password) throws InvalidCredentialsException {
        Cursor<Client> cursor = clientUserRepository.find(ObjectFilters.and(
                ObjectFilters.eq("username", username),
                ObjectFilters.eq("password", PasswordEncodingService.encodePassword(username, password)))
        );
        if (cursor.totalCount() != 1) {
            throw new InvalidCredentialsException();
        }
        return cursor.firstOrDefault();
    }
    public static Client getUserByUsername(String username)  {
        Cursor<Client> cursor = clientUserRepository.find(ObjectFilters.and(
                ObjectFilters.eq("username", username)));
        return cursor.firstOrDefault();
    }

    public static List<Client> getClientUsers() {
        return clientUserRepository.find().toList();
    }
}
