package com.resort;

import java.util.Scanner;

public class MtBullerAdmin {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MtBullerResort resort = new MtBullerResort(scanner);
        resort.loadInitialData();

        System.out.println("==========================================");
        System.out.println("Welcome to Mt Buller Resort Bundle System");
        

        boolean running = true;
        while (running) {
            printMenu();
            int choice = InputHelper.readInt(scanner, "Please select an option", 1, 8);
            try {
                switch (choice) {
                    case 1 -> resort.displayAllAccommodations(); 
                    case 2 -> resort.displayAvailableAccommodations();
                    case 3 -> resort.addCustomer();
                    case 4 -> resort.listCustomers();
                    case 5 -> resort.addBundle();
                    case 6 -> resort.listBundles();
                    case 7 -> resort.addLiftPass();
                    case 8 -> running = false;
                    default -> System.out.println("Invalid choice. Please try again.");
                }   
            } catch (RecordNotFoundException | AccommodationNotAvailableException | OverLappingBookingException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
    }
    
    private static void printMenu() {
        System.out.println("-------------Main Menu-------------");
        System.out.println("1. Display all accommodations");
        System.out.println("2. View available Accommodations");
        System.out.println("3. Add Customer");
        System.out.println("4. List customers");
        System.out.println("5. Create bundle");
        System.out.println("6. List bundles");
        System.out.println("7. Add lift pass to bundle");
        System.out.println("8. Quit");
    }
}