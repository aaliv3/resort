package com.resort;

public class LiftPass implements Priceable {

    public static final double SINGLE_LIFT_RATE = 26.0; // Rate for a single lift pass
    public static final double SEASON_UNLIMITED_PRICE = 200.0; // Flat
    public static final int SEASON_DAYS = 30;
    public static final int DAY_DISCOUNT_THRESHOLD = 5;
    public static final double DAY_DISCOUNT_RATE = 0.9;

    private static int counter = 1;
    private int id;
    private String holderName;
    private LiftPassType type;
    private int days;

    public LiftPass(String holderName, LiftPassType type, int days) {
        this.id = counter++;
        this.holderName = holderName;
        this.type = type;
        this.days = days;
    }

    public int getId() {
        return id;
    }

    public String getHolderName() {
        return holderName;
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
        return type == LiftPassType.DAY && days > DAY_DISCOUNT_THRESHOLD;
    }

    @Override 
    public double getPrice() {
        return hasDiscount() ? getBasePrice() * DAY_DISCOUNT_RATE : getBasePrice();
    }

    @Override
    public String toString() {
        return "LiftPass{" +
                "id=" + id +
                ", holderName='" + holderName + '\'' +
                ", type=" + type +
                ", days=" + days +
                ", price=" + getPrice() +
                '}';
    }
}
