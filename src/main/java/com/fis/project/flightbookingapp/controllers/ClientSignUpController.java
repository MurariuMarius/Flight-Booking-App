package com.fis.project.flightbookingapp.controllers;

import com.fis.project.flightbookingapp.exceptions.UserAlreadyExistsException;
import com.fis.project.flightbookingapp.model.Client;
import com.fis.project.flightbookingapp.services.ClientUserService;
import com.fis.project.flightbookingapp.services.EmailValidationService;
import com.fis.project.flightbookingapp.services.PhoneValidationService;
import com.fis.project.flightbookingapp.services.StageChangeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;

public class ClientSignUpController {

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

            Stage stage = (Stage) emailField.getScene().getWindow();

            FXMLLoader fxmlLoader = StageChangeService.changeScene(
                    stage,
                    "client-menu.fxml",
                    "Welcome " + client.getUsername()
            );

            ClientMenuController clientMenuController = fxmlLoader.getController();
            clientMenuController.initData(client);

        } catch (UserAlreadyExistsException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    void initData(Client client) {
        this.client = client;
    }
    @FXML
    void goBack(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = StageChangeService.changeScene(
                stage,
                "login.fxml",
                "Login"
        );
    }
}
