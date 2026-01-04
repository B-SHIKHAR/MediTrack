package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.Validator;

import java.util.List;
import java.util.Optional;

public class PatientService {
    private final DataStore<Patient> store = new DataStore<>();

    public Patient createPatient(Patient p) {
        Validator.requireNonBlank(p.getName(), "name");
        Validator.requireAge(p.getAge());
        Validator.requireNonBlank(p.getPhone(), "phone");
        store.add(p.getId(), p);
        return p;
    }

    public Optional<Patient> getById(long id) { return store.getById(id); }
    public List<Patient> getAll() { return store.getAll(); }
    public boolean delete(long id) { return store.remove(id); }

    // Overloading: searchPatient() by ID / name / age
    public Optional<Patient> searchPatient(long id) { return getById(id); }
    public List<Patient> searchPatient(String name) {
        return store.filter(p -> p.getName().equalsIgnoreCase(name));
    }
    public List<Patient> searchPatient(int age) {
        return store.filter(p -> p.getAge() == age);
    }
}
