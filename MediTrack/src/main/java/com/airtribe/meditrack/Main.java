package com.airtribe.meditrack;

import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.entity.enums.AppointmentStatus;
import com.airtribe.meditrack.entity.enums.Specialization;
import com.airtribe.meditrack.service.*;
import com.airtribe.meditrack.util.IdGenerator;
import com.airtribe.meditrack.util.DateUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final DoctorService doctorService = new DoctorService(); // static vs instance
    private static final PatientService patientService = new PatientService();
    private static final AppointmentService appointmentService = new AppointmentService();
    private static BillingStrategy billingStrategy = new StandardBillingStrategy();

    public static void main(String[] args) {
        boolean loadData = false;
        for (String arg : args) {
            if ("--loadData".equals(arg)) loadData = true;
        }
        if (loadData) preload();

        Scanner sc = new Scanner(System.in);
        System.out.println("Clinic & Appointment Management");
        int choice;
        do {
            menu();
            choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1 -> addDoctor(sc);
                case 2 -> addPatient(sc);
                case 3 -> createAppointment(sc);
                case 4 -> viewAppointments();
                case 5 -> cancelAppointment(sc);
                case 6 -> searchDoctor(sc);
                case 7 -> searchPatient(sc);
                case 8 -> billingDemo(sc);
                case 9 -> analyticsDemo(sc);
                case 10 -> persistAll();
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice");
            }
        } while (choice != 0);
    }

    private static void menu() {
        System.out.println("""
            1. Add Doctor
            2. Add Patient
            3. Create Appointment
            4. View Appointments
            5. Cancel Appointment
            6. Search Doctor
            7. Search Patient
            8. Generate Bill
            9. Analytics (avg fee, appts per doctor)
            10. Save to CSV
            0. Exit
            """);
    }

    private static void preload() {
        // Load existing CSV data
        List<Doctor> dLoaded = PersistenceService.loadDoctors();
        dLoaded.forEach(doctorService::createDoctor);
        List<Patient> pLoaded = PersistenceService.loadPatients();
        pLoaded.forEach(patientService::createPatient);
        List<Appointment> aLoaded = PersistenceService.loadAppointments(dLoaded, pLoaded);
        aLoaded.forEach(appointmentService::create);
        System.out.println("Data loaded.");
    }

    private static void addDoctor(Scanner sc) {
        System.out.print("Name: "); String name = sc.nextLine();
        System.out.print("Age: "); int age = Integer.parseInt(sc.nextLine());
        System.out.print("Phone: "); String phone = sc.nextLine();
        System.out.print("Specialization (GENERAL/PEDIATRICS/DERMATOLOGY/ORTHOPEDICS/CARDIOLOGY): ");
        Specialization spec = Specialization.valueOf(sc.nextLine().trim().toUpperCase());
        System.out.print("Fee: "); double fee = Double.parseDouble(sc.nextLine());

        Doctor d = new Doctor(IdGenerator.nextId(), name, age, phone, spec, fee);
        doctorService.createDoctor(d);
        System.out.println("Doctor added: " + d.summary());
    }

    private static void addPatient(Scanner sc) {
        System.out.print("Name: "); String name = sc.nextLine();
        System.out.print("Age: "); int age = Integer.parseInt(sc.nextLine());
        System.out.print("Phone: "); String phone = sc.nextLine();
        System.out.print("Address: "); String address = sc.nextLine();
        String notes = "Initial notes";
        Patient p = new Patient(IdGenerator.nextId(), name, age, phone, address, notes);
        patientService.createPatient(p);
        System.out.println("Patient added: " + p.summary());
    }

    private static void createAppointment(Scanner sc) {
        System.out.print("Doctor ID: "); long did = Long.parseLong(sc.nextLine());
        System.out.print("Patient ID: "); long pid = Long.parseLong(sc.nextLine());
        System.out.print("DateTime (yyyy-MM-dd HH:mm): "); String dtStr = sc.nextLine();
        Optional<Doctor> d = doctorService.getById(did);
        Optional<Patient> p = patientService.getById(pid);
        if (d.isEmpty() || p.isEmpty()) {
            System.out.println("Invalid doctor/patient ID");
            return;
        }
        LocalDateTime dt = DateUtil.parse(dtStr);
        Appointment a = new Appointment(IdGenerator.nextId(), d.get(), p.get(), dt, AppointmentStatus.PENDING);
        appointmentService.create(a);
        System.out.println("Appointment created: #" + a.getId());
    }

    private static void viewAppointments() {
        appointmentService.getAll().forEach(a ->
            System.out.println("#" + a.getId() + " " + a.getDoctor().getName() + " with " + a.getPatient().getName()
                + " at " + a.getDateTime() + " [" + a.getStatus() + "]"));
    }

    private static void cancelAppointment(Scanner sc) {
        System.out.print("Appointment ID: "); long id = Long.parseLong(sc.nextLine());
        try {
            Appointment a = appointmentService.cancel(id);
            System.out.println("Cancelled: #" + a.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void searchDoctor(Scanner sc) {
        System.out.print("Name: "); String name = sc.nextLine();
        doctorService.searchByName(name).forEach(d -> System.out.println(d.summary()));
    }

    private static void searchPatient(Scanner sc) {
        System.out.print("Search by (id/name/age): "); String mode = sc.nextLine().trim().toLowerCase();
        switch (mode) {
            case "id" -> {
                System.out.print("ID: "); long id = Long.parseLong(sc.nextLine());
                patientService.searchPatient(id).ifPresentOrElse(
                    p -> System.out.println(p.summary()),
                    () -> System.out.println("Not found")
                );
            }
            case "name" -> {
                System.out.print("Name: "); String name = sc.nextLine();
                patientService.searchPatient(name).forEach(p -> System.out.println(p.summary()));
            }
            case "age" -> {
                System.out.print("Age: "); int age = Integer.parseInt(sc.nextLine());
                patientService.searchPatient(age).forEach(p -> System.out.println(p.summary()));
            }
            default -> System.out.println("Invalid mode");
        }
    }

    private static void billingDemo(Scanner sc) {
        System.out.print("Appointment ID: "); long aid = Long.parseLong(sc.nextLine());
        Appointment a = appointmentService.getById(aid).orElse(null);
        if (a == null) {
            System.out.println("Appointment not found");
            return;
        }
        Doctor d = a.getDoctor();
        System.out.print("Use discount? (y/n): "); String ans = sc.nextLine();
        if (ans.equalsIgnoreCase("y")) {
            System.out.print("Discount %: "); double pct = Double.parseDouble(sc.nextLine());
            billingStrategy = new DiscountBillingStrategy(pct);
        } else {
            billingStrategy = new StandardBillingStrategy();
        }
        Bill bill = billingStrategy.generate(a, d);
        BillSummary sum = new BillSummary(bill.getId(), bill.getAppointmentId(), bill.getTotalAmount(),
                a.getPatient().getName(), d.getName());
        // Dynamic dispatch: Payable.calculateAmount on Bill instance
        System.out.println("Bill Amount: " + bill.calculateAmount());
        System.out.println("Summary -> Bill#" + sum.getBillId() + " Total: " + sum.getTotalAmount());
    }

    private static void analyticsDemo(Scanner sc) {
        System.out.print("Specialization: "); Specialization spec = Specialization.valueOf(sc.nextLine().trim().toUpperCase());
        double avg = doctorService.averageFeeBySpecialization(spec);
        System.out.println("Average fee for " + spec + ": " + avg);
        System.out.print("Doctor ID: "); long did = Long.parseLong(sc.nextLine());
        long count = appointmentService.appointmentsCountForDoctor(did);
        System.out.println("Appointments for Doctor#" + did + ": " + count);
    }

    private static void persistAll() {
        PersistenceService.saveDoctors(doctorService.getAll());
        PersistenceService.savePatients(patientService.getAll());
        PersistenceService.saveAppointments(appointmentService.getAll());
        System.out.println("Data saved.");
    }
}
