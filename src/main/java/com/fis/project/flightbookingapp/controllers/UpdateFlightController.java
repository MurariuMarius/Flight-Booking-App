package com.fis.project.flightbookingapp.controllers;

import com.fis.project.flightbookingapp.exceptions.NotInDatabaseException;
import com.fis.project.flightbookingapp.model.Flight;
import com.fis.project.flightbookingapp.services.FlightService;
import com.fis.project.flightbookingapp.services.StageChangeService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateFlightController implements Initializable {

    @FXML
    private Button buttonUpdate;

    @FXML
    private ListView<Flight> listView;

    @FXML
    private TextField searchBar;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @Override
    public void initialize(URL arg0, ResourceBundle arg1)
    {
        listView.getItems().addAll(FlightService.getFlights());
    }

    @FXML
    void goUpdate(MouseEvent event) throws NotInDatabaseException , IOException {
        String flightNo = searchBar.getText().toString();
        Flight flight = FlightService.getFlightByNumber(flightNo);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = StageChangeService.changeScene(
                stage,
                "update_page.fxml",
                "Update flights"
        );
        UpdatePageController updatePageController = loader.getController();
        updatePageController.updateFlight(flight);
    }


}
