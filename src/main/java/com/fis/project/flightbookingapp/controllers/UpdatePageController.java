package com.fis.project.flightbookingapp.controllers;

import com.fis.project.flightbookingapp.exceptions.FlightAlreadyExistsException;
import com.fis.project.flightbookingapp.exceptions.NotInDatabaseException;
import com.fis.project.flightbookingapp.model.Airport;
import com.fis.project.flightbookingapp.model.Flight;
import com.fis.project.flightbookingapp.services.AirportService;
import com.fis.project.flightbookingapp.services.FlightService;
import com.fis.project.flightbookingapp.services.StageChangeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class UpdatePageController {

    @FXML
    private Button button;

    @FXML
    private CheckBox c1;

    @FXML
    private CheckBox c2;

    @FXML
    private CheckBox c3;

    @FXML
    private CheckBox c4;

    @FXML
    private CheckBox c5;

    @FXML
    private CheckBox c6;

    @FXML
    private CheckBox c7;

    @FXML
    private TextField field2;

    @FXML
    private TextField field3;

    @FXML
    private TextField field4;

    @FXML
    private TextField field5;

    @FXML
    private TextField field6;
    private Flight flight;

    @FXML
    void goUpdate(ActionEvent event) throws NotInDatabaseException , FlightAlreadyExistsException, IOException {
        Airport departureAirport, arrivalAirport;
        List<DayOfWeek> operatingWeekDays = new ArrayList<>();


        if(!field2.getText().isEmpty())
        {
            String f2 = field2.getText().toString();
            flight.setAirlineCode(f2);
        }
        if(!field3.getText().isEmpty()) {
            String f3 = field3.getText().toString();
            flight.setDepartureAirport(AirportService.getAirportByCode(f3));
        }
        if(!field4.getText().isEmpty()) {
            String f4 = field4.getText().toString();
            flight.setArrivalAirport(AirportService.getAirportByCode(f4));
        }
        if(!field5.getText().isEmpty()) {
            String f5 = field5.getText().toString();
            flight.setDepartureTime(LocalTime.parse(f5));
        }
        if(!field6.getText().isEmpty()) {
            String f6 = field6.getText().toString();
            flight.setArrivalTime(LocalTime.parse(f6));
        }

        if(c1.isSelected())  operatingWeekDays.add(DayOfWeek.MONDAY);
        if(c2.isSelected())  operatingWeekDays.add(DayOfWeek.TUESDAY);
        if(c3.isSelected())  operatingWeekDays.add(DayOfWeek.WEDNESDAY);
        if(c4.isSelected())  operatingWeekDays.add(DayOfWeek.THURSDAY);
        if(c5.isSelected())  operatingWeekDays.add(DayOfWeek.FRIDAY);
        if(c6.isSelected())  operatingWeekDays.add(DayOfWeek.SATURDAY);
        if(c7.isSelected())  operatingWeekDays.add(DayOfWeek.SUNDAY);

        if(!operatingWeekDays.isEmpty()) flight.setOperatingWeekDays(operatingWeekDays);

        FlightService.updateFlight(flight);

        Stage stage = (Stage) field2.getScene().getWindow();

        StageChangeService.changeScene(
                stage,
                "airline-menu.fxml",
                "Airline Menu"
        );
    }
    void updateFlight(Flight flight){
        this.flight = flight;
    }
}
