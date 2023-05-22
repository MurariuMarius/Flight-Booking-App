package com.fis.project.flightbookingapp.model;

import org.dizitart.no2.objects.Id;

import java.io.Serializable;
import java.time.ZoneOffset;
import java.util.Objects;

public class Airport implements Serializable {
    @Id
    private String airportCode;
    private String airportName;
    private String city;
    private String zoneOffset;


    public Airport() {}
    public Airport(String airportCode, String airportName, String city, ZoneOffset zoneOffset) {
        this.airportCode = airportCode;
        this.airportName = airportName;
        this.city = city;
        this.zoneOffset = zoneOffset.toString();
    }

    public ZoneOffset getZoneOffset() {
        return ZoneOffset.of(zoneOffset);
    }

    public void setZoneOffset(String zoneOffset) {
        this.zoneOffset = zoneOffset;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport = (Airport) o;
        return Objects.equals(airportCode, airport.airportCode) && Objects.equals(airportName, airport.airportName) && Objects.equals(city, airport.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(airportCode, airportName, city);
    }

    @Override
    public String toString() {
        return "Airport{" +
                "airportCode='" + airportCode + '\'' +
                ", airportName='" + airportName + '\'' +
                ", city='" + city + '\'' +
                ", zoneOffset=" + zoneOffset +
                '}';
    }
}
