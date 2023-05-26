package com.fis.project.flightbookingapp.controllers;

import com.fis.project.flightbookingapp.model.Booking;
import com.fis.project.flightbookingapp.model.BookingStatus;
import com.fis.project.flightbookingapp.services.BookingService;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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

    public void initialize() {
        List<Booking> bookings = BookingService.getBookingsByStatus(BookingStatus.UNDER_REVIEW);

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
                // TODO Update database
            });
            tableCell.graphicProperty().bind(Bindings.when(tableCell.emptyProperty()).then((Node)null).otherwise(c));
            return tableCell;
        });
        tableView.getItems().addAll(bookings);

        System.out.println(bookings);
    }

}
