package com.fis.project.flightbookingapp.controllers;

import com.fis.project.flightbookingapp.model.Booking;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.*;

public class BookFlightController implements Initializable {

    @FXML
    private TextField field1;
    @FXML
    private TextField field2;

    @FXML
    private ComboBox<String> food;

    @FXML
    private CheckBox priority;
    private List<Booking> bookings;
    private double price;
    public void initialize(URL arg0, ResourceBundle arg1){
        food.getItems().addAll("Pasta","Mexican food","Vegan","Vegetarian");
    }

    @FXML
    void goBack(MouseEvent event) {

    }
    public void setBooking(List<Booking> bookings, double price){
        this.bookings =bookings;
        this.price = price;
    }

    @FXML
    void goPay(MouseEvent event) {
        for (Booking booking : bookings) {
            booking.setSeatNumber(field1.getText().toString());
            if (priority.isSelected())
                booking.setHasPriorityBoarding(true);
            else
                booking.setHasPriorityBoarding(false);

            Set<String> traveller = new HashSet<>();
            traveller.add(field2.getText().toString());
            booking.setTravellers(traveller);
            booking.setFoodOption(food.getValue());
        }
        System.out.println(bookings);
    }


}
