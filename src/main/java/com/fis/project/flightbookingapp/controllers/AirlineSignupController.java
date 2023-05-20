package com.fis.project.flightbookingapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AirlineSignupController {

    @FXML
    private TextField iataCodeField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField countryField;

    @FXML
    private Button createAccountButton;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField airOperatorCertificateField;

    @FXML
    void createAirlineAccount() {
        if (iataCodeField.getText().isBlank() || addressField.getText().isBlank() || countryField.getText().isBlank() ||
        phoneNumberField.getText().isBlank() || airOperatorCertificateField.getText().isBlank()) {
            // TODO Alert user for missing fields
            return;
        }
        // TODO Insert new user in db
    }
}
