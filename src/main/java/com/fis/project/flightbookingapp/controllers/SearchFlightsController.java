package com.fis.project.flightbookingapp.controllers;

import com.fis.project.flightbookingapp.exceptions.NotInDatabaseException;
import com.fis.project.flightbookingapp.model.Airport;
import com.fis.project.flightbookingapp.model.Booking;
import com.fis.project.flightbookingapp.model.Client;
import com.fis.project.flightbookingapp.model.Flight;
import com.fis.project.flightbookingapp.services.AirportService;
import com.fis.project.flightbookingapp.services.FlightService;
import com.fis.project.flightbookingapp.services.StageChangeService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValueBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
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
    private DatePicker outboundFlightDatePicker;

    @FXML
    private DatePicker inboundFlightDatePicker;

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

    @FXML
    private Button goBackButton;

    private static final int searchResultLimit = 5;
    private Airport departureAirport = null;
    private Airport arrivalAirport = null;
    private LocalDate outboundFlightDate = null;
    private LocalDate inboundFlightDate = null;
    private Client client = null;
    private Flight outboundFlight = null;
    private Flight inboundFlight = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        outboundFlightDatePicker.setShowWeekNumbers(false);
        outboundFlightDatePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0 );
            }
        });

        inboundFlightDatePicker.setShowWeekNumbers(false);
        inboundFlightDatePicker.setDayCellFactory(picker -> new DateCell() {
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

        outboundFlightTableView.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) ->
                outboundFlight = newValue
        ));

        inboundFlightTableView.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) ->
                inboundFlight = newValue
        ));

        goBackButton.setVisible(false);
    }

    public void setClient(Client client) {
        this.client = client;

        if (client != null) {
            goBackButton.setVisible(true);
        }
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
        outboundFlightDate = outboundFlightDatePicker.getValue();

        inboundFlightDatePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(outboundFlightDate) < 0);
            }
        });

        if (inboundFlightDate != null && outboundFlightDate.compareTo(inboundFlightDate) > 0) {
            inboundFlightDate = outboundFlightDate;
            inboundFlightDatePicker.setValue(outboundFlightDate);
        }
    }

    @FXML
    public void selectedArrivalDate() {
        inboundFlightDate = inboundFlightDatePicker.getValue();
        outboundFlightDatePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(inboundFlightDate) > 0 || date.compareTo(today) < 0);
            }
        });

        if (outboundFlightDate != null && outboundFlightDate.compareTo(inboundFlightDate) > 0) {
            outboundFlightDate = inboundFlightDate;
            outboundFlightDatePicker.setValue(inboundFlightDate);
        }
    }

    @FXML
    public void oneWayFlightSelected() {
        inboundFlightDatePicker.setVisible(!oneWayCheckBox.isSelected());
        inboundFlightDate = null;
        inboundFlight = null;
    }

    @FXML
    void searchFlights() {

        if (departureAirport != null && arrivalAirport != null && outboundFlightDate != null &&
                (inboundFlightDate != null || oneWayCheckBox.isSelected())) {

            if (client != null) {
                bookFlightsButton.setVisible(true);
            }

            outboundFlightSearchDate.setText(outboundFlightDate.toString());

            setOutboundFlightFieldsVisibility(true);

            List<Flight> outboundFlights = FlightService.getFlightsOnRoute(
                    departureAirport,
                    arrivalAirport,
                    outboundFlightDate.getDayOfWeek()
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

                inboundFlightSearchDate.setText(inboundFlightDate.toString());

                List<Flight> inboundFlights = FlightService.getFlightsOnRoute(
                        arrivalAirport,
                        departureAirport,
                        inboundFlightDate.getDayOfWeek()
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
        inboundFlightDate = inboundFlightDate.plusDays(1);
        searchFlights();
    }

    @FXML
    void inboundFlightSearchPreviousDay(ActionEvent event) {
        if (inboundFlightDate.compareTo(outboundFlightDate) > 0 && inboundFlightDate.compareTo(LocalDate.now()) > 0) {
            inboundFlightDate = inboundFlightDate.minusDays(1);
            searchFlights();
        }
    }

    @FXML
    void outboundFlightSearchNextDay(ActionEvent event) {
        if (outboundFlightDate.compareTo(inboundFlightDate) < 0) {
            outboundFlightDate = outboundFlightDate.plusDays(1);
            searchFlights();
        }
    }

    @FXML
    void outboundFlightSearchPreviousDay(ActionEvent event) {
        if (outboundFlightDate.compareTo(LocalDate.now()) > 0) {
            outboundFlightDate = outboundFlightDate.minusDays(1);
            searchFlights();
        }
    }

    @FXML
    public void bookFlights(ActionEvent event) throws IOException {
        if (outboundFlightDate.compareTo(inboundFlightDate) > 0) {
            System.out.println("Invalid trip dates");
            return;
        }

        if (inboundFlight != null && outboundFlightDate.equals(outboundFlightDate) &&
                inboundFlight.getDepartureTime().compareTo(outboundFlight.getArrivalTime()) < 0) {
            System.out.println("Return flight before outbound flight");
            return;
        }

        System.out.println("Bookable flight");

        List<Booking> bookings = new ArrayList<>();

        Booking outboundFlightBooking = new Booking(
                client.getUsername(),
                outboundFlight,
                outboundFlightDate,
                Set.of(client.getUsername())
        );

        bookings.add(outboundFlightBooking);

        if (!oneWayCheckBox.isSelected()) {
            Booking inboundFlightBooking = new Booking(
                    client.getUsername(),
                    inboundFlight,
                    inboundFlightDate,
                    Set.of(client.getUsername())
            );
            bookings.add(inboundFlightBooking);
        }

        Stage stage = (Stage) bookFlightsButton.getScene().getWindow();
        FXMLLoader fxmlLoader = StageChangeService.changeScene(
                stage,
                "book_flight.fxml",
                "Book flight"
        );
        BookFlightController bookFlightController = fxmlLoader.getController();
        bookFlightController.setData(client, bookings, inboundFlight.getPrice() + outboundFlight.getPrice());
    }
    @FXML
    void goBack(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = StageChangeService.changeScene(
                stage,
                "client-menu.fxml",
                "Menu"
        );
    }
}
