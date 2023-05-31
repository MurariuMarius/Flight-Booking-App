package com.fis.project.flightbookingapp.controllers;

import com.fis.project.flightbookingapp.model.Airline;
import com.fis.project.flightbookingapp.model.Booking;
import com.fis.project.flightbookingapp.model.BookingStatus;
import com.fis.project.flightbookingapp.services.BookingService;
import com.fis.project.flightbookingapp.services.StageChangeService;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class BookingRequestsManagementController {

    @FXML
    private TableColumn<Booking, ComboBox> actionColumn;

    @FXML
    private TableColumn<Booking, String> bookingId;

    @FXML
    private TableColumn<Booking, String> clientUsername;

    @FXML
    private TableColumn<Booking, String> date;

    @FXML
    private TableColumn<Booking, String> flightNumber;

    @FXML
    private Button homeButton;

    @FXML
    private TableView<Booking> tableView;

    @FXML
    private TableColumn<Booking, Set<String>> travellers;

    private Airline airline;

    public void initData(Airline airline) {

        this.airline = airline;

        List<Booking> bookings = BookingService.getBookingsByStatus(BookingStatus.UNDER_REVIEW, airline);

        bookingId.setCellValueFactory(new PropertyValueFactory<Booking, String>("bookingId"));
        clientUsername.setCellValueFactory(new PropertyValueFactory<Booking, String >("username"));
        flightNumber.setCellValueFactory(new PropertyValueFactory<Booking, String >("flight"));
        travellers.setCellValueFactory(new PropertyValueFactory<Booking, Set<String>>("travellers"));
        date.setCellValueFactory(new PropertyValueFactory<Booking, String>("date"));
        actionColumn.setCellFactory(col -> {
            System.out.println(col.getCellFactory().toString());
            TableCell<Booking, ComboBox> tableCell = new TableCell<>();
            ComboBox<String> c = new ComboBox<>();
            c.setId(bookingId.getId());
            c.getItems().addAll("Accept","Reject");
            c.setPromptText("Action");
            c.setOnAction(e -> {
                Booking booking = tableCell.getTableRow().getItem();
                if (c.getSelectionModel().getSelectedItem().equals("Accept")) {
                    booking.setBookingStatus(BookingStatus.ACCEPTED);
                } else {
                    booking.setBookingStatus(BookingStatus.REJECTED);
                }
                System.out.println(booking);
                BookingService.updateBooking(booking);
            });
            tableCell.graphicProperty().bind(Bindings.when(tableCell.emptyProperty()).then((Node)null).otherwise(c));
            return tableCell;
        });
        tableView.getItems().addAll(bookings);

        System.out.println(bookings);
    }
    @FXML
    void goBack(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = StageChangeService.changeScene(
                stage,
                "airline-menu.fxml",
                "Menu"
        );
        AirlineMenuController controller = loader.getController();
        controller.initData(airline);
    }

}
