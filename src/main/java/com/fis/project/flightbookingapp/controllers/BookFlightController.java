package com.fis.project.flightbookingapp.controllers;

import com.fis.project.flightbookingapp.model.Booking;
import com.fis.project.flightbookingapp.model.Client;
import com.fis.project.flightbookingapp.services.StageChangeService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
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
    private Client client;
    public void initialize(URL arg0, ResourceBundle arg1){
        food.getItems().addAll("None","Pasta","Mexican food","Vegan","Vegetarian");
    }

    @FXML
    void goBack(MouseEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = StageChangeService.changeScene(
                stage,
                "search-flights.fxml",
                "Search flights"
        );
        SearchFlightsController searchFlightsController = fxmlLoader.getController();
        searchFlightsController.setClient(client);
    }
    public void setData(Client client, List<Booking> bookings, double price){
        this.client = client;
        this.bookings = bookings;
        this.price = price;
    }

    @FXML
    void goPay(MouseEvent event) {
        for (Booking booking : bookings) {
            booking.setSeatNumber(field1.getText().toString());
            if (priority.isSelected()) {
                price *= 1.05;
                booking.setHasPriorityBoarding(true);
            }
            else
                booking.setHasPriorityBoarding(false);

            Set<String> traveller = new HashSet<>();
            traveller.add(field2.getText().toString());
            booking.setTravellers(traveller);
            if (food.getValue().equals("None")) {
                booking.setFoodOption("");
            } else {
                booking.setFoodOption(food.getValue());
                price *= 1.05;
            }
        }
        System.out.println(bookings);
    }


}
