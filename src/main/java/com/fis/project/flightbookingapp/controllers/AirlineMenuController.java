package com.fis.project.flightbookingapp.controllers;
import com.fis.project.flightbookingapp.model.Airline;
import com.fis.project.flightbookingapp.services.StageChangeService;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        StageChangeService.changeScene(
                stage,
                "add_flights.fxml",
                "Add flights"
        );
      }
      @FXML
    public void goDelete(ActionEvent actionEvent) throws  IOException{
          Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
          StageChangeService.changeScene(
                  stage,
                  "delete_flight.fxml",
                  "Delete flight"
          );
    }
    @FXML
    public void goUpdate(ActionEvent actionEvent) throws IOException{
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        StageChangeService.changeScene(
                stage,
                "update_flights.fxml",
                "Update flights"
        );
    }

    @FXML
    public void goManageBookingRequests(ActionEvent actionEvent) throws IOException{
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = StageChangeService.changeScene(
                stage,
                "manage-booking-requests.fxml",
                "Manage booking requests"
        );
        BookingRequestsManagementController controller = loader.getController();
        controller.initData(airline);
    }

    @FXML
    public void goViewPassengersPerFlight(ActionEvent actionEvent) throws IOException{
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        StageChangeService.changeScene(
                stage,
                "view_passengers.fxml",
                "View passengers per flight"
        );
    }
    @FXML
    void goBack(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = StageChangeService.changeScene(
                stage,
                "home.fxml",
                "Home"
        );
    }
}
