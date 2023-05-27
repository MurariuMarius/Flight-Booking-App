package com.fis.project.flightbookingapp.services;

import com.fis.project.flightbookingapp.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StageChangeService {
    public static FXMLLoader changeScene(Stage stage, String path, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(path));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();

        return fxmlLoader;
    }
}
