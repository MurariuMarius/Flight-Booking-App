package com.fis.project.flightbookingapp.controllers;

import com.fis.project.flightbookingapp.exceptions.NotInDatabaseException;
import com.fis.project.flightbookingapp.model.Airport;
import com.fis.project.flightbookingapp.services.AirportService;
import com.fis.project.flightbookingapp.services.FlightService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class SearchFlightsController implements Initializable {

    @FXML
    private ComboBox<String> departureAirportComboBox;

    @FXML
    private ComboBox<String> arrivalAirportComboBox;

    @FXML
    private DatePicker datePicker;

    private static final int searchResultLimit = 5;
    private Airport departureAirport = null;
    private Airport arrivalAirport = null;

    private LocalDate date = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datePicker.setShowWeekNumbers(false);
        datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0 );
            }
        });

        departureAirportComboBox.setEditable(true);
        departureAirportComboBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (departureAirportComboBox.getValue() == null || oldValue.equals("")) {
                departureAirportComboBox.getItems().clear();
                departureAirportComboBox.show();
                List<Airport> airports = AirportService.getAirportSuggestions(newValue);
                System.out.println(airports.stream().limit(searchResultLimit).map(Airport::getCity).distinct().toList());
                departureAirportComboBox.getItems().addAll(
                        airports.stream().limit(searchResultLimit).map(Airport::getAirportName).toList()
                );
            }
        });

        arrivalAirportComboBox.setEditable(true);
        arrivalAirportComboBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (departureAirport != null && (arrivalAirportComboBox.getValue() == null || oldValue.equals(""))) {
                arrivalAirportComboBox.getItems().clear();
                arrivalAirportComboBox.show();
                List<Airport> airports = FlightService.getFlightsByDepartureAirport(departureAirport)
                        .stream()
                        .map(f -> {
                            try {
                                return f.getArrivalAirport();
                            } catch (NotInDatabaseException e) {
                                System.out.println(e);
                            }
                            return null;
                        })
                        .toList();
                arrivalAirportComboBox.getItems().addAll(
                        airports.stream().limit(searchResultLimit).map(Airport::getAirportName).toList()
                );
            }
        });
    }

    public void selectedDepartureAirport() {
        try {
            departureAirport = AirportService.getAirportByName(departureAirportComboBox.getValue());
            System.out.println(departureAirport);
        } catch (NotInDatabaseException e) {
            System.out.println(e);
        }
    }

    public void selectedArrivalAirport() {
        try {
            arrivalAirport = AirportService.getAirportByName(arrivalAirportComboBox.getValue());
            System.out.println(arrivalAirport);
        } catch (NotInDatabaseException e) {
            System.out.println(e);
        }
    }

    public void selectedDate() {
        date = datePicker.getValue();
        System.out.println(date);
    }
}
