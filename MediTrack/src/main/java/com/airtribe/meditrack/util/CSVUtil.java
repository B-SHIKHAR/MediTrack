package com.airtribe.meditrack.util;

import java.util.Arrays;
import java.util.List;

public final class CSVUtil {
    public static List<String> parseLine(String line) {
        return Arrays.asList(line.split(","));
    }
    public static String join(String... parts) {
        return String.join(",", parts);
    }
}
