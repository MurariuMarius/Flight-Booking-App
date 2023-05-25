package com.fis.project.flightbookingapp.services;

import com.fis.project.flightbookingapp.model.Airport;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public class AirportGetterService {

    private static final Type AIRPORT_LIST_TYPE = new TypeToken<List<Airport>>(){}.getType();
    public static List<Airport> getAirportsFromJSON() throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader("src/main/resources/com/fis/project/flightbookingapp/airports.json"));
        return gson.fromJson(reader, AIRPORT_LIST_TYPE);
    }
}
