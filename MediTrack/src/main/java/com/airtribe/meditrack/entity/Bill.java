package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.contract.Payable;

public class Bill implements Payable {
    private final long id;
    private final long appointmentId;
    private double baseAmount;
    private double taxAmount;
    private double totalAmount;

    public Bill(long id, long appointmentId, double baseAmount) {
        this.id = id;
        this.appointmentId = appointmentId;
        this.baseAmount = baseAmount;
        this.taxAmount = baseAmount * Constants.TAX_RATE;
        this.totalAmount = this.baseAmount + this.taxAmount;
    }

    public long getId() { return id; }
    public long getAppointmentId() { return appointmentId; }
    public double getBaseAmount() { return baseAmount; }
    public double getTaxAmount() { return taxAmount; }
    public double getTotalAmount() { return totalAmount; }

    // Overriding generateBill-like behavior can be modeled in strategies/services;
    // Payable serves for polymorphic billing calculation.
    @Override
    public double calculateAmount() {
        return totalAmount;
    }
}
