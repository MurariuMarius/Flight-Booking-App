package com.fis.project.flightbookingapp.controllers;

import com.fis.project.flightbookingapp.exceptions.NotInDatabaseException;
import com.fis.project.flightbookingapp.model.Airport;
import com.fis.project.flightbookingapp.model.Client;
import com.fis.project.flightbookingapp.model.Flight;
import com.fis.project.flightbookingapp.services.AirportService;
import com.fis.project.flightbookingapp.services.FlightService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValueBase;
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
    private Button inboundFlightSearchNextDayButton;

    @FXML
    private Button inboundFlightSearchPreviousDayButton;

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
    private Text outboundFlightText;
    
    @FXML
    private Text inboundFlightText;

    @FXML
    private TableView<Flight> outboundFlightTableView;

    @FXML
    private TableColumn<Flight, String> outboundFlightNumberColumn;

    @FXML
    private TableColumn<Flight, String> outboundAirlineCodeColumn;


    private TableColumn<Flight, String> outboundDepartureTimeColumn;

    private TableColumn<Flight, String> outboundArrivalTimeColumn;


    private TableColumn<Flight, String> outboundFlightDurationColumn;


    private TableColumn<Flight, Double> outboundFlightPriceColumn;

    @FXML
    private TableColumn<Flight, String> inboundFlightNumberColumn;

    @FXML
    private TableColumn<Flight, String> inboundAirlineCodeColumn;


    private TableColumn<Flight, String> inboundDepartureTimeColumn;


    private TableColumn<Flight, String> inboundArrivalTimeColumn;


    private TableColumn<Flight, Double> inboundFlightPriceColumn;


    private TableColumn<Flight, String> inboundFlightDurationColumn;

    @FXML
    private Button bookFlightsButton;

    private static final int searchResultLimit = 5;
    private Airport departureAirport = null;
    private Airport arrivalAirport = null;
    private LocalDate departureDate = null;
    private LocalDate arrivalDate = null;
    private Client client = null;
    private Flight outboundFlight = null;
    private Flight inboundFlight = null;

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

        setOutboundFlightFieldsVisibility(false);
        setInboundFlightFieldsVisibility(false);
        bookFlightsButton.setVisible(false);

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
                        .distinct()
                        .toList();
                arrivalAirportComboBox.getItems().addAll(
                        airports.stream().limit(searchResultLimit).map(Airport::getAirportName).toList()
                );
            }
        });

        outboundAirlineCodeColumn.setCellValueFactory(new PropertyValueFactory<>("airlineCode"));
        outboundFlightNumberColumn.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));

        inboundAirlineCodeColumn.setCellValueFactory(new PropertyValueFactory<>("airlineCode"));
        inboundFlightNumberColumn.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));

    }

    public void setClient(Client client) {
        this.client = client;
    }

    private void setOutboundFlightFieldsVisibility(boolean visible) {
        outboundFlightTableView.setVisible(visible);
        outboundFlightSearchNextDayButton.setVisible(visible);
        outboundFlightSearchDate.setVisible(visible);
        outboundFlightSearchPreviousDayButton.setVisible(visible);
        outboundFlightText.setVisible(visible);
    }

    private void setInboundFlightFieldsVisibility(boolean visibile) {
        inboundFlightTableView.setVisible(visibile);
        inboundFlightSearchNextDayButton.setVisible(visibile);
        inboundFlightSearchPreviousDayButton.setVisible(visibile);
        inboundFlightSearchDate.setVisible(visibile);
        inboundFlightText.setVisible(visibile);
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
        arrivalDate = null;
        inboundFlight = null;
    }

    @FXML
    void searchFlights(ActionEvent event) {

        if (departureAirport != null && arrivalAirport != null && departureDate != null &&
                (arrivalDate != null || oneWayCheckBox.isSelected())) {

            if (client != null) {
                bookFlightsButton.setVisible(true);
            }

            setOutboundFlightFieldsVisibility(true);

            List<Flight> outboundFlights = FlightService.getFlightsOnRoute(
                    departureAirport,
                    arrivalAirport,
                    departureDate.getDayOfWeek()
            );
            System.out.println(outboundFlights);
            outboundFlightTableView.getColumns().removeAll(
                    outboundDepartureTimeColumn, outboundArrivalTimeColumn,
                    outboundFlightDurationColumn, outboundFlightPriceColumn
            );
            outboundFlightTableView.getItems().clear();
            outboundFlightTableView.getItems().addAll(outboundFlights);

            outboundDepartureTimeColumn = new TableColumn<>("DEP");
            outboundDepartureTimeColumn.setCellValueFactory(c ->
                    new SimpleStringProperty(LocalTime.from(c.getValue().getDepartureTime()).toString())
            );

            outboundArrivalTimeColumn = new TableColumn<>("ARR");
            outboundArrivalTimeColumn.setCellValueFactory(c ->
                    new SimpleStringProperty(LocalTime.from(c.getValue().getArrivalTime()).toString()));

            outboundFlightDurationColumn = new TableColumn<>("Duration");
            outboundFlightDurationColumn.setCellValueFactory(c ->
                    new SimpleStringProperty(c.getValue().getFlightDuration()));

            outboundFlightPriceColumn = new TableColumn<>("Price");
            outboundFlightPriceColumn.setCellValueFactory(c -> new ObservableValueBase<Double>() {
                @Override
                public Double getValue() {
                    return c.getValue().getPrice();
                }
            });

            outboundFlightTableView.getColumns().addAll(outboundDepartureTimeColumn, outboundArrivalTimeColumn,
                    outboundFlightDurationColumn, outboundFlightPriceColumn);


            if (!oneWayCheckBox.isSelected()) {
                setInboundFlightFieldsVisibility(true);

                List<Flight> inboundFlights = FlightService.getFlightsOnRoute(
                        arrivalAirport,
                        departureAirport,
                        arrivalDate.getDayOfWeek()
                );
                System.out.println(inboundFlights);
                inboundFlightTableView.getColumns().removeAll(
                        inboundDepartureTimeColumn, inboundArrivalTimeColumn,
                        inboundFlightDurationColumn, inboundFlightPriceColumn
                );
                inboundFlightTableView.getItems().clear();
                inboundFlightTableView.getItems().addAll(inboundFlights);

                inboundDepartureTimeColumn = new TableColumn<>("DEP");
                inboundDepartureTimeColumn.setCellValueFactory(c ->
                        new SimpleStringProperty(LocalTime.from(c.getValue().getDepartureTime()).toString())
                );

                inboundArrivalTimeColumn = new TableColumn<>("ARR");
                inboundArrivalTimeColumn.setCellValueFactory(c ->
                        new SimpleStringProperty(LocalTime.from(c.getValue().getArrivalTime()).toString()));

                inboundFlightDurationColumn = new TableColumn<>("Duration");
                inboundFlightDurationColumn.setCellValueFactory(c ->
                        new SimpleStringProperty(c.getValue().getFlightDuration()));

                inboundFlightPriceColumn = new TableColumn<>("Price");
                inboundFlightPriceColumn.setCellValueFactory(c -> new ObservableValueBase<Double>() {
                    @Override
                    public Double getValue() {
                        return c.getValue().getPrice();
                    }
                });

                inboundFlightTableView.getColumns().addAll(inboundDepartureTimeColumn, inboundArrivalTimeColumn,
                        inboundFlightDurationColumn, inboundFlightPriceColumn);

                }
            } else {
                setInboundFlightFieldsVisibility(false);
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

    @FXML
    public void bookFlights(ActionEvent event) {
        if (departureDate.compareTo(arrivalDate) > 0) {
            System.out.println("Invalid trip dates");
            return;
        }

        if (outboundFlightTableView.getItems().stream().count() != 1 ||
                (inboundFlightTableView.getItems().stream().count() != 1 && !oneWayCheckBox.isSelected())) {
            System.out.println("Select only one flight per leg");
            return;
        }

        outboundFlight = outboundFlightTableView.getItems().get(0);
        if (!oneWayCheckBox.isSelected()) {
            inboundFlight = inboundFlightTableView.getItems().get(0);
        }

        if (inboundFlight != null && departureDate.equals(departureDate) &&
                inboundFlight.getDepartureTime().compareTo(outboundFlight.getArrivalTime()) < 0) {
            System.out.println("Return flight before outbound flight");
            return;
        }

        System.out.println("Bookable flight");
        // TODO Redirect to booking page
    }
}
