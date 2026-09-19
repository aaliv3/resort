package com.resort;

/**
 * Thrown when a customer already has a bundle whose dates overlap with the
 * dates being requested for a new bundle. A customer cannot be on two trips
 * at once, regardless of which family members are involved in each one.
 */
public class OverLappingBookingException extends Exception {
    public OverLappingBookingException(String message) {
        super(message);
    }
}
