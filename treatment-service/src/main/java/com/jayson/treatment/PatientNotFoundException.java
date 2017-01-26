package com.jayson.treatment;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by jayson on 11/5/16.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PatientNotFoundException extends RuntimeException  {
    public PatientNotFoundException(String patientId) {
        super("Could not find patient '" + patientId + "'.");
    }
}