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
    private Button buttonDelete;
    @FXML
    private Button buttonUpdate;
    @FXML
    public void goAdd(ActionEvent actionEvent) throws IOException {
        addUpdateRemoveApplication m = new addUpdateRemoveApplication();
        m.changeScene("add_flights.fxml");
      }
      @FXML
    public void goDelete(ActionEvent actionEvent) throws  IOException{
          addUpdateRemoveApplication m = new addUpdateRemoveApplication();
          m.changeScene("delete_flight.fxml");
    }
    @FXML
    public void goUpdate(ActionEvent actionEvent) throws IOException{
        addUpdateRemoveApplication m = new addUpdateRemoveApplication();
        m.changeScene("update_flights.fxml");
    }

}
