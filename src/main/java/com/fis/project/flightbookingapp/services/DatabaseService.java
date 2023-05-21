package com.fis.project.flightbookingapp.services;

import com.fis.project.flightbookingapp.model.Flight;
import org.dizitart.no2.Nitrite;


public class DatabaseService {

    public static Nitrite database = null;
    private static void initDatabase() {
        database = Nitrite.builder()
                .filePath("fba.db")
                .openOrCreate("student", "student");
    }

    public static Nitrite getDatabase() {
        if (database == null) {
            initDatabase();
        }
        return database;
    }
}
