package com.resort;

public class HotelRoom extends Accommodation {
    public static final int MAX_OCCUPANCY = 4;
    
    public HotelRoom(String id, double pricePerNight) {
        super(id, pricePerNight, MAX_OCCUPANCY, AccommodationType.HOTEL_ROOM);
    }

    @Override 
    public String describe() {
        return "Hotel Room (Up to " + MAX_OCCUPANCY + " occupants)";
    }
}   
