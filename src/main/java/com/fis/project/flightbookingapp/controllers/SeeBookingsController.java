package com.fis.project.flightbookingapp.controllers;

import com.fis.project.flightbookingapp.model.Booking;
import com.fis.project.flightbookingapp.model.BookingStatus;
import com.fis.project.flightbookingapp.model.Client;
import com.fis.project.flightbookingapp.services.BookingService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.dizitart.no2.NitriteId;

import java.net.URL;
import java.util.ResourceBundle;

public class SeeBookingsController implements Initializable {
    @FXML
    private TableView<Booking> bookingTableView;
    @FXML
    Booking booking = bookingTableView.getSelectionModel().getSelectedItem();
    @FXML
    private TableColumn<Booking, NitriteId> c1;

    @FXML
    private TableColumn<Booking, String> c2;

    @FXML
    private TableColumn<Booking, String> c3;

    @FXML
    private TableColumn<Booking, String> c4;

    @FXML
    private TableColumn<Booking, BookingStatus> c5;
    private Client client;
    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        c1.setCellValueFactory(new PropertyValueFactory<Booking,NitriteId>("bookingId"));
        c2.setCellValueFactory(new PropertyValueFactory<Booking,String>("username"));
        c3.setCellValueFactory(new PropertyValueFactory<Booking,String>("flightNumber"));
        c4.setCellValueFactory(new PropertyValueFactory<Booking,String>("date"));
        c5.setCellValueFactory(new PropertyValueFactory<Booking,BookingStatus>("bookingStatus"));

    }
    private void setupTable(){

       bookingTableView.getItems().addAll(BookingService.getBookingsForClient(client));
        System.out.println(BookingService.getBookingsForClient(client));
    }
    public void setClient(Client client){
        this.client = client;
        setupTable();
    }

}
