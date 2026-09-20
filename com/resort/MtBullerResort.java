package com.resort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * The application manager. Holds the three core array lists (accommodations,
 * customers, bundles) and implements every system function as a method that
 * the menu in MtBullerAdmin calls.
 */
public class MtBullerResort {

    private final List<Accommodation> accommodations;
    private final List<Customer> customers;
    private final List<TravelBundle> bundles;
    private final Scanner scanner;
    private int nextCustomerId;
    private int nextBundleId;

    public MtBullerResort(Scanner scanner) {
        this.scanner = scanner;
        this.accommodations = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.bundles = new ArrayList<>();
        this.nextCustomerId = 1;
        this.nextBundleId = 1;
    }

    public List<Accommodation> getAccommodations() {
        return accommodations;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public List<TravelBundle> getBundles() {
        return bundles;
    }

    /**
     * Populates the starting data: at least 10 accommodations of different
     * types/prices and at least 3 Customers. This is called once at the start of the program.
     */
    public void loadInitialData() {
        accommodations.add(new LodgeRoom("LR01", 95.0));
        accommodations.add(new LodgeRoom("LR02", 105.0));
        accommodations.add(new LodgeRoom("LR03", 90.0));
        accommodations.add(new HotelRoom("HR01", 150.0));
        accommodations.add(new HotelRoom("HR02", 165.0));
        accommodations.add(new HotelRoom("HR03", 140.0));
        accommodations.add(new Apartment("AP01", 260.0));
        accommodations.add(new Apartment("AP02", 290.0));
        accommodations.add(new Apartment("AP03", 240.0));
        accommodations.add(new Apartment("AP04", 275.0));

        Customer cm1 = new Customer(nextCustomerId++, "John Doe", "john.doe@example.com", SkillLevel.ADVANCED);
        Customer cm2 = new Customer(nextCustomerId++, "Jane Doe", "jane.doe@example.com", SkillLevel.INTERMEDIATE);
        Customer cm3 = new Customer(nextCustomerId++, "Steven Li", "steven.li@example.com", SkillLevel.BEGINNER);

        customers.add(cm1);
        customers.add(cm2);
        customers.add(cm3);
    }


    //  Display all accommodations

    public void displayAllAccommodations() {
        if (accommodations.isEmpty()) {
            System.out.println("There are no accommodations in the system.");
            return;
        }
        System.out.println("\n--- All Accommodations ---");
        for (Accommodation a : accommodations) {
            System.out.println(a);
        }
    }


    //  Display available accommodations

    public void displayAvailableAccommodations() {
        LocalDate start = InputHelper.readDate(scanner, "Enter the check-in date you are looking for", true);
        int duration = InputHelper.readInt(scanner, "Enter the number of nights", 1, 60);
        int people = InputHelper.readInt(scanner, "Enter the total number of people (customer + family members)", 1, 6);
        LocalDate end = start.plusDays(duration);

        List<Accommodation> available = findAvailableAccommodations(start, end, people);
        if (available.isEmpty()) {
            System.out.println("No accommodations are available for those dates and group size.");
        } else {
            System.out.println("\n--- Available Accommodations (" + start + " to " + end + ", for " + people + " people) ---");
            for (Accommodation a : available) {
                System.out.println(a);
            }
        }
    }

    private List<Accommodation> findAvailableAccommodations(LocalDate start, LocalDate end, int people) {
        return accommodations.stream()
                .filter(a -> a.canHold(people))
                .filter(a -> a.isAvailable(start, end))
                .collect(Collectors.toList());
    }


    //  Add family member

    public void addCustomer() {
        String name = InputHelper.readNonEmptyString(scanner, "Enter customer's full name");
        String contact = InputHelper.readNonEmptyString(scanner, "Enter customer's contact details (email or phone)");
        SkillLevel level = InputHelper.readSkillLevel(scanner, "Enter the customer's skill level");

        Customer customer = new Customer(nextCustomerId++, name, contact, level);

        customers.add(customer);
        System.out.println("Customer added successfully: " + customer);
    }


    //  List customers

    public void listCustomers() {
        if (customers.isEmpty()) {
            System.out.println("There are no customers in the system.");
            return;
        }
        System.out.println("\n--- All Customers ---");
        for (Customer customer : customers) {
            System.out.println(customer);
            List<TravelBundle> familyBundles = bundles.stream().filter(bundle -> bundle.getCustomer().equals(customer)).toList();
            familyBundles.forEach(bundle -> {
                List<FamilyMember> familyMembersList = bundle.getFamilyMembers();
                if (familyMembersList != null) {
                    familyMembersList.forEach(System.out::println);
                }
            });
        }
    }


    //  Create a bundle

    public void addBundle() throws RecordNotFoundException, AccommodationNotAvailableException, OverLappingBookingException {
        if (customers.isEmpty()) {
            System.out.println("No customers exist yet. Please add a customer first."); // Will never reach this point because the system starts with 3 customers, but just in case lol.
            return;
        }

        int customerId = InputHelper.readInt(scanner, "Enter the customer ID for this bundle", 1, Integer.MAX_VALUE);
        Customer customer = findCustomerById(customerId);

        LocalDate startDate = InputHelper.readDate(scanner, "Enter the bundle start date", true);
        int duration = InputHelper.readInt(scanner, "Enter the duration of the stay in days", 1, 60);
        LocalDate endDate = startDate.plusDays(duration);
        if (hasOverlappingBundle(customer, startDate, endDate)) {
            throw new OverLappingBookingException("Customer " + customer.getName() + " (ID " + customer.getId()
                    + ") already has a bundle booked between " + startDate + " and " + endDate
                    + " that overlaps with these dates. A family member cannot be on two bundles at once - please choose different dates.");
        }


        TravelBundle bundle = new TravelBundle(nextBundleId++, customer, startDate, duration);

        int numfamilyMember = InputHelper.readInt(scanner, "Enter the number of family members joining this bundle (max 5)", 0, 5);
        for (int i = 1; i <= numfamilyMember; i++) {
            System.out.println("Details for family member " + i + " of " + numfamilyMember + ":");
            String familyMemberName = InputHelper.readNonEmptyString(scanner, "  Enter family member's name");
            SkillLevel familyMemberLevel = InputHelper.readSkillLevel(scanner, "  Enter family member's skill level");
            FamilyMember familyMember = new FamilyMember(familyMemberName, familyMemberLevel);
            bundle.addFamilyMember(familyMember);
        }

        addAccommodation(bundle);

        bundles.add(bundle);
        System.out.println("Bundle created successfully:");
        System.out.println(bundle);
    }

    /**
     * Searches for accommodation that can hold everyone in the bundle and is
     * available for the bundle's dates, shows it to the user, and attaches
     * the chosen accommodation ID to the bundle. Reserving the accommodation
     * makes it unavailable for those dates only.
     */
    public void addAccommodation(TravelBundle bundle) throws AccommodationNotAvailableException {
        int people = bundle.totalFamilyMembers();
        LocalDate start = bundle.getStartDate();
        LocalDate end = bundle.getEndDate();

        List<Accommodation> available = findAvailableAccommodations(start, end, people);
        if (available.isEmpty()) {
            throw new AccommodationNotAvailableException(
                    "No accommodation can hold " + people + " people for " + start + " to " + end + ".");
        }

        System.out.println("\nAccommodations available for " + people + " people (" + start + " to " + end + "):");
        for (Accommodation a : available) {
            System.out.println(a);
        }

        while (true) {
            String idInput = InputHelper.readNonEmptyString(scanner, "Enter the ID of the accommodation to book");
            Accommodation chosen = available.stream()
                    .filter(a -> a.getId().equalsIgnoreCase(idInput))
                    .findFirst()
                    .orElse(null);
            if (chosen == null) {
                System.out.println("That ID was not in the list of available accommodations shown above. Please try again.");
                continue;
            }
            chosen.addReservation(start, end);
            bundle.setAccommodation(chosen);
            System.out.println("Accommodation " + chosen.getId() + " has been attached to the bundle.");
            return;
        }
    }


    //  List bundles 

    public void listBundles() {
        if (bundles.isEmpty()) {
            System.out.println("--- All Travel Bundles ---");
            System.out.println("There are no bundles in the system.");
            return;
        }
        System.out.println("\n--- All Travel Bundles ---");
        for (TravelBundle b : bundles) {
            System.out.println(b);
        }
    }


    //  Add a lift pass to bundle

    public void addLiftPass() throws RecordNotFoundException {
        TravelBundle bundle = selectBundle();
        String holder = selectPersonInBundle(bundle);

        System.out.println("Choose lift pass type: 1-Single lift(s) at $" + LiftPass.SINGLE_LIFT_RATE + "/day, "
                        + "2-Season unlimited pass (" + LiftPass.SEASON_DAYS +
                        " days) at $" + LiftPass.SEASON_UNLIMITED_PRICE + " flat");
        int choice = InputHelper.readInt(scanner, "Enter your choice", 1, 2);

        if (choice == 1) {
            int days = InputHelper.readInt(scanner, "Enter the number of days for the single lift pass for " + holder, 1, 30);
            bundle.addLiftPass(new LiftPass(holder, LiftPassType.DAY, days));
        } else {
            bundle.addLiftPass(new LiftPass(holder, LiftPassType.SEASON, LiftPass.SEASON_DAYS));
        }
        System.out.println("Lift pass added. Bundle lift pass total is now $" + String.format("%.2f", bundle.liftPassTotal()));
    }


    // Search / helper methods

    public Customer findCustomerById(int id) throws RecordNotFoundException {
        return customers.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RecordNotFoundException("No customer found with ID " + id + "."));
    }

    public Accommodation findAccommodationById(String id) throws RecordNotFoundException {
        return accommodations.stream()
                .filter(a -> a.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElseThrow(() -> new RecordNotFoundException("No accommodation found with ID " + id + "."));
    }

    /**
     * True if customer already has a bundle whose [start, end] date range
     * overlaps the given range.
     */
    private boolean hasOverlappingBundle(Customer customer, LocalDate startDate, LocalDate endDate) {
        return bundles.stream()
                .filter(b -> b.getCustomer().getId() == customer.getId())
                .anyMatch(b -> !startDate.isAfter(b.getEndDate()) && !b.getStartDate().isAfter(endDate));
    }

    private TravelBundle selectBundle() throws RecordNotFoundException {
        if (bundles.isEmpty()) {
            throw new RecordNotFoundException("There are no bundles yet. Please create one first.");
        }
        listBundles();
        int bundleId = InputHelper.readInt(scanner, "Enter the bundle ID", 1, Integer.MAX_VALUE);
        return bundles.stream()
                .filter(b -> b.getId() == bundleId)
                .findFirst()
                .orElseThrow(() -> new RecordNotFoundException("No bundle found with ID " + bundleId + "."));
    }

    private String selectPersonInBundle(TravelBundle bundle) {
        System.out.println("People in this bundle: \n1-" + bundle.getCustomer().getName() + " (customer)");
        List<FamilyMember> familyMembers = bundle.getFamilyMembers();
        for (int i = 0; i < familyMembers.size(); i++) {
            System.out.println((i + 2) + "-" + familyMembers.get(i).getName() + " (family member)");
        }
        int choice = InputHelper.readInt(scanner, "Select the person", 1, familyMembers.size() + 1);
        if (choice == 1) {
            return bundle.getCustomer().getName();
        }
        return familyMembers.get(choice - 2).getName();
    }
}
