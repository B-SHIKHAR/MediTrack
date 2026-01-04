package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.entity.enums.Specialization;

public class Doctor extends Person {
    private Specialization specialization;
    private double consultationFee; // primitive double

    public Doctor(long id, String name, int age, String phone, Specialization specialization, double fee) {
        super(id, name, age, phone);
        this.specialization = specialization;
        this.consultationFee = fee;
    }

    public Specialization getSpecialization() { return specialization; }
    public void setSpecialization(Specialization specialization) { this.specialization = specialization; }
    public double getConsultationFee() { return consultationFee; }
    public void setConsultationFee(double consultationFee) { this.consultationFee = consultationFee; }

    @Override
    public String summary() {
        return "Doctor#" + getId() + " " + getName() + " [" + specialization + "] Fee: " + consultationFee;
    }
}
