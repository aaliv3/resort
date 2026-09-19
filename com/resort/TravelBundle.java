package com.resort;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

public class TravelBundle {
    private final int id;
    private final Customer customer;
    private final List<FamilyMember> familyMembers; // Family members on this trip
    private final List<LiftPass> liftPasses; // Family memebrs with Lift passes
    private Accommodation accommodation; // Accommodation for the trip
    private final LocalDate startDate; // Start date of the trip
    private final int duration;

    public TravelBundle(int id, Customer customer, LocalDate startDate, int duration) {
        this.id = id;
        this.customer = customer;
        this.familyMembers = new ArrayList<>();
        this.liftPasses = new ArrayList<>();
        this.startDate = startDate;
        this.duration = duration;
    }
    
    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<FamilyMember> getFamilyMembers() {
        return familyMembers;
    }

    public void addFamilyMember(FamilyMember familyMember) {
        familyMembers.add(familyMember);
    }

    public List<LiftPass> getLiftPasses() {
        return liftPasses;
    }

    public void addLiftPass(LiftPass liftPass) {
        liftPasses.add(liftPass);
    }

    public double liftPassTotal() {
        return liftPasses.stream()
                .mapToDouble(LiftPass::getPrice)
                .sum();
    }


    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public int getDuration() {
        return duration;
    }

    public LocalDate getEndDate() {
        return startDate.plusDays(duration);
    }

    public int totalFamilyMembers() {
        return 1 + familyMembers.size();
    }

    @Override 
    public String toString() {
        String accomodationInfo = (accommodation != null) ? accommodation.describe() : "No accommodation booked";
        return "TravelBundle ID: " + id + "\n" +
               "Customer: " + customer.getName() + "\n" +
               "Family Members: " + familyMembers.size() + "\n" +
               "Lift Passes: " + liftPasses.size() + "\n" +
               "Accommodation: " + accomodationInfo + "\n" +
               "Start Date: " + startDate + "\n" +
               "Duration: " + duration + " days\n" +
               "End Date: " + getEndDate() + "\n" +
               "Total Lift Pass Cost: $" + String.format("%.2f", liftPassTotal()) + "\n" +
               "-------------------- \n";
    }

}
