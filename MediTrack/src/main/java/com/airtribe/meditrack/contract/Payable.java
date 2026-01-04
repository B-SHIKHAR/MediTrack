package com.airtribe.meditrack.contract;

public interface Payable {
    double calculateAmount();
    default String paymentSummary() {
        return "Payment processed for amount.";
    }
}
