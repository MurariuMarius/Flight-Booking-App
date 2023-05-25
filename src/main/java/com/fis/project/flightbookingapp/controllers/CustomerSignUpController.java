package com.fis.project.flightbookingapp.controllers;

import com.fis.project.flightbookingapp.exceptions.UserAlreadyExistsException;
import com.fis.project.flightbookingapp.model.Client;
import com.fis.project.flightbookingapp.services.ClientUserService;
import com.fis.project.flightbookingapp.services.EmailValidationService;
import com.fis.project.flightbookingapp.services.PhoneValidationService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.Arrays;

public class CustomerSignUpController {

    @FXML
    private TextField emailField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField countryField;

    @FXML
    private Button createAccountButton;

    @FXML
    private TextField phoneNumberField;

    private Client client;

    @FXML
    void createCustomerAccount() {
        if (emailField.getText().isBlank() || addressField.getText().isBlank() || countryField.getText().isBlank() ||
        phoneNumberField.getText().isBlank()) {
            System.out.println("Missing fields");
            return;
        }

        if (!EmailValidationService.isValid(emailField.getText().trim())) {
            System.out.println("Invalid email");
            return;
        }

        if (!PhoneValidationService.isValid(phoneNumberField.getText().trim())) {
            System.out.println("Invalid phone number");
            return;
        }

        client.setCountry(countryField.getText().trim());
        client.setAddress(addressField.getText().trim());
        client.setPhoneNumber(phoneNumberField.getText().trim());
        client.setCreditCards(Arrays.asList());

        try {
            ClientUserService.addUser(client);
            System.out.println("User successfully added");
        } catch (UserAlreadyExistsException e) {
            System.out.println(e);
        }
    }

    void initData(Client client) {
        this.client = client;
    }
}
