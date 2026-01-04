package com.airtribe.meditrack.entity;

// Immutable, thread-safe summary
public final class BillSummary {
    private final long billId;
    private final long appointmentId;
    private final double totalAmount;
    private final String patientName;
    private final String doctorName;

    public BillSummary(long billId, long appointmentId, double totalAmount, String patientName, String doctorName) {
        this.billId = billId;
        this.appointmentId = appointmentId;
        this.totalAmount = totalAmount;
        this.patientName = patientName;
        this.doctorName = doctorName;
    }

    public long getBillId() { return billId; }
    public long getAppointmentId() { return appointmentId; }
    public double getTotalAmount() { return totalAmount; }
    public String getPatientName() { return patientName; }
    public String getDoctorName() { return doctorName; }
}
