package com.fis.project.flightbookingapp.controllers;

import com.fis.project.flightbookingapp.Main;
import com.fis.project.flightbookingapp.exceptions.InvalidCredentialsException;
import com.fis.project.flightbookingapp.services.AirlineUserService;
import com.fis.project.flightbookingapp.services.ClientUserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button loginButton;

    @FXML
    private TextField passwordField;

    @FXML
    private Button signupButton;

    @FXML
    private TextField usernameField;

    @FXML
    private Text titleText;

    @FXML
    private Button loginAsAirlineButton;

    private boolean logInAsAirline = false;

    @FXML
    void goToSignupPage() {
        try {
            Stage stage = (Stage) signupButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("signup.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle("Flight Booking App - Log In");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void login() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        try {
            if (logInAsAirline) {
                AirlineUserService.checkCredentials(username, password);
            } else {
                ClientUserService.checkCredentials(username, password);
            }

            System.out.println("Logged in successfully as " + username);

            // TODO Redirect to home page based on role

        } catch (InvalidCredentialsException e) {
            System.out.println(e);
        }
    }

    public void logInAsAirline() {
        logInAsAirline = true;
        loginAsAirlineButton.setVisible(false);
        titleText.setText("Airline Log In");
    }
}