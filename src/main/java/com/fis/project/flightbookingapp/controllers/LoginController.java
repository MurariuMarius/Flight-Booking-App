package com.fis.project.flightbookingapp.controllers;

import com.fis.project.flightbookingapp.Main;
import com.fis.project.flightbookingapp.exceptions.InvalidCredentialsException;
import com.fis.project.flightbookingapp.model.Airline;
import com.fis.project.flightbookingapp.model.Client;
import com.fis.project.flightbookingapp.services.AirlineUserService;
import com.fis.project.flightbookingapp.services.ClientUserService;
import com.fis.project.flightbookingapp.services.StageChangeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
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
    void login() throws IOException {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        Stage stage = (Stage) signupButton.getScene().getWindow();

        try {
            if (logInAsAirline) {
                Airline airline = AirlineUserService.checkCredentials(username, password);

                FXMLLoader fxmlLoader = StageChangeService.changeScene(
                        stage,
                        "airline-menu.fxml",
                        "Airline Menu"
                );

                AirlineMenuController airlineMenuController = fxmlLoader.getController();
                airlineMenuController.initData(airline);

            } else {
                Client client = ClientUserService.checkCredentials(username, password);

                FXMLLoader fxmlLoader = StageChangeService.changeScene(
                        (Stage) signupButton.getScene().getWindow(),
                        "client-menu.fxml",
                        "Welcome, " + client.getUsername()
                );

                ClientMenuController clientMenuController = fxmlLoader.getController();
                clientMenuController.initData(client);
            }

            System.out.println("Logged in successfully as " + username);

        } catch (InvalidCredentialsException e) {
            System.out.println(e);
        }
    }

    public void logInAsAirline() {
        logInAsAirline = true;
        loginAsAirlineButton.setVisible(false);
        titleText.setText("Airline Log In");
    }
    @FXML
    void goBack(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = StageChangeService.changeScene(
                stage,
                "home.fxml",
                "Home"
        );
    }
}