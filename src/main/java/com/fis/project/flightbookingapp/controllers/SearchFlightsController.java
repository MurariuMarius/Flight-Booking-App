package com.fis.project.flightbookingapp.controllers;

import com.fis.project.flightbookingapp.exceptions.NotInDatabaseException;
import com.fis.project.flightbookingapp.model.Airport;
import com.fis.project.flightbookingapp.model.Flight;
import com.fis.project.flightbookingapp.services.AirportService;
import com.fis.project.flightbookingapp.services.FlightService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class SearchFlightsController implements Initializable {

    @FXML
    private ComboBox<String> departureAirportComboBox;

    @FXML
    private ComboBox<String> arrivalAirportComboBox;

    @FXML
    private DatePicker departureDatePicker;

    @FXML
    private DatePicker arrivalDatePicker;

    @FXML
    private CheckBox oneWayCheckBox;

    @FXML
    private Button flightSearchButton;

    @FXML
    private Button inboudFlightSearchNextDayButton;

    @FXML
    private Button inboudFlightSearchPreviousDayButton;

    @FXML
    private Text inboundFlightSearchDate;

    @FXML
    private TableView<Flight> inboundFlightTableView;

    @FXML
    private Button outboudFlightSearchPreviousDayButton;

    @FXML
    private Text outboundFlightSearchDate;

    @FXML
    private Button outboundFlightSearchNextDayButton;

    @FXML
    private TableView<Flight> outboundFlightTableView;

    @FXML
    private TableColumn<Flight, String> outboundFlightNumber;

    @FXML
    private TableColumn<Flight, String> outboundAirlineCode;

    @FXML
    private TableColumn<Flight, String> outboundDepartureTime; // TODO Translate to local airport time

    @FXML
    private TableColumn<Flight, String> outboundArrivalTime; // TODO Translate to local airport time

    @FXML
    private TableColumn<Flight, Double> outboundFlightPrice; // TODO Create field in model

    @FXML
    private TableColumn<Flight, String> inBoundFlightNumber;

    @FXML
    private TableColumn<Flight, String> inBoundAirlineCode;

    @FXML
    private TableColumn<Flight, String> inBoundDepartureTime; // TODO Translate to local airport time

    @FXML
    private TableColumn<Flight, String> inBoundArrivalTime; // TODO Translate to local airport time

    @FXML
    private TableColumn<Flight, Double> inBoundFlightPrice; // TODO Create field in model

    private static final int searchResultLimit = 5;
    private Airport departureAirport = null;
    private Airport arrivalAirport = null;

    private LocalDate departureDate = null;
    private LocalDate arrivalDate = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        departureDatePicker.setShowWeekNumbers(false);
        departureDatePicker.setDayCellFactory(picker -> new DateCell() {
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

    @FXML
    public void selectedDepartureAirport() {
        try {
            departureAirport = AirportService.getAirportByName(departureAirportComboBox.getValue());
            System.out.println(departureAirport);
        } catch (NotInDatabaseException e) {
            System.out.println(e);
        }
    }

    @FXML
    public void selectedArrivalAirport() {
        try {
            arrivalAirport = AirportService.getAirportByName(arrivalAirportComboBox.getValue());
            System.out.println(arrivalAirport);
        } catch (NotInDatabaseException e) {
            System.out.println(e);
        }
    }

    @FXML
    public void selectedDepartureDate() {
        departureDate = departureDatePicker.getValue();
        System.out.println(departureDate);
    }

    @FXML
    public void selectedArrivalDate() {
        arrivalDate = arrivalDatePicker.getValue();
        System.out.println(arrivalDate);
    }

    @FXML
    public void oneWayFlightSelected() {
        arrivalDatePicker.setVisible(!oneWayCheckBox.isSelected());
    }

    @FXML
    void searchFlights(ActionEvent event) {
        System.out.println(departureDate.getDayOfWeek());
        List<Flight> flights = FlightService.getFlightsOnRoute(
                departureAirport,
                arrivalAirport,
                departureDate.getDayOfWeek()
        );
        System.out.println(flights);
    }

    @FXML
    void inboundFlightSearchNextDay(ActionEvent event) {

    }

    @FXML
    void inboundFlightSearchPreviousDay(ActionEvent event) {

    }

    @FXML
    void outboundFlightSearchNextDay(ActionEvent event) {

    }

    @FXML
    void outboundFlightSearchPreviousDay(ActionEvent event) {

    }

}
