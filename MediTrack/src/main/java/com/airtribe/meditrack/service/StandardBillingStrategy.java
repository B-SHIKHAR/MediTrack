package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.util.IdGenerator;

public class StandardBillingStrategy implements BillingStrategy {
    @Override
    public Bill generate(Appointment appointment, Doctor doctor) {
        double base = doctor.getConsultationFee();
        // Polymorphism: Bill implements Payable; dynamic dispatch occurs when calculateAmount() is called
        return new Bill(IdGenerator.nextId(), appointment.getId(), base);
    }
}
