package com.resort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for all accommodation offered by the resort.
 * LodgeRoom, HotelRoom and Apartment share this common structure
 * (id, price, capacity, reservations) but each fixes its own maximum
 * capacity and description.
 */
public abstract class Accommodation implements Priceable, Reservable {

    private final String id;
    private final double pricePerNight;
    private final int capacity;
    private final AccommodationType type;
    private final List<Reservation> reservations;

    public Accommodation(String id, double pricePerNight, int capacity, AccommodationType type) {
        this.id = id;
        this.pricePerNight = pricePerNight;
        this.capacity = capacity;
        this.type = type;
        this.reservations = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public AccommodationType getType() {
        return type;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    @Override
    public double getPrice() {
        return pricePerNight;
    }

    @Override
    public boolean isAvailable(LocalDate startDate, LocalDate endDate) {
        for (Reservation r : reservations) {
            if (r.overlaps(startDate, endDate)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void addReservation(LocalDate startDate, LocalDate endDate) {
        reservations.add(new Reservation(startDate, endDate));
    }

    @Override
    public List<Reservation> getReservations() {
        return reservations;
    }

    public boolean canHold(int numberOfPeople) {
        return numberOfPeople <= capacity;
    }

    /**
     * Each concrete accommodation type describes itself for display purposes.
     */
    public abstract String describe();

    @Override
    public String toString() {
        return String.format("[%s] %s - Capacity: %d - $%.2f/night", id, describe(), capacity, pricePerNight);
    }
}
