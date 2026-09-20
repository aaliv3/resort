package com.resort;

import java.util.ArrayList;
import java.util.List;

public class LiftPass implements Priceable {

    public static final double SINGLE_LIFT_RATE = 26.0; // Rate for a single lift pass
    public static final double SEASON_UNLIMITED_PRICE = 200.0; // Flat
    public static final int SEASON_DAYS = 30;
    public static final int DAY_DISCOUNT_THRESHOLD = 5;
    public static final double DAY_DISCOUNT_MULTI = 0.9;

    private static int counter = 1;

    private int id;
    private List<String> holderNames;
    private LiftPassType type;
    private int days;

    public LiftPass(List<String> holderNames, LiftPassType type, int days) {
        this.id = counter++;
        this.holderNames = List.copyOf(holderNames);
        this.type = type;
        this.days = days;
    }

    public int getId() {
        return id;
    }

    public List<String> getHolderNames() {
        return holderNames;
    }

    public LiftPassType getType() {
        return type;
    }

    public int getDays() {
        return days;
    }

    public double getBasePrice() {
        return type == LiftPassType.SEASON ? SEASON_UNLIMITED_PRICE : SINGLE_LIFT_RATE * days;
    }

    public boolean hasDiscount() {
        return type == LiftPassType.DAY && days >= DAY_DISCOUNT_THRESHOLD;
    }

    @Override 
    public double getPrice() {
        return hasDiscount() ? getBasePrice() * DAY_DISCOUNT_MULTI : getBasePrice();
    }

    @Override
    public String toString() {
        return String.format("LiftPass{id=%d, holders=%s, type=%s, days=%d, price=$%.2f}", 
                    id, holderNames, type, days, getPrice());
    }
}
