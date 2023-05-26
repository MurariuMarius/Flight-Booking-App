package com.fis.project.flightbookingapp.controllers;

import com.fis.project.flightbookingapp.model.Flight;
import com.fis.project.flightbookingapp.services.FlightService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class DeleteFlightController implements Initializable {

    @FXML
    private ListView<Flight> listOfFlights;
    @Override
    public void initialize(URL arg0, ResourceBundle arg1)
    {
        listOfFlights.getItems().addAll(FlightService.getFlights());
    }

    @FXML
    void removeFlight(MouseEvent event) {

        String selectedFlight = listOfFlights.getSelectionModel().getSelectedItem().getFlightNumber();
        int selectedID = listOfFlights.getSelectionModel().getSelectedIndex();
        FlightService.removeFlight(selectedFlight);
        listOfFlights.getItems().remove(selectedID);
    }

}
