package org.example.astraqa.utils;

public class CommonUtils {
    public static String generateUniqueName(String base) {
        return base.replace(" ", "_")
                .replaceAll("[^a-zA-Z0-9_-]", "");
    }

    public static double parsePrice(String priceText) {
        try {
            return Double.parseDouble(priceText.replaceAll("[^\\d.]", ""));
        } catch (NumberFormatException e) {
            LoggingUtils.logError("Invalid price format: " + priceText, e);
            return 0.0;
        }
    }
}
