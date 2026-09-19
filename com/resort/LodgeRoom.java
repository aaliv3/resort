package com.resort;
public class LodgeRoom extends Accommodation {
    public static final int MAX_OCCUPANCY = 2;
    
    public LodgeRoom(String id, double pricePerNight) {
        super(id, pricePerNight, MAX_OCCUPANCY, AccommodationType.LODGE_ROOM);
    }

    @Override 
    public String describe() {
        return "Lodge Room (Up to " + MAX_OCCUPANCY + " occupants)";
    }
}   
