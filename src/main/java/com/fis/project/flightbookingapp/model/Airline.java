package com.fis.project.flightbookingapp.model;

public class Airline extends User {
    private String icaoCode;
    private String airOperatorCertificate;

    public Airline() {}

    public Airline(String username, String password, String phoneNumber, String address, String country, String email, String icaoCode, String airOperatorCertificate) {
        super(username, password, phoneNumber, address, country, email);
        this.icaoCode = icaoCode;
        this.airOperatorCertificate = airOperatorCertificate;
    }

    public String getIcaoCode() {
        return icaoCode;
    }

    public void setIcaoCode(String icaoCode) {
        this.icaoCode = icaoCode;
    }

    public String getAirOperatorCertificate() {
        return airOperatorCertificate;
    }

    public void setAirOperatorCertificate(String airOperatorCertificate) {
        this.airOperatorCertificate = airOperatorCertificate;
    }

    @Override
    public String toString() {
        return "Airline{" +
                "icaoCode='" + icaoCode + '\'' +
                ", airOperatorCertificate='" + airOperatorCertificate + '\'' +
                "} " + super.toString();
    }
}
