package com.jayson.treatment;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created by jayson on 11/5/16.
 */
@Entity
public class Treatment {

    public String alarm;
    //HHD or APD
    public String modality;
    public Date treatmentDateTime;

    @JsonIgnore
    @ManyToOne
    private Patient patient;

    @Id
    @GeneratedValue
    private Long id;

    Treatment() {
        // jpa only
    }

    public Treatment(Patient patient, String alarm, String modality, Date treatmentDateTime) {
        this.alarm = alarm;
        this.modality = modality;
        this.treatmentDateTime = treatmentDateTime;
        this.patient = patient;
    }

    public String getAlarm() {
        return alarm;
    }

    public String getModality() {
        return modality;
    }

    public Date getTreatmentDateTime() {
        return treatmentDateTime;
    }

    public Patient getPatient() {
        return patient;
    }

    public Long getId() {

        return id;
    }
}
