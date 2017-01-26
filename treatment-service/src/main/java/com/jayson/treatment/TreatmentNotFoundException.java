package com.jayson.treatment;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by jayson on 11/6/16.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class TreatmentNotFoundException extends RuntimeException {
    public TreatmentNotFoundException(String message) {
        super("Requested treatment not found: '" + message + "'.");
    }
}
