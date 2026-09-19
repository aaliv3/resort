package com.resort;

import java.util.ArrayList;
import java.util.List;

public class FamilyMember {
    private static int counter = 1;
    private int id;
    private String name;
    private SkillLevel level;

    public FamilyMember(String name, SkillLevel level) {
        this.id = counter++;
        this.name = name;
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

    @Override
    public String toString() {
        return "FamilyMember{id=" + id + ", name=" + name + ", Skill level=" + level + "}";
    }
}