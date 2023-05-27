package com.fis.project.flightbookingapp.controllers;

import com.fis.project.flightbookingapp.model.Booking;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class BookFlightController extends Initializable {

    @FXML
    private TextField field1;

    @FXML
    private ComboBox<String> food;

    @FXML
    private CheckBox priority;
    private Booking booking;
    public void initialize(URL arg0, ResourceBundle arg1){
        food.getItems().addAll("Pasta","Mexican food","Vegan","Vegetarian");
    }

    @FXML
    void goBack(MouseEvent event) {

    }
    void setBooking(Booking booking){
        this.booking=booking;
    }

    @FXML
    void goPay(MouseEvent event) {
        booking.setfield1.getText().toString()
    }


}
