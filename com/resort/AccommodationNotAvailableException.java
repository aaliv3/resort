package com.resort;

/**
 * Thrown when no accommodation can be found that both fits the group size
 * and is free for the requested dates.
 */
public class AccommodationNotAvailableException extends Exception {
    public AccommodationNotAvailableException(String message) {
        super(message);
    }
}
