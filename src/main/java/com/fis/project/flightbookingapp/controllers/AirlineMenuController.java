package com.fis.project.flightbookingapp.controllers;
import com.fis.project.flightbookingapp.addUpdateRemoveApplication;
import com.fis.project.flightbookingapp.model.Airline;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.io.IOException;
public class AirlineMenuController {

    public AirlineMenuController() {
    }
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonUpdate;
    @FXML
    private Button buttonManageFlights;
    @FXML
    private Button buttonViewPassengersPerFlight;
    private Airline airline;

    public void initData(Airline airline) {
        this.airline = airline;
    }

    @FXML
    public void goAdd(ActionEvent actionEvent) throws IOException {
        addUpdateRemoveApplication m = new addUpdateRemoveApplication();
        m.changeScene("add_flights.fxml");
      }
      @FXML
    public void goDelete(ActionEvent actionEvent) throws  IOException{
          addUpdateRemoveApplication m = new addUpdateRemoveApplication();
          m.changeScene("delete_flight.fxml");
    }
    @FXML
    public void goUpdate(ActionEvent actionEvent) throws IOException{
        addUpdateRemoveApplication m = new addUpdateRemoveApplication();
        m.changeScene("update_flights.fxml");
    }

    @FXML
    public void goManageFlights(ActionEvent actionEvent) throws IOException{
        addUpdateRemoveApplication m = new addUpdateRemoveApplication();
        m.changeScene("manage-booking-requests.fxml");
    }

    @FXML
    public void goViewPassengersPerFlight(ActionEvent actionEvent) throws IOException{
//        addUpdateRemoveApplication m = new addUpdateRemoveApplication();
//        m.changeScene("update_flights.fxml");
        // TODO
    }
}
