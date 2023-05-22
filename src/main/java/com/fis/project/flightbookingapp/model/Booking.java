package com.fis.project.flightbookingapp.model;

import com.fis.project.flightbookingapp.exceptions.TravellerAlreadyExistsException;
import org.dizitart.no2.IndexType;
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.Id;
import org.dizitart.no2.objects.Index;
import org.dizitart.no2.objects.Indices;

import java.io.Serializable;
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
    private Flight flight;
    private String date;
    private Set<String> travellers;

    public Booking() {}

    public Booking(String username, Flight flight, String date, Set<String> travellers) {
        this.username = username;
        this.flight = flight;
        this.date = date;
        this.travellers = travellers;
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

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Set<String> getTravellers() {
        return travellers;
    }

    public void setTravellers(Set<String> travellers) {
        this.travellers = travellers;
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
        return Objects.equals(username, booking.username) && Objects.equals(flight, booking.flight) && Objects.equals(date, booking.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId, username, flight, date);
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", username='" + username + '\'' +
                ", flight='" + flight + '\'' +
                ", date='" + date + '\'' +
                ", travellers=" + travellers +
                '}';
    }
}
