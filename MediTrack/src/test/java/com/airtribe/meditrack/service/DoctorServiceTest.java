package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.enums.Specialization;
import com.airtribe.meditrack.util.IdGenerator;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DoctorServiceTest {
    private DoctorService doctorService;

    @BeforeEach
    void setUp() {
        doctorService = new DoctorService();
    }

    @Test
    void testCreateDoctorAndSearchByName() {
        Doctor d = new Doctor(IdGenerator.nextId(), "Dr. Mehta", 40, "99999",
                Specialization.CARDIOLOGY, 1000.0);
        doctorService.createDoctor(d);

        List<Doctor> result = doctorService.searchByName("Dr. Mehta");
        assertEquals(1, result.size());
        assertEquals("Dr. Mehta", result.get(0).getName());
    }

    @Test
    void testAverageFeeBySpecialization() {
        doctorService.createDoctor(new Doctor(IdGenerator.nextId(), "Dr. A", 45, "11111",
                Specialization.CARDIOLOGY, 800.0));
        doctorService.createDoctor(new Doctor(IdGenerator.nextId(), "Dr. B", 50, "22222",
                Specialization.CARDIOLOGY, 1200.0));

        double avg = doctorService.averageFeeBySpecialization(Specialization.CARDIOLOGY);
        assertEquals(1000.0, avg);
    }
}
