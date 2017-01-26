package com.jayson.treatment;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jayson on 11/5/16.
 */
@Entity
public class Patient {

    @OneToMany(mappedBy = "patient")
    private Set<Treatment> treatments = new HashSet<>();

    @Id
    @GeneratedValue
    private Long id;

    public Set<Treatment> getTreatments() {
        return treatments;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @JsonIgnore
    public String password;
    public String username;

    public Patient(String username, String password) {
        this.username = username;
        this.password = password;
    }

    Patient() { //jpa only
    }
}
