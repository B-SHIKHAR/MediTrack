package com.airtribe.meditrack.util;

import com.airtribe.meditrack.exception.InvalidDataException;

public final class Validator {
    private Validator() {}

    public static void requireNonBlank(String s, String field) {
        if (s == null || s.trim().isEmpty()) throw new InvalidDataException(field + " cannot be blank");
    }
    public static void requirePositive(double n, String field) {
        if (n <= 0) throw new InvalidDataException(field + " must be positive");
    }
    public static void requireNonNull(Object o, String field) {
        if (o == null) throw new InvalidDataException(field + " cannot be null");
    }
    public static void requireAge(int age) {
        if (age < 0 || age > 120) throw new InvalidDataException("Invalid age");
    }
}
