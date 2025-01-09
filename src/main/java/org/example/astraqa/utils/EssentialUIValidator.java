package org.example.astraqa.utils;

import com.microsoft.playwright.Page;
import org.testng.Assert;

import java.util.List;

public class EssentialUIValidator {
    public static void validateEssentialUIElements(Page page,List<UIElement> uiElements) {
        for (UIElement element : uiElements) {
            try {
                validateElementPresence(page, element.selector(), element.errorMessage());
            } catch (Exception e) {
                LoggingUtils.logError("Error during validation of essential UI element " + element.selector(), e);
                Assert.fail("Failed to validate essential UI element " + element.errorMessage());
            }
        }
    }

    private static void validateElementPresence(Page page, String selector, String errorMessage) {
        if (!page.locator(selector).isVisible()) {
            LoggingUtils.logError(errorMessage, new Exception("Element not found " + selector));
            Assert.fail(errorMessage);
        }
    }
}