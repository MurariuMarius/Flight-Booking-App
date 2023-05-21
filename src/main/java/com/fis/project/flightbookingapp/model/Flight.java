package com.fis.project.flightbookingapp.model;

import org.dizitart.no2.objects.Id;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.*;
import java.util.List;
import java.util.Objects;

public class Flight implements Serializable {
    @Id
    private String flightNumber;
    private String airlineCode;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private List<DayOfWeek> operatingWeekDays;
    private String departureTime;
    private String arrivalTime;

    public Flight() {}

    public Flight(String flightNumber, String airlineCode, @NotNull Airport departureAirport, @NotNull Airport arrivalAirport,
                  List<DayOfWeek> operatingWeekDays, LocalTime localDepartureHour, LocalTime localArrivalHour) {
        this.flightNumber = flightNumber;
        this.airlineCode = airlineCode;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.operatingWeekDays = operatingWeekDays;
        this.departureTime = OffsetTime.of(localDepartureHour, departureAirport.getZoneOffset()).toString();
        this.arrivalTime = OffsetTime.of(localArrivalHour, arrivalAirport.getZoneOffset()).toString();
    }

    public Duration getFlightDuration() {
        return Duration.between(OffsetTime.parse(departureTime), OffsetTime.parse(arrivalTime));
    }

    public LocalTime getLocalDepartureHour() {
        return OffsetTime.parse(departureTime).toLocalTime();
    }

    public LocalTime getLocalArrivalHour() {
        return OffsetTime.parse(arrivalTime).toLocalTime();
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public List<DayOfWeek> getOperatingWeekDays() {
        return operatingWeekDays;
    }

    public void setOperatingWeekDays(List<DayOfWeek> operatingWeekDays) {
        this.operatingWeekDays = operatingWeekDays;
    }

    public OffsetTime getDepartureTime() {
        return OffsetTime.parse(departureTime);
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public OffsetTime getArrivalTime() {
        return OffsetTime.parse(arrivalTime);
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(flightNumber, flight.flightNumber) && Objects.equals(airlineCode, flight.airlineCode) && Objects.equals(departureAirport, flight.departureAirport) && Objects.equals(arrivalAirport, flight.arrivalAirport) && Objects.equals(operatingWeekDays, flight.operatingWeekDays) && Objects.equals(departureTime, flight.departureTime) && Objects.equals(arrivalTime, flight.arrivalTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightNumber, airlineCode, departureAirport, arrivalAirport, operatingWeekDays, departureTime, arrivalTime);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightNumber='" + flightNumber + '\'' +
                ", airlineCode='" + airlineCode + '\'' +
                ", departureAirport=" + departureAirport +
                ", arrivalAirport=" + arrivalAirport +
                ", operatingWeekDays=" + operatingWeekDays +
                ", departureHour=" + departureTime +
                ", arrivalHour=" + arrivalTime +
                '}';
    }
}
