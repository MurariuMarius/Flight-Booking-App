package com.fis.project.flightbookingapp.controllers;

import com.fis.project.flightbookingapp.model.Booking;
import com.fis.project.flightbookingapp.model.BookingStatus;
import com.fis.project.flightbookingapp.model.Client;
import com.fis.project.flightbookingapp.model.User;
import com.fis.project.flightbookingapp.services.BookingService;
import com.fis.project.flightbookingapp.services.ClientUserService;
import com.fis.project.flightbookingapp.services.StageChangeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.dizitart.no2.NitriteId;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ViewPassengersController implements Initializable {

    @FXML
    private TableView<Client> clientTableView;

    @FXML
    private TableColumn<Client, String> c1;

    @FXML
    private TableColumn<Client, String> c2;

    @FXML
    private TableColumn<Client, String> c3;

    @FXML
    private TableColumn<Client, String> c4;

    @FXML
    private TableColumn<Client, String> c5;

    @FXML
    private TextField searchBar;
    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        c1.setCellValueFactory(new PropertyValueFactory<Client, String>("username"));
        c2.setCellValueFactory(new PropertyValueFactory<Client, String>("phoneNumber"));
        c3.setCellValueFactory(new PropertyValueFactory<Client, String>("address"));
        c4.setCellValueFactory(new PropertyValueFactory<Client, String>("country"));
        c5.setCellValueFactory(new PropertyValueFactory<Client, String>("email"));

    }

    @FXML
    void search(ActionEvent event) {
        String flight = searchBar.getText().toString();
        List<Client> clients = new ArrayList<>();
        List<Booking> bookings = BookingService.getBookingsForFlight(flight);
        System.out.println(bookings);
        for(Booking b: bookings){
            clients.add(ClientUserService.getUserByUsername(b.getUsername()));
        }
        System.out.println(clients);
        clientTableView.getItems().addAll(clients);

    }
    @FXML
    void goBack(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = StageChangeService.changeScene(
                stage,
                "airline-menu.fxml",
                "Menu"
        );
    }

}
