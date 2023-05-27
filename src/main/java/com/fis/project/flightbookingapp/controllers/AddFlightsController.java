package com.fis.project.flightbookingapp.controllers;

import com.fis.project.flightbookingapp.exceptions.FlightAlreadyExistsException;
import com.fis.project.flightbookingapp.exceptions.NotInDatabaseException;
import com.fis.project.flightbookingapp.model.Airport;
import com.fis.project.flightbookingapp.model.Flight;
import com.fis.project.flightbookingapp.services.AirportService;
import com.fis.project.flightbookingapp.services.FlightService;
import com.fis.project.flightbookingapp.services.StageChangeService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AddFlightsController {

    public AddFlightsController() {

    }

    @FXML
    private Button button;
    @FXML
    private TextField field1,field2,field3,field4,field5,field6;
    @FXML
    private CheckBox c1,c2,c3,c4,c5,c6,c7;

    @FXML
    public void addFlight(ActionEvent actionEvent) throws IOException, NotInDatabaseException, FlightAlreadyExistsException {
        add_f();
    }
    @FXML
    public void add_f() throws IOException, NotInDatabaseException, FlightAlreadyExistsException{
    //save the flight data
      Airport departureAirport, arrivalAirport;
        List<DayOfWeek> operatingWeekDays = new ArrayList<>();
        String f1 = field1.getText().toString();
        String f2 = field2.getText().toString();
        String f3 = field3.getText().toString();
        departureAirport = AirportService.getAirportByCode(f3);
        String f4 = field4.getText().toString();
        arrivalAirport = AirportService.getAirportByCode(f4);
        String f5 = field5.getText().toString();
        String f6 = field6.getText().toString();

        if(c1.isSelected())  operatingWeekDays.add(DayOfWeek.MONDAY);
        if(c2.isSelected())  operatingWeekDays.add(DayOfWeek.TUESDAY);
        if(c3.isSelected())  operatingWeekDays.add(DayOfWeek.WEDNESDAY);
        if(c4.isSelected())  operatingWeekDays.add(DayOfWeek.THURSDAY);
        if(c5.isSelected())  operatingWeekDays.add(DayOfWeek.FRIDAY);
        if(c6.isSelected())  operatingWeekDays.add(DayOfWeek.SATURDAY);
        if(c7.isSelected())  operatingWeekDays.add(DayOfWeek.SUNDAY);

        Flight flight = new Flight(f1,f2,departureAirport,arrivalAirport,operatingWeekDays, LocalTime.parse(f5),LocalTime.parse(f6),0);

        FlightService.addFlight(flight);

        Stage stage = (Stage) field1.getScene().getWindow();

        StageChangeService.changeScene(
                stage,
                "airline-menu.fxml",
                "Airline Menu"
        );
    }

}