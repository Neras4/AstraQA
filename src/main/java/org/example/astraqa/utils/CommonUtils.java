package org.example.astraqa.utils;

import java.util.UUID;

public class CommonUtils {
    public static String generateUniqueName(String base) {
        return base.replace(" ", "_")
                .replaceAll("[^a-zA-Z0-9_-]", "");
    }
}
