package com.airtribe.meditrack.entity;

public class Patient extends Person implements Cloneable {
    private String address;
    private String notes;

    public Patient(long id, String name, int age, String phone, String address, String notes) {
        super(id, name, age, phone);
        this.address = address;
        this.notes = notes;
    }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    @Override
    public String summary() {
        return "Patient#" + getId() + " " + getName() + " [" + address + "]";
    }

    // Deep vs Shallow Copy demonstration: Patient has only primitives/Strings (immutable),
    // but we still implement clone to show semantics.
    @Override
    public Patient clone() {
        try {
            return (Patient) super.clone(); // safe since fields are primitives/Strings
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
