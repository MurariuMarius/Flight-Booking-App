package com.fis.project.flightbookingapp.controllers;

import com.fis.project.flightbookingapp.exceptions.BookingAlreadyExistsException;
import com.fis.project.flightbookingapp.exceptions.NotInDatabaseException;
import com.fis.project.flightbookingapp.model.Booking;
import com.fis.project.flightbookingapp.model.Client;
import com.fis.project.flightbookingapp.model.CreditCard;
import com.fis.project.flightbookingapp.services.BookingService;
import com.fis.project.flightbookingapp.services.ClientUserService;
import com.fis.project.flightbookingapp.services.FlightService;
import com.fis.project.flightbookingapp.services.StageChangeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FinalizeBookingController {

    @FXML
    private Button addCardButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Text cardExpirationDate;

    @FXML
    private TextField cardExpirationDateField;

    @FXML
    private Text cardNumber;

    @FXML
    private TextField cardNumberField;

    @FXML
    private Text cardhoderName;

    @FXML
    private TextField cardholderNameField;

    @FXML
    private ListView<Booking> bookingListView;

    @FXML
    private GridPane cardGridPane;

    @FXML
    private Button newCard;

    @FXML
    private Text pageTitle;

    @FXML
    private Button payButton;

    @FXML
    private ComboBox<String> paymentOptions;

    @FXML
    private Text totalPrice;

    private Client client;
    private List<Booking> bookings;
    private double price;

    public void setData(Client client, List<Booking> bookings, double price){
        this.client = client;
        this.bookings = bookings;
        this.price = price;

        paymentOptions.getItems().addAll(client.getCreditCards().stream().map(CreditCard::getCardNumber).toList());

        try {
            pageTitle.setText("Your booking to " +
                    FlightService.getFlightByNumber(bookings.get(0).getFlightNumber()).getArrivalAirport().getCity());
        } catch (NotInDatabaseException e) {
            System.out.println(e);
        }

        bookingListView.getItems().addAll(bookings);

        totalPrice.setText(String.format("€ %.2f", price));
    }

    @FXML
    void initialize() {
        makeCardAdderFieldsVisible(false);
    }

    @FXML
    void addNewCard(ActionEvent event) {
        String cardHolderName = cardholderNameField.getText();
        String expirationDate = cardExpirationDateField.getText();
        String cardNumber = cardNumberField.getText();
        CreditCard creditCard = new CreditCard(
                cardNumber,
                cardHolderName,
                expirationDate
        );
        List<CreditCard> creditCards = new ArrayList<>(client.getCreditCards());
        if (creditCards.contains(creditCard)) {
            System.out.println("Credit card already added");
        } else {
            creditCards.add(creditCard);
            client.setCreditCards(creditCards);
            ClientUserService.updateUser(client);
            makeCardAdderFieldsVisible(false);
            paymentOptions.getItems().add(creditCard.getCardNumber());
        }
    }

    @FXML
    void cancelBooking(ActionEvent event) throws IOException {
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
    void openNewCardWindow(ActionEvent event) {
        makeCardAdderFieldsVisible(true);
    }

    @FXML
    void pay(ActionEvent event) throws BookingAlreadyExistsException, NotInDatabaseException, IOException {
        for (Booking b : bookings) {
            BookingService.addBooking(b);
        }

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = StageChangeService.changeScene(
                stage,
                "see_bookings.fxml",
                "Flight Booking App - Your bookings"
        );
        ManageBookingsController controller = loader.getController();
        controller.setClient(client);
    }

    @FXML
    void selectCard(ActionEvent event) {

    }

    private void makeCardAdderFieldsVisible(boolean option) {
        cardGridPane.setVisible(option);
        addCardButton.setVisible(option);
    }

}
