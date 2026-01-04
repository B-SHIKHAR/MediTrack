package com.airtribe.meditrack.entity;

public class Person extends MedicalEntity {
    private String name;
    private int age; // primitive
    private String phone;

    public Person(long id, String name, int age, String phone) {
        super(id); // use super
        this.name = name;
        this.age = age;
        this.phone = phone;
    }

    public Person(long id, String name) {
        this(id, name, 0, ""); // constructor chaining via this
    }

    // Encapsulated private fields + getters/setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    @Override
    public String summary() {
        return "Person#" + id + " " + name + " (" + age + ")";
    }
}
