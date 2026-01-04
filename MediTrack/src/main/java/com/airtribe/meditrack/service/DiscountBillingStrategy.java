package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.util.IdGenerator;

public class DiscountBillingStrategy implements BillingStrategy {
    private final double discountPercent; // primitive

    public DiscountBillingStrategy(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    @Override
    public Bill generate(Appointment appointment, Doctor doctor) {
        double base = doctor.getConsultationFee();
        double discountedBase = base * (1.0 - (discountPercent / 100.0)); // primitive + casting demo
        return new Bill(IdGenerator.nextId(), appointment.getId(), discountedBase);
    }
}
