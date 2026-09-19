package com.resort;

import java.time.LocalDate;
import java.util.List;

/**
 * Things that can be reserved and checked for availability over a date range.
 * Implemented by Accommodation.
 */
public interface Reservable {
    boolean isAvailable(LocalDate startDate, LocalDate endDate);

    void addReservation(LocalDate startDate, LocalDate endDate);

    List<Reservation> getReservations();
}
