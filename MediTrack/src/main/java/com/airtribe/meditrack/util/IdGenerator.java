package com.airtribe.meditrack.util;

import java.util.concurrent.atomic.AtomicLong;

public final class IdGenerator {
    private static final AtomicLong COUNTER = new AtomicLong(1000);

    static {
        System.out.println("[Init] IdGenerator starting at " + COUNTER.get());
    }

    public static long nextId() {
        return COUNTER.incrementAndGet();
    }
}
