package com.fis.project.flightbookingapp.controllers;

import com.fis.project.flightbookingapp.exceptions.UserAlreadyExistsException;
import com.fis.project.flightbookingapp.model.Airline;
import com.fis.project.flightbookingapp.services.AirlineUserService;
import com.fis.project.flightbookingapp.services.EmailValidationService;
import com.fis.project.flightbookingapp.services.PhoneValidationService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AirlineSignupController {

    @FXML
    private TextField icaoCodeField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField countryField;

    @FXML
    private Button createAccountButton;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField airOperatorCertificateField;

    private Airline airline;

    @FXML
    void createAirlineAccount() {
        if (icaoCodeField.getText().isBlank() || addressField.getText().isBlank() || countryField.getText().isBlank() ||
        phoneNumberField.getText().isBlank() || airOperatorCertificateField.getText().isBlank()) {
            System.out.println("Missing fields");
            return;
        }
        if (!EmailValidationService.isValid(emailField.getText())) {
            System.out.println("Invalid email");
            return;
        }

        if (!PhoneValidationService.isValid(phoneNumberField.getText().trim())) {
            System.out.println("Invalid phone number");
            return;
        }

        airline.setCountry(countryField.getText().trim());
        airline.setAddress(addressField.getText().trim());
        airline.setEmail(emailField.getText().trim());
        airline.setPhoneNumber(phoneNumberField.getText().trim());
        airline.setIcaoCode(icaoCodeField.getText());
        airline.setAirOperatorCertificate(airOperatorCertificateField.getText());
        
        try {
            AirlineUserService.addUser(airline);
            System.out.println("Account successfully created");
        } catch (UserAlreadyExistsException e) {
            System.out.println(e);
        }
    }

    void initData(Airline airline) {
        this.airline = airline;
    }
}
