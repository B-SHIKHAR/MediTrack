package com.airtribe.meditrack.entity;

public abstract class MedicalEntity {
    protected long id;

    protected MedicalEntity(long id) {
        this.id = id;
    }

    public long getId() { return id; }

    public abstract String summary();

    public void touch() {
        // common behavior; could update timestamps
    }
}
