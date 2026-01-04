package com.airtribe.meditrack.service;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.entity.enums.AppointmentStatus;
import com.airtribe.meditrack.entity.enums.Specialization;
import com.airtribe.meditrack.util.CSVUtil;
import com.airtribe.meditrack.util.DateUtil;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class PersistenceService {

    public static void saveDoctors(List<Doctor> doctors) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(Constants.DOCTOR_CSV))) {
            for (Doctor d : doctors) {
                pw.println(CSVUtil.join(
                    String.valueOf(d.getId()), d.getName(), String.valueOf(d.getAge()), d.getPhone(),
                    d.getSpecialization().name(), String.valueOf(d.getConsultationFee())
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Doctor> loadDoctors() {
        List<Doctor> list = new ArrayList<>();
        File f = new File(Constants.DOCTOR_CSV);
        if (!f.exists()) return list;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<String> parts = CSVUtil.parseLine(line);
                long id = Long.parseLong(parts.get(0));
                String name = parts.get(1);
                int age = Integer.parseInt(parts.get(2));
                String phone = parts.get(3);
                Specialization spec = Specialization.valueOf(parts.get(4));
                double fee = Double.parseDouble(parts.get(5));
                list.add(new Doctor(id, name, age, phone, spec, fee));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void savePatients(List<Patient> patients) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(Constants.PATIENT_CSV))) {
            for (Patient p : patients) {
                pw.println(CSVUtil.join(
                    String.valueOf(p.getId()), p.getName(), String.valueOf(p.getAge()), p.getPhone(),
                    p.getAddress(), p.getNotes()
                ));
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static List<Patient> loadPatients() {
        List<Patient> list = new ArrayList<>();
        File f = new File(Constants.PATIENT_CSV);
        if (!f.exists()) return list;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<String> parts = CSVUtil.parseLine(line);
                long id = Long.parseLong(parts.get(0));
                String name = parts.get(1);
                int age = Integer.parseInt(parts.get(2));
                String phone = parts.get(3);
                String address = parts.get(4);
                String notes = parts.get(5);
                list.add(new Patient(id, name, age, phone, address, notes));
            }
        } catch (IOException e) { e.printStackTrace(); }
        return list;
    }

    public static void saveAppointments(List<Appointment> appts) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(Constants.APPOINTMENT_CSV))) {
            for (Appointment a : appts) {
                pw.println(CSVUtil.join(
                    String.valueOf(a.getId()),
                    String.valueOf(a.getDoctor().getId()),
                    String.valueOf(a.getPatient().getId()),
                    DateUtil.format(a.getDateTime()),
                    a.getStatus().name()
                ));
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static List<Appointment> loadAppointments(List<Doctor> doctors, List<Patient> patients) {
        List<Appointment> list = new ArrayList<>();
        File f = new File(Constants.APPOINTMENT_CSV);
        if (!f.exists()) return list;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<String> parts = CSVUtil.parseLine(line);
                long id = Long.parseLong(parts.get(0));
                long doctorId = Long.parseLong(parts.get(1));
                long patientId = Long.parseLong(parts.get(2));
                LocalDateTime dt = DateUtil.parse(parts.get(3));
                AppointmentStatus status = AppointmentStatus.valueOf(parts.get(4));
                Doctor d = doctors.stream().filter(x -> x.getId() == doctorId).findFirst().orElse(null);
                Patient p = patients.stream().filter(x -> x.getId() == patientId).findFirst().orElse(null);
                if (d != null && p != null) {
                    list.add(new Appointment(id, d, p, dt, status));
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
        return list;
    }
}
