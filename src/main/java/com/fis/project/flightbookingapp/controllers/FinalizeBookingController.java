package com.fis.project.flightbookingapp.controllers;

import com.fis.project.flightbookingapp.exceptions.BookingAlreadyExistsException;
import com.fis.project.flightbookingapp.exceptions.NotInDatabaseException;
import com.fis.project.flightbookingapp.model.Booking;
import com.fis.project.flightbookingapp.model.Client;
import com.fis.project.flightbookingapp.model.CreditCard;
import com.fis.project.flightbookingapp.services.BookingService;
import com.fis.project.flightbookingapp.services.ClientUserService;
import com.fis.project.flightbookingapp.services.StageChangeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.ref.Cleaner;
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
    private Text fligthDetails;

    @FXML
    private GridPane cardGridPane;

    @FXML
    private Button newCard;

    @FXML
    private Text pageTittle;

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
    }

    @FXML
    void initialize() {
        makeCardAdderFiledsVisible(false);
        paymentOptions.getItems().addAll(client.getCreditCards().stream().map(CreditCard::getCardNumber).toList());
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
        List<CreditCard> creditCards = client.getCreditCards();
        if (creditCards.contains(creditCard)) {
            System.out.println("Credit card already added");
        } else {
            creditCards.add(creditCard);
            client.setCreditCards(creditCards);
            ClientUserService.updateUser(client);
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
        makeCardAdderFiledsVisible(true);
    }

    @FXML
    void pay(ActionEvent event) throws BookingAlreadyExistsException, NotInDatabaseException {
        for (Booking b : bookings) {
            BookingService.addBooking(b);
        }
    }

    @FXML
    void selectCard(ActionEvent event) {

    }

    private void makeCardAdderFiledsVisible(boolean option) {
        cardGridPane.setVisible(option);
        addCardButton.setVisible(option);
    }

}
