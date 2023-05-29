package com.fis.project.flightbookingapp;

import com.fis.project.flightbookingapp.exceptions.AirportAlreadyExistsException;
import com.fis.project.flightbookingapp.exceptions.BookingAlreadyExistsException;
import com.fis.project.flightbookingapp.exceptions.FlightAlreadyExistsException;
import com.fis.project.flightbookingapp.exceptions.NotInDatabaseException;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BookFlightApplication extends Application {
    private Parent root;
    @Override
    public void start(Stage stage) throws IOException, NotInDatabaseException {
        /*Flight flight = new Flight("W63453", "WZZ",
                AirportService.getAirportByCode("TSR"),
                AirportService.getAirportByCode("BGY"),
                new ArrayList<>(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.FRIDAY)),
                LocalTime.of(10, 50),
                LocalTime.of(11,45),
                35
        );

        FXMLLoader fxmlLoader = new FXMLLoader(BookFlightApplication.class.getResource("book_flight.fxml"));
        root = fxmlLoader.load();
        BookFlightController bookFlightController = fxmlLoader.getController();
        bookFlightController.setBooking(new Booking("Titi Titulescu", flight, LocalDate.of(2023, 12, 1), Set.of("Titi Titulescu")));
        Scene scene = new Scene(root);
        stage.setTitle("Flight Booking App");
        stage.setScene(scene);
        stage.show();*/
    }

    public static void main(String args[]) throws NotInDatabaseException, FileNotFoundException, AirportAlreadyExistsException, BookingAlreadyExistsException, FlightAlreadyExistsException {
            /*AirportService.addAirports(AirportGetterService.getAirportsFromJSON());

            Flight flight = new Flight("W63453", "WZZ",
                    AirportService.getAirportByCode("TSR"),
                    AirportService.getAirportByCode("BGY"),
                    new ArrayList<>(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.FRIDAY)),
                    LocalTime.of(10, 50),
                    LocalTime.of(11,45)
            );

            FlightService.addFlight(flight);

            Booking booking = new Booking("Titi Titulescu", flight, LocalDate.of(2023, 12, 1), Set.of("Titi Titulescu"));
            Booking booking1 = new Booking("Barbu Titulescu", flight, LocalDate.of(2023, 12, 1), Set.of("Titi Titulescu"));
            Booking booking2 = new Booking("Andrei Titulescu", flight, LocalDate.of(2023, 12, 1), Set.of("Titi Titulescu"));

            BookingService.addBooking(booking);
            BookingService.addBooking(booking1);
            BookingService.addBooking(booking2);

            for (Booking b : BookingService.getBookings()) {
                System.out.println(b);
            }

            Flight flight1 = new Flight("W63443", "WZZ",
                    AirportService.getAirportByCode("TSR"),
                    AirportService.getAirportByCode("BGY"),
                    new ArrayList<>(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.FRIDAY)),
                    LocalTime.of(16, 00),
                    LocalTime.of(17,15)
            );

            Flight flight2 = new Flight("W63444", "WZZ",
                    AirportService.getAirportByCode("BGY"),
                    AirportService.getAirportByCode("TSR"),
                    new ArrayList<>(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.FRIDAY)),
                    LocalTime.of(17, 55),
                    LocalTime.of(20,30)
            );

            FlightService.addFlight(flight1);
            FlightService.addFlight(flight2);

            for (Flight f:FlightService.getFlights()) {
                System.out.println(f);
            }

            Client client = new Client("Titi Titulescu", "parolaputernica", "+40256111222", "Vasile Parvan 2, Timisoara", "Romania", "titi@titulescu.ro");
            Client client2 = new Client("Barbu Titulescu", "parolaputernica", "+40256111222", "Vasile Parvan 2, Timisoara", "Romania", "titi@titulescu.ro");
            Client client3 = new Client("Andrei Titulescu", "parolaputernica", "+40256111222", "Vasile Parvan 2, Timisoara", "Romania", "titi@titulescu.ro");

            System.out.println(BookingService.getBookingsForClient(client, BookingStatus.ACCEPTED));
            System.out.println(BookingService.getBookingsByStatus(BookingStatus.UNDER_REVIEW));
            System.out.println("main");*/



        launch();
    }
}
