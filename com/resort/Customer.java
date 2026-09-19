package com.resort;

import java.util.List;

public class Customer {
    private final int id;
    private final String name;
    private final String contactInfo;
    private final SkillLevel level;


    public Customer(int id, String name, String contactInfo, SkillLevel level) {
        this.id = id;
        this.name = name;
        this.contactInfo = contactInfo;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public SkillLevel getSkillLevel() {
        return level;
    }

    public String getContactInfo() {
        return contactInfo;
    }


    @Override 
    public String toString() {
        return String.format("Customer{id=%d, name='%s', contactInfo='%s', skillLevel=%s}", id, name, contactInfo, level);
    }
}
