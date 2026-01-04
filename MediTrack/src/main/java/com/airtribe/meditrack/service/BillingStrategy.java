package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Appointment;

public interface BillingStrategy {
    Bill generate(Appointment appointment, Doctor doctor);
}
