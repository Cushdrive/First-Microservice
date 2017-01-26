package com.jayson.treatment;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

/**
 * Created by jayson on 11/5/16.
 */
@RestController
@RequestMapping("/api/{patientId}/treatments")
public class TreatmentRestController {
    private static final Logger log = LoggerFactory.getLogger(TreatmentRestController.class);
    private final TreatmentRepository treatmentRepository;
    private final PatientRepository patientRepository;

    @Autowired
    TreatmentRestController(TreatmentRepository treatmentRepository,
                            PatientRepository patientRepository) {
        this.treatmentRepository = treatmentRepository;
        this.patientRepository = patientRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Treatment> readTreatments(@PathVariable String patientId) {
        try {
            this.validateUser(patientId);
        } catch (PatientNotFoundException e) {
            e.printStackTrace();
        }
        return this.treatmentRepository.findByPatientUsername(patientId);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable String patientId, @RequestBody Treatment input) {
        try {
            this.validateUser(patientId);
        } catch (PatientNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }

        return this.patientRepository
                .findByUsername(patientId)
                .map(account -> {
                    Treatment result = treatmentRepository.save(new Treatment(account, input.alarm, input.modality, input.treatmentDateTime));

                    URI location = ServletUriComponentsBuilder
                            .fromCurrentRequest().path("/{id}")
                            .buildAndExpand(result.getId()).toUri();

                    return ResponseEntity.created(location).build();
                })
                .orElse(ResponseEntity.noContent().build());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{treatmentId}")
    Treatment readTreatment(@PathVariable String patientId, @PathVariable Long treatmentId, HttpServletRequest request) {
        try {
            this.validateUser(patientId);
        } catch (PatientNotFoundException e) {
            e.printStackTrace();
        }
        log.info("Servicing request for: " + request.getRequestURI());
        Treatment temp = this.treatmentRepository.findOne(treatmentId);
        if (patientId.compareTo(temp.getPatient().getUsername().toString()) == 0) {
            return temp;
        }
        else
        {
            throw new TreatmentNotFoundException(treatmentId.toString());
        }
    }

    private void validateUser(String patientId) throws PatientNotFoundException {
        this.patientRepository.findByUsername(patientId).orElseThrow(
                () -> new PatientNotFoundException(patientId.toString()));
    }
}
