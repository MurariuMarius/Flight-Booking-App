module com.fis.project.flightbookingapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires nitrite;
    requires annotations;

    opens com.fis.project.flightbookingapp to javafx.fxml;
    exports com.fis.project.flightbookingapp;
    opens com.fis.project.flightbookingapp.services;
    opens com.fis.project.flightbookingapp.model;
    exports com.fis.project.flightbookingapp.controllers;
    opens com.fis.project.flightbookingapp.controllers to javafx.fxml;
}