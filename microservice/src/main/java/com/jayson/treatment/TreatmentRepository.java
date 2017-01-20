package com.jayson.treatment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Created by jayson on 11/5/16.
 */
public interface TreatmentRepository extends JpaRepository<Treatment, Long> {
    Collection<Treatment> findByPatientUsername(String username);
}
