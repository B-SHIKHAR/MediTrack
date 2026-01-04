package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.enums.AppointmentStatus;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.util.DataStore;

import java.util.List;
import java.util.Optional;

public class AppointmentService {
    private final DataStore<Appointment> store = new DataStore<>();

    public Appointment create(Appointment appt) {
        store.add(appt.getId(), appt);
        return appt;
    }

    public Optional<Appointment> getById(long id) { return store.getById(id); }
    public List<Appointment> getAll() { return store.getAll(); }

    public Appointment cancel(long id) {
        Appointment appt = store.getById(id).orElseThrow(() -> new AppointmentNotFoundException("Appointment not found"));
        appt.setStatus(AppointmentStatus.CANCELLED);
        return appt;
    }

    // Analytics: appointments per doctor via streams
    public long appointmentsCountForDoctor(long doctorId) {
        return store.getAll().stream().filter(a -> a.getDoctor().getId() == doctorId).count();
    }
}
