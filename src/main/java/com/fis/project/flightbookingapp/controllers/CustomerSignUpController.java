package com.fis.project.flightbookingapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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

    @FXML
    void createCustomerAccount() {
        if (emailField.getText().isBlank() || addressField.getText().isBlank() || countryField.getText().isBlank() ||
        phoneNumberField.getText().isBlank()) {
            // TODO Alert user for missing field
            return;
        }
        // TODO Insert new user into db
    }
}
