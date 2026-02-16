package com.example.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a library member who can borrow books.
 * Each member has a unique ID and name.
 * 
 * @author Library System
 * @version 1.0
 */
public class Member {

    private static final Logger logger = LoggerFactory.getLogger(Member.class);
    
    private final int id;
    private final String name;

    /**
     * Creates a new library member with the specified ID and name.
     * 
     * @param id the unique identifier for the member
     * @param name the name of the member
     * @throws IllegalArgumentException if id is negative or name is null/empty
     */
    public Member(int id, String name) {
        if (id < 0) {
            logger.error("Attempted to create member with negative ID: {}", id);
            throw new IllegalArgumentException("Member ID cannot be negative");
        }
        if (name == null || name.trim().isEmpty()) {
            logger.error("Attempted to create member with null or empty name");
            throw new IllegalArgumentException("Member name cannot be null or empty");
        }
        
        this.id = id;
        this.name = name.trim();
        
        logger.debug("Created new member: ID={}, Name='{}'", id, name);
    }

    /**
     * Gets the unique identifier of this member.
     * 
     * @return the member ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of this member.
     * 
     * @return the member name
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Member{id=" + id + ", name='" + name + "'}";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Member member = (Member) obj;
        return id == member.id;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
