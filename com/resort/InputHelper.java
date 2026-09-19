package com.resort;

import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Reusable, validated console input helpers. Every prompt tells the user
 * exactly what is expected, and re-asks (rather than crashing) on invalid
 * input, so this is where most of the "guide the user" and input-validation
 * requirements are satisfied in one place.
 */
public class InputHelper {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private InputHelper() {
        // utility class - no instances
    }

    public static int readInt(Scanner sc, String prompt, int min, int max) {
        while (true) {
            System.out.printf("%s (whole number between %d and %d): ", prompt, min, max);
            String line = sc.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value < min || value > max) {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("That is not a valid whole number. Please try again.");
            }
        }
    }

    public static String readNonEmptyString(Scanner sc, String prompt) {
        while (true) {
            System.out.printf("%s: ", prompt);
            String line = sc.nextLine().trim();
            if (!line.isEmpty()) {
                return line;
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    public static LocalDate readDate(Scanner sc, String prompt, boolean mustNotBeInPast) {
        while (true) {
            System.out.printf("%s (format yyyy-MM-dd, e.g. 2026-09-20): ", prompt);
            String line = sc.nextLine().trim();
            try {
                LocalDate date = LocalDate.parse(line, DATE_FORMAT);
                if (mustNotBeInPast && date.isBefore(LocalDate.now())) {
                    System.out.println("The date cannot be in the past. Please try again.");
                    continue;
                }
                return date;
            } catch (DateTimeParseException e) {
                System.out.println("That date format was not recognised. Please use yyyy-MM-dd.");
            }
        }
    }

    public static SkillLevel readSkillLevel(Scanner sc, String prompt) {
        while (true) {
            System.out.printf("%s (options: 1-Beginner, 2-Intermediate, 3-Advanced): ", prompt);
            String line = sc.nextLine().trim();
            switch (line) {
                case "1":
                    return SkillLevel.BEGINNER;
                case "2":
                    return SkillLevel.INTERMEDIATE;
                case "3":
                    return SkillLevel.ADVANCED;
                default:
                    System.out.println("Please enter 1, 2, or 3.");
            }
        }
    }

    public static boolean readYesNo(Scanner sc, String prompt) {
        while (true) {
            System.out.printf("%s (y/n): ", prompt);
            String line = sc.nextLine().trim().toLowerCase();
            if (line.equals("y") || line.equals("yes")) {
                return true;
            }
            if (line.equals("n") || line.equals("no")) {
                return false;
            }
            System.out.println("Please answer y or n.");
        }
    }
}
