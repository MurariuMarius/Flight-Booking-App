package com.fis.project.flightbookingapp.controllers;

import com.fis.project.flightbookingapp.model.Booking;
import com.fis.project.flightbookingapp.model.BookingStatus;
import com.fis.project.flightbookingapp.model.Client;
import com.fis.project.flightbookingapp.services.BookingService;
import com.fis.project.flightbookingapp.services.StageChangeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.dizitart.no2.NitriteId;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.fis.project.flightbookingapp.model.BookingStatus.CANCELLED;

public class ManageBookingsController implements Initializable {
    @FXML
    private TableView<Booking> bookingTableView;
    @FXML
    private Button buttonCancel;
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
    @FXML
    void cancelFlight(ActionEvent event) {
        Booking booking = bookingTableView.getSelectionModel().getSelectedItem();
        booking.setBookingStatus(CANCELLED);
        BookingService.updateBooking(booking);


        System.out.println(booking);
        bookingTableView.getItems().clear();

        setupTable();
    }
    @FXML
    void goBack(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();


        FXMLLoader loader = StageChangeService.changeScene(
                stage,
                "client-menu.fxml",
                "Menu"
        );
        ClientMenuController controller = loader.getController();
        controller.initData(client);


    }

}
