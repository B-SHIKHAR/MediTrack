package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.util.IdGenerator;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PatientServiceTest {
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        patientService = new PatientService();
    }

    @Test
    void testCreateAndSearchPatientById() {
        Patient p = new Patient(IdGenerator.nextId(), "Anita", 30, "88888", "Bengaluru", "Notes");
        patientService.createPatient(p);

        Optional<Patient> found = patientService.searchPatient(p.getId());
        assertTrue(found.isPresent());
        assertEquals("Anita", found.get().getName());
    }

    @Test
    void testSearchPatientByAge() {
        patientService.createPatient(new Patient(IdGenerator.nextId(), "Ravi", 25, "77777", "Delhi", ""));
        patientService.createPatient(new Patient(IdGenerator.nextId(), "Kiran", 25, "66666", "Mumbai", ""));

        List<Patient> result = patientService.searchPatient(25);
        assertEquals(2, result.size());
    }
}
