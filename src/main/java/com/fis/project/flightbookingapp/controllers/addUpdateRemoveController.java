package com.fis.project.flightbookingapp.controllers;
import com.fis.project.flightbookingapp.addUpdateRemoveApplication;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.io.IOException;
public class addUpdateRemoveController {
    public addUpdateRemoveController() {
    }
    @FXML
    private Button buttonAdd;

    public void goAdd(ActionEvent event) throws IOException {
        addUpdateRemoveApplication m = new addUpdateRemoveApplication();
        m.changeScene("add_flights.fxml");
    }
}
