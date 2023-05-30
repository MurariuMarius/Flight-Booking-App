package com.fis.project.flightbookingapp;

import com.fis.project.flightbookingapp.exceptions.AirportAlreadyExistsException;
import com.fis.project.flightbookingapp.exceptions.UserAlreadyExistsException;
import com.fis.project.flightbookingapp.model.Client;
import com.fis.project.flightbookingapp.services.AirportGetterService;
import com.fis.project.flightbookingapp.services.AirportService;
import com.fis.project.flightbookingapp.services.ClientUserService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1000,700);
        stage.setTitle("Flight Booking App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws UserAlreadyExistsException, FileNotFoundException, AirportAlreadyExistsException {
        AirportService.addAirports(AirportGetterService.getAirportsFromJSON());
        launch();
    }
}