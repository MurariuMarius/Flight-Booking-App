package com.fis.project.flightbookingapp;

import com.fis.project.flightbookingapp.exceptions.AirportAlreadyExistsException;
import com.fis.project.flightbookingapp.services.AirportGetterService;
import com.fis.project.flightbookingapp.services.AirportService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

public class addUpdateRemoveApplication extends Application {

    private static Stage stg;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stg = primaryStage;
        primaryStage.setResizable(true);
        Parent root = FXMLLoader.load(getClass().getResource("add_update_remove.fxml"));
        primaryStage.setTitle("add/update/delete");
        primaryStage.setScene(new Scene(root, 650, 500));
        primaryStage.show();
    }

    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.getScene().setRoot(pane);
    }


    public static void main(String[] args) throws FileNotFoundException, AirportAlreadyExistsException {

        //AirportService.addAirports(AirportGetterService.getAirportsFromJSON());launch();
        launch(args);
    }
}