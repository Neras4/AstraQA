package org.example.astraqa.utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ActionUtils {
    public static void performActionWithScreenshot(Page page, String description, Runnable action, Locator elementLocator) {
        try {
            LoggingUtils.logInfo(description);
            LoggingUtils.logDebug(String.format("Performing action: %s", description));

            if(!WebTestUtils.waitForElementVisible(elementLocator, 4000)) {
                LoggingUtils.logDebug(String.format("Element not visible for action: %s", description));
                throw new RuntimeException(String.format("Element not visible for action: %s", description));
            }

            highlightElement(elementLocator);

            ScreenshotUtils.takeScreenshot(page,  CommonUtils.generateUniqueName(description) + "_before");
            action.run();

            removeHighlight(elementLocator);

            LoggingUtils.logDebug(String.format("Action performed: %s", description));
        } catch (Exception e) {
            LoggingUtils.logError(String.format("Error during action: %s", description), e);
            throw new RuntimeException(String.format("Failed action: %s", description), e);
        }
    }

    private static void highlightElement(Locator element) {
        if(element.isVisible()) element.evaluate("el => el.style.border = '5px solid red'");
    }

    private static void removeHighlight(Locator element) {
        if(element.isVisible()) element.evaluate("el => el.style.border = 'none'");
    }
}
