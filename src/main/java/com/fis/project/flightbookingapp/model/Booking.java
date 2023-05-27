package com.fis.project.flightbookingapp.model;

import com.fis.project.flightbookingapp.exceptions.TravellerAlreadyExistsException;
import org.dizitart.no2.IndexType;
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.Id;
import org.dizitart.no2.objects.Index;
import org.dizitart.no2.objects.Indices;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Indices({
     @Index(value = "bookingId", type = IndexType.Unique),
     @Index(value = "username", type = IndexType.NonUnique)
})
public class Booking implements Serializable {
    @Id
    private NitriteId bookingId;
    private String username;
    private String flightNumber;
    private String date;
    private Set<String> travellers;
    private BookingStatus bookingStatus;
    private String seatNumber;
    private String foodOption;
    private boolean hasPriorityBoarding;

    public Booking() {}

    public Booking(String username, Flight flight, LocalDate date, Set<String> travellers, String seatNumber, String foodOption, boolean hasPriorityBoarding) {
        this.username = username;
        this.flightNumber = flight.getFlightNumber();
        this.date = date.toString();
        this.travellers = travellers;
        this.bookingStatus = BookingStatus.UNDER_REVIEW;
        this.seatNumber = seatNumber;
        this.foodOption = foodOption;
        this.hasPriorityBoarding = hasPriorityBoarding;
    }

    public NitriteId getBookingId() {
        return bookingId;
    }

    public void setBookingId(NitriteId bookingId) {
        this.bookingId = bookingId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlight(Flight flight) {
        this.flightNumber = flight.getFlightNumber();
    }

    public void setFlight(String flightNumber) { this.flightNumber = flightNumber; }

    public String getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date.toString();
    }

    public void setDate(String date) {
        LocalDate.parse(date);
        this.date = date;
    }

    public Set<String> getTravellers() {
        return travellers;
    }

    public void setTravellers(Set<String> travellers) {
        this.travellers = travellers;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getFoodOption() {
        return foodOption;
    }

    public void setFoodOption(String foodOption) {
        this.foodOption = foodOption;
    }

    public boolean isHasPriorityBoarding() {
        return hasPriorityBoarding;
    }

    public void setHasPriorityBoarding(boolean hasPriorityBoarding) {
        this.hasPriorityBoarding = hasPriorityBoarding;
    }

    public void addTraveller(String name) throws TravellerAlreadyExistsException {
        if (!travellers.add(name)) {
            throw new TravellerAlreadyExistsException(name);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(username, booking.username) && Objects.equals(flightNumber, booking.flightNumber) && Objects.equals(date, booking.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId, username, flightNumber, date);
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", username='" + username + '\'' +
                ", flightNumber='" + flightNumber + '\'' +
                ", date='" + date + '\'' +
                ", travellers=" + travellers +
                ", bookingStatus=" + bookingStatus +
                ", seatNumber='" + seatNumber + '\'' +
                ", foodOption='" + foodOption + '\'' +
                ", hasPriorityBoarding=" + hasPriorityBoarding +
                '}';
    }
}
