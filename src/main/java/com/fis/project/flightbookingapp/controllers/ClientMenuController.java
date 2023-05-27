package com.fis.project.flightbookingapp.controllers;

import com.fis.project.flightbookingapp.model.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

public class ClientMenuController {

    @FXML
    private Button buttonManageBookings;

    @FXML
    private Button buttonSearchFlights;

    @FXML
    private Button buttonViewBookings;

    private Client client;

    void initData(Client client) {
        this.client = client;
    }

    @FXML
    void goManageBookings(ActionEvent event) {
        // TODO
    }

    @FXML
    void goSearchFlights(ActionEvent event) {
        // TODO
    }

    @FXML
    void goViewBookings(ActionEvent event) {
        // TODO
    }

}
