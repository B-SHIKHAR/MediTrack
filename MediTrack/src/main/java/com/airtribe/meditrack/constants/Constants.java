package com.airtribe.meditrack.constants;

public final class Constants {
    public static final double TAX_RATE = 0.18; // primitive + casting demo later
    public static final String PATIENT_CSV = "data/patients.csv";
    public static final String DOCTOR_CSV = "data/doctors.csv";
    public static final String APPOINTMENT_CSV = "data/appointments.csv";

    static {
        // Static block demo: app-wide config, could read env/system props
        System.out.println("[Config] Constants initialized.");
    }

    private Constants() {}
}
