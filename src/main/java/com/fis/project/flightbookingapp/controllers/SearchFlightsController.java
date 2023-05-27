package com.fis.project.flightbookingapp.controllers;

import com.fis.project.flightbookingapp.exceptions.NotInDatabaseException;
import com.fis.project.flightbookingapp.model.Airport;
import com.fis.project.flightbookingapp.model.Flight;
import com.fis.project.flightbookingapp.services.AirportService;
import com.fis.project.flightbookingapp.services.FlightService;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
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
    private Button outboundFlightSearchPreviousDayButton;

    @FXML
    private Text outboundFlightSearchDate;

    @FXML
    private Button outboundFlightSearchNextDayButton;

    @FXML
    private TableView<Flight> outboundFlightTableView;

    @FXML
    private TableColumn<Flight, String> outboundFlightNumberColumn;

    @FXML
    private TableColumn<Flight, String> outboundAirlineCodeColumn;

    @FXML
    private TableColumn<Flight, String> outboundDepartureTimeColumn;

    @FXML
    private TableColumn<Flight, String> outboundArrivalTimeColumn;

    @FXML
    private TableColumn<String, String> outboundFlightDurationColumn;

    @FXML
    private TableColumn<Flight, Double> outboundFlightPriceColumn;

    @FXML
    private TableColumn<Flight, String> inboundFlightNumberColumn;

    @FXML
    private TableColumn<Flight, String> inboundAirlineCodeColumn;

    @FXML
    private TableColumn<Flight, String> inboundDepartureTimeColumn;

    @FXML
    private TableColumn<Flight, String> inboundArrivalTimeColumn;

    @FXML
    private TableColumn<Flight, Double> inboundFlightPriceColumn;

    @FXML
    private TableColumn<String, String> inboundFlightDurationColumn;

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

        arrivalDatePicker.setShowWeekNumbers(false);
        arrivalDatePicker.setDayCellFactory(picker -> new DateCell() {
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

        outboundAirlineCodeColumn.setCellValueFactory(new PropertyValueFactory<>("airlineCode"));
        outboundFlightNumberColumn.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));
        outboundFlightPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        inboundAirlineCodeColumn.setCellValueFactory(new PropertyValueFactory<>("airlineCode"));
        inboundDepartureTimeColumn.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        inboundArrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        inboundFlightNumberColumn.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));
        inboundFlightPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // TODO Hide table fields

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

        arrivalDatePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(departureDate) < 0);
            }
        });

        if (arrivalDate != null && departureDate.compareTo(arrivalDate) > 0) {
            arrivalDate = departureDate;
            arrivalDatePicker.setValue(departureDate);
        }
    }

    @FXML
    public void selectedArrivalDate() {
        arrivalDate = arrivalDatePicker.getValue();
        departureDatePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(arrivalDate) > 0 || date.compareTo(today) < 0);
            }
        });

        if (departureDate != null && departureDate.compareTo(arrivalDate) > 0) {
            departureDate = arrivalDate;
            departureDatePicker.setValue(arrivalDate);
        }
    }

    @FXML
    public void oneWayFlightSelected() {
        arrivalDatePicker.setVisible(!oneWayCheckBox.isSelected());
    }

    @FXML
    void searchFlights(ActionEvent event) {
        System.out.println(departureDate.getDayOfWeek());
        List<Flight> outboundFlights = FlightService.getFlightsOnRoute(
                departureAirport,
                arrivalAirport,
                departureDate.getDayOfWeek()
        );
        System.out.println(outboundFlights);
        outboundFlightTableView.getItems().clear();
        outboundFlightTableView.getItems().addAll(outboundFlights);

        for (Flight f : outboundFlightTableView.getItems()) {
            outboundFlightDurationColumn.setCellValueFactory(
                    c -> new SimpleStringProperty(f.getFlightDuration())
            );
            outboundDepartureTimeColumn.setCellValueFactory(
                    c -> new SimpleStringProperty(LocalTime.from(f.getDepartureTime()).toString())
            );
            outboundArrivalTimeColumn.setCellValueFactory(
                    c -> new SimpleStringProperty(LocalTime.from(f.getArrivalTime()).toString())
            );
        }

        if (!oneWayCheckBox.isSelected()) {
            // TODO Unhide fields

            List<Flight> inboundFlights = FlightService.getFlightsOnRoute(
                    arrivalAirport,
                    departureAirport,
                    arrivalDate.getDayOfWeek()
            );
            System.out.println(inboundFlights);
            inboundFlightTableView.getItems().clear();
            inboundFlightTableView.getItems().addAll(inboundFlights);

            for (Flight f : inboundFlightTableView.getItems()) {
                inboundFlightDurationColumn.setCellValueFactory(
                        c -> new SimpleStringProperty(f.getFlightDuration())
                );
                inboundDepartureTimeColumn.setCellValueFactory(
                        c -> new SimpleStringProperty(LocalTime.from(f.getDepartureTime()).toString())
                );
                inboundArrivalTimeColumn.setCellValueFactory(
                        c -> new SimpleStringProperty(LocalTime.from(f.getArrivalTime()).toString())
                );
            }
        }


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
