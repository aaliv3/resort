package com.resort;

import java.time.LocalDate;

public class Reservation {

    private final LocalDate START_DATE;
    private final LocalDate END_DATE;

    public Reservation(LocalDate START_DATE, LocalDate END_DATE) {
        this.START_DATE = START_DATE;
        this.END_DATE = END_DATE;
    }

    public LocalDate getStartDate() {
         return START_DATE;
    }

    public LocalDate getEndDate() {
        return END_DATE;
    }
    
    public boolean overlaps(LocalDate startDate, LocalDate endDate) {
        return !startDate.isAfter(END_DATE) && !endDate.isBefore(START_DATE);
    }
}
