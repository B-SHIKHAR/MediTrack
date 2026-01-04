package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.entity.enums.AppointmentStatus;

import java.time.LocalDateTime;

public class Appointment implements Cloneable {
    private long id;
    private Doctor doctor;
    private Patient patient;
    private LocalDateTime dateTime;
    private AppointmentStatus status;

    public Appointment(long id, Doctor doctor, Patient patient, LocalDateTime dateTime, AppointmentStatus status) {
        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.dateTime = dateTime;
        this.status = status;
    }

    public long getId() { return id; }
    public Doctor getDoctor() { return doctor; }
    public Patient getPatient() { return patient; }
    public LocalDateTime getDateTime() { return dateTime; }
    public AppointmentStatus getStatus() { return status; }
    public void setStatus(AppointmentStatus status) { this.status = status; }

    // Deep copy: clone nested Doctor/Patient references to avoid shared mutable state
    @Override
    public Appointment clone() {
        try {
            Appointment copy = (Appointment) super.clone();
            Doctor d = new Doctor(doctor.getId(), doctor.getName(), doctor.getAge(), doctor.getPhone(),
                    doctor.getSpecialization(), doctor.getConsultationFee());
            Patient p = patient.clone(); // Patient implements Cloneable
            copy.doctor = d;
            copy.patient = p;
            // LocalDateTime is immutable; reuse safely
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
