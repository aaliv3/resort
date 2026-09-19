package com.resort;

/**
 * Thrown when a lookup by id (customer, accommodation, bundle) finds nothing.
 */
public class RecordNotFoundException extends Exception {
    public RecordNotFoundException(String message) {
        super(message);
    }
}
