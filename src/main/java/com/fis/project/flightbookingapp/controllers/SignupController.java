package com.fis.project.flightbookingapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import com.fis.project.flightbookingapp.Main;
import com.fis.project.flightbookingapp.model.Airline;
import com.fis.project.flightbookingapp.model.Client;
import com.fis.project.flightbookingapp.model.User;
import com.fis.project.flightbookingapp.services.PasswordEncodingService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignupController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button signupButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField repeatedPasswordField;

    @FXML
    private ComboBox<String> userRole;

    @FXML
    private TextField usernameField;

    @FXML
    void initialize() {
        userRole.getItems().addAll("Customer", "Airline");
    }

    @FXML
    void continueRegistrationBasedOnRole() {
        String option = userRole.getSelectionModel().getSelectedItem();
        if (option == null || usernameField.getText().isBlank() || passwordField.getText().isBlank() || repeatedPasswordField.getText().isBlank()) {
            // TODO Alert user to fill out all fields
            System.out.println("Alert user to fill out all fields");
            return;
        }
        if (!passwordField.getText().equals(repeatedPasswordField.getText())) {
            // TODO Alert user that passwords don't match
            System.out.println("user that passwords don't match");
            return;
        }

        // TODO Check username not already existent
        String username = usernameField.getText().trim().toLowerCase();
        if (username.contains(" ") || passwordField.getText().contains(" ")) {
            // TODO Invalid input
        }

        FXMLLoader fxmlLoader;
        User user;

        // Pass new User to appropriate controller
        if (userRole.getSelectionModel().getSelectedItem().equals("Customer")) {
            fxmlLoader = new FXMLLoader(Main.class.getResource("customer-signup-form.fxml"));
        } else {
            fxmlLoader = new FXMLLoader(Main.class.getResource("airline-signup-form.fxml"));
        }

        try {
            Stage stage = (Stage) signupButton.getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle("Flight Booking App - "+ option + " Sign Up");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (userRole.getSelectionModel().getSelectedItem().equals("Customer")) {
            user = new Client();
            CustomerSignUpController controller = fxmlLoader.getController();
            controller.initData((Client) user);
        } else {
            user = new Airline();
            AirlineSignupController controller = fxmlLoader.getController();
            controller.initData((Airline) user);
        }

        user.setUsername(username);
        user.setPassword(PasswordEncodingService.encodePassword(username,passwordField.getText()));
    }

}
