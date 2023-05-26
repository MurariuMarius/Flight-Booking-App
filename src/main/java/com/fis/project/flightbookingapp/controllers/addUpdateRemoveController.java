package com.fis.project.flightbookingapp.controllers;
import com.fis.project.flightbookingapp.Main;
import com.fis.project.flightbookingapp.addUpdateRemoveApplication;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
public class addUpdateRemoveController {
    public addUpdateRemoveController() {
    }
    @FXML
    private Button buttonAdd;
    @FXML
    public void goAdd(ActionEvent actionEvent) throws IOException {
        addUpdateRemoveApplication m = new addUpdateRemoveApplication();
        m.changeScene("add_flights.fxml");
      /*  try {
            Stage stage = (Stage) buttonAdd.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("add_flights.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle("Add flights");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
    }*/}
}
