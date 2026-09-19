package com.resort;

public class LiftPass implements Priceable {

    public static final double SINGLE_LIFT_RATE = 26.0; // Rate for a single lift pass
    public static final double SEASON_UNLIMITED_PRICE = 200.0; // Flat
    public static final int SEASON_UNLIMITED_DAYS = 30; 

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

    @Override 
    public double getPrice() {
        return (type == LiftPassType.DAY) ? days * SINGLE_LIFT_RATE : SEASON_UNLIMITED_PRICE;
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
