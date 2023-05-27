package com.fis.project.flightbookingapp.controllers;

import com.fis.project.flightbookingapp.model.Client;
import com.fis.project.flightbookingapp.services.StageChangeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

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
    void goSearchFlights(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = StageChangeService.changeScene(
                stage,
                "search-flights.fxml",
                "Search flights"
        );
        SearchFlightsController searchFlightsController = fxmlLoader.getController();
        searchFlightsController.setClient(client);
    }

    @FXML
    void goViewBookings(ActionEvent event) {
        // TODO
    }

}
