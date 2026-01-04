package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.entity.enums.AppointmentStatus;
import com.airtribe.meditrack.entity.enums.Specialization;
import com.airtribe.meditrack.util.IdGenerator;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BillingStrategyTest {
    private Doctor doctor;
    private Patient patient;
    private Appointment appointment;

    @BeforeEach
    void setUp() {
        doctor = new Doctor(IdGenerator.nextId(), "Dr. Rao", 45, "99999",
                Specialization.CARDIOLOGY, 1000.0);
        patient = new Patient(IdGenerator.nextId(), "Anita", 30, "88888", "Bengaluru", "Hypertension");
        appointment = new Appointment(IdGenerator.nextId(), doctor, patient,
                LocalDateTime.now().plusDays(1), AppointmentStatus.CONFIRMED);
    }

    @Test
    void testStandardBillingStrategy() {
        BillingStrategy strat = new StandardBillingStrategy();
        Bill bill = strat.generate(appointment, doctor);
        assertTrue(bill.calculateAmount() > 1000.0); // includes tax
    }

    @Test
    void testDiscountBillingStrategy() {
        BillingStrategy strat = new DiscountBillingStrategy(20.0);
        Bill bill = strat.generate(appointment, doctor);
        assertTrue(bill.calculateAmount() < 1000.0 * 1.18); // discounted base + tax
    }
}
