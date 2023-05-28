package com.fis.project.flightbookingapp.model;

import org.dizitart.no2.objects.InheritIndices;

import java.util.List;
import java.util.Objects;

@InheritIndices
public class Client extends User {
    private List<CreditCard> creditCards;

    public Client () {}
    public Client(String username, String password, String phoneNumber, String address, String country, String email) {
        super(username, password, phoneNumber, address, country, email);
    }

    public List<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(List<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Client client = (Client) o;
        return Objects.equals(creditCards, client.creditCards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), creditCards);
    }

    @Override
    public String toString() {
        return "Client{" +
                "creditCards=" + creditCards +
                "} " + super.toString();
    }
}
