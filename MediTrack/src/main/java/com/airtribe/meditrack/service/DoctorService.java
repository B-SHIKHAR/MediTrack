package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.enums.Specialization;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.Validator;

import java.util.*;
import java.util.stream.Collectors;

public class DoctorService {
    private final DataStore<Doctor> store = new DataStore<>();

    public Doctor createDoctor(Doctor d) {
        Validator.requireNonBlank(d.getName(), "name");
        Validator.requireAge(d.getAge());
        Validator.requirePositive(d.getConsultationFee(), "fee");
        store.add(d.getId(), d);
        return d;
    }

    public Optional<Doctor> getById(long id) { return store.getById(id); }
    public List<Doctor> getAll() { return store.getAll(); }
    public boolean delete(long id) { return store.remove(id); }

    public List<Doctor> searchByName(String name) {
        return store.filter(d -> d.getName().equalsIgnoreCase(name));
    }

    public List<Doctor> filterBySpecialization(Specialization spec) {
        return store.filter(d -> d.getSpecialization() == spec);
    }

    // Streams + lambdas: average fee, analytics
    public double averageFeeBySpecialization(Specialization spec) {
        return filterBySpecialization(spec).stream()
            .collect(Collectors.averagingDouble(Doctor::getConsultationFee));
    }
}
