package com.fis.project.flightbookingapp.controllers;

import com.fis.project.flightbookingapp.addUpdateRemoveApplication;
import com.fis.project.flightbookingapp.exceptions.FlightAlreadyExistsException;
import com.fis.project.flightbookingapp.exceptions.NotInDatabaseException;
import com.fis.project.flightbookingapp.model.Airport;
import com.fis.project.flightbookingapp.model.Flight;
import com.fis.project.flightbookingapp.services.AirportService;
import com.fis.project.flightbookingapp.services.FlightService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class addFlightsController {

    public addFlightsController() {

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

        Flight flight = new Flight(f1,f2,departureAirport,arrivalAirport,operatingWeekDays, LocalTime.parse(f5),LocalTime.parse(f6),50);

        FlightService.addFlight(flight);
        addUpdateRemoveApplication m = new addUpdateRemoveApplication();
        m.changeScene("add_update_remove.fxml");

    }

}