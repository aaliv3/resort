package com.resort;
public class Apartment extends Accommodation {
    public static final int MAX_OCCUPANCY = 6;
    
    public Apartment(String id, double pricePerNight) {
        super(id, pricePerNight, MAX_OCCUPANCY, AccommodationType.APARTMENT);
    }

    @Override 
    public String describe() {
        return "Apartment (Up to " + MAX_OCCUPANCY + " occupants)";
    }
}   
