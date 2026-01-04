package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.entity.enums.AppointmentStatus;
import com.airtribe.meditrack.entity.enums.Specialization;
import com.airtribe.meditrack.util.IdGenerator;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentServiceTest {
    private AppointmentService appointmentService;
    private Doctor doctor;
    private Patient patient;

    @BeforeEach
    void setUp() {
        appointmentService = new AppointmentService();
        doctor = new Doctor(IdGenerator.nextId(), "Dr. Rao", 45, "99999",
                Specialization.CARDIOLOGY, 800.0);
        patient = new Patient(IdGenerator.nextId(), "Anita", 30, "88888", "Bengaluru", "Hypertension");
    }

    @Test
    void testCreateAndCancelAppointment() {
        Appointment a = new Appointment(IdGenerator.nextId(), doctor, patient,
                LocalDateTime.now().plusDays(1), AppointmentStatus.CONFIRMED);
        appointmentService.create(a);

        Appointment cancelled = appointmentService.cancel(a.getId());
        assertEquals(AppointmentStatus.CANCELLED, cancelled.getStatus());
    }

    @Test
    void testAppointmentsCountForDoctor() {
        appointmentService.create(new Appointment(IdGenerator.nextId(), doctor, patient,
                LocalDateTime.now().plusDays(1), AppointmentStatus.CONFIRMED));
        appointmentService.create(new Appointment(IdGenerator.nextId(), doctor, patient,
                LocalDateTime.now().plusDays(2), AppointmentStatus.CONFIRMED));

        long count = appointmentService.appointmentsCountForDoctor(doctor.getId());
        assertEquals(2, count);
    }
}
