package com.fis.project.flightbookingapp.model;

import com.fis.project.flightbookingapp.exceptions.NotInDatabaseException;
import com.fis.project.flightbookingapp.services.AirportService;
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
    private String departureAirportCode;
    private String arrivalAirportCode;
    private List<DayOfWeek> operatingWeekDays;
    private String departureTime;
    private String arrivalTime;

    public Flight() {}

    public Flight(String flightNumber, String airlineCode, @NotNull Airport departureAirport, @NotNull Airport arrivalAirport,
                  List<DayOfWeek> operatingWeekDays, LocalTime localDepartureHour, LocalTime localArrivalHour) {
        this.flightNumber = flightNumber;
        this.airlineCode = airlineCode;
        this.departureAirportCode = departureAirport.getAirportCode();
        this.arrivalAirportCode = arrivalAirport.getAirportCode();
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

    public Airport getDepartureAirportCode() throws NotInDatabaseException {
        return AirportService.getAirportByCode(departureAirportCode);
    }

    public void setDepartureAirport(Airport departureAirport) throws NotInDatabaseException {
        this.departureAirportCode = AirportService.getAirportByCode(departureAirport.getAirportCode()).getAirportCode();
    }

    public void setDepartureAirportCode(String departureAirportCode) throws NotInDatabaseException {
        AirportService.getAirportByCode(departureAirportCode);
        this.departureAirportCode = departureAirportCode;
    }

    public Airport getArrivalAirport() throws NotInDatabaseException {
        return AirportService.getAirportByCode(arrivalAirportCode);
    }

    public String getArrivalAirportCode() {
        return arrivalAirportCode;
    }

    public void setArrivalAirport(Airport arrivalAirport) throws NotInDatabaseException {
        this.arrivalAirportCode = AirportService.getAirportByCode(arrivalAirport.getAirportCode()).getAirportCode();
    }

    public void setArrivalAirportCode(String arrivalAirportCode) throws NotInDatabaseException {
        AirportService.getAirportByCode(arrivalAirportCode);
        this.arrivalAirportCode = arrivalAirportCode;
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

    void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * Sets the flight's departure time, taking the local departure time as an argument
     */
    public void setDepartureTime(LocalTime localDepartureTime) {
        try {
            this.departureTime = OffsetTime.of(localDepartureTime, AirportService.getAirportByCode(departureAirportCode).getZoneOffset()).toString();
        } catch (NotInDatabaseException e) {
            System.out.println(e);
        }
    }

    public OffsetTime getArrivalTime() {
        return OffsetTime.parse(arrivalTime);
    }

    void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * Sets the flight's arrival time, taking the local arrival time as an argument
     */
    public void setArrivalTime(LocalTime localArrivalTime) {
        try {
            this.arrivalTime = OffsetTime.of(localArrivalTime, AirportService.getAirportByCode(arrivalAirportCode).getZoneOffset()).toString();
        } catch (NotInDatabaseException e) {
            System.out.println(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(flightNumber, flight.flightNumber) && Objects.equals(airlineCode, flight.airlineCode) && Objects.equals(departureAirportCode, flight.departureAirportCode) && Objects.equals(arrivalAirportCode, flight.arrivalAirportCode) && Objects.equals(operatingWeekDays, flight.operatingWeekDays) && Objects.equals(departureTime, flight.departureTime) && Objects.equals(arrivalTime, flight.arrivalTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightNumber, airlineCode, departureAirportCode, arrivalAirportCode, operatingWeekDays, departureTime, arrivalTime);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightNumber='" + flightNumber + '\'' +
                ", airlineCode='" + airlineCode + '\'' +
                ", departureAirport=" + departureAirportCode +
                ", arrivalAirport=" + arrivalAirportCode +
                ", operatingWeekDays=" + operatingWeekDays +
                ", departureHour=" + departureTime +
                ", arrivalHour=" + arrivalTime +
                '}';
    }
}
