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

    public boolean hasLiftPassFor(String personName) {
        return liftPasses.stream()
                .anyMatch(pass -> pass.getHolderNames().contains(personName));
    }
    
    public LiftPass getLiftPassFor(String personName) {
        return liftPasses.stream()
            .filter(pass -> pass.getHolderNames().contains(personName))
            .findFirst()
                .orElse(null);
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
        String accommodationInfo = accommodation != null ? accommodation.describe() : "No accommodation booked";
        StringBuilder output = new StringBuilder();
        output.append("TravelBundle ID: ").append(id).append("\n")
                .append("Customer: ").append(customer.getName()).append("\n")
                .append("Family Members: ").append(familyMembers.size());
                if (familyMembers.isEmpty()) {
                    output.append(" None\n");
                } else {
                    output.append(System.lineSeparator());
                    for (FamilyMember member : familyMembers) {
                        output.append(" - ").append(member.getName()).append("\n");
                    }
                }
                output.append("Lift Passes Holders: ").append("\n");
                if (liftPasses.isEmpty()) {
                    output.append(" None\n");
                } else {
                    for (LiftPass pass : liftPasses) {
                        output.append(" - ").append(pass.getHolderNames())
                                .append(", Type:").append(pass.getType())
                                .append(", Days: ").append(pass.getDays())
                                .append(", Price: $").append(String.format("%.2f\n", pass.getPrice()));
                    }
                }
                output.append("Accommodation: ").append(accommodationInfo);
                if (accommodation != null) {
                    output.append(" - $").append(String.format("%.2f", accommodation.getPrice())).append("/night\n");
                } else {
                    output.append("\n");
                }
                output.append("Start Date: ").append(startDate).append("\n")
                .append("Duration: ").append(duration).append(" days\n")
                .append("End Date: ").append(getEndDate()).append("\n")
                .append("Total Lift Pass Cost: $").append(String.format("%.2f", liftPassTotal())).append("\n")
                .append("Bundle Total Cost: $").append(String.format("%.2f", (liftPassTotal() + (accommodation != null ? accommodation.getPrice() * duration : 0)))).append("\n")
                .append("-------------------- \n");
         return output.toString();
    }
}
