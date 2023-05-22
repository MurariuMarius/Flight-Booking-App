package com.fis.project.flightbookingapp.controllers;

import com.fis.project.flightbookingapp.addUpdateRemoveApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.io.IOException;
public class addFlightsController {

    public addFlightsController() {

    }

    @FXML
    private Button button;
    @FXML
   /* private TextField field1,field2,field3,field4,field5,field6;
    @FXML
    private CheckBox c1,c2,c3,c4,c5,c6,c7;*/


    public void addFlight(ActionEvent event) throws IOException {
        //save the flight data
        /*String f1 = field1.getText().toString();
        String f2 = field2.getText().toString();
        String f3 = field3.getText().toString();
        String f4 = field4.getText().toString();
        String f5 = field5.getText().toString();
        String f6 = field6.getText().toString();*/
        addUpdateRemoveApplication m = new addUpdateRemoveApplication();
        m.changeScene("add_update_remove.fxml");
    }

}