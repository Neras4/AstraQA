package org.example.astraqa.utils;

import com.microsoft.playwright.*;

public class BrowserManager {
    private static Playwright playwright;
    private static Browser browser;

    public static Browser getBrowser() {
        try {
            if (playwright == null) {
                playwright = Playwright.create();
                LoggingUtils.logInfo("Playwright instance created.");
            }
            if (browser == null) {
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                        .setHeadless(false));
                LoggingUtils.logInfo("Browser launched.");
            }
            return browser;
        } catch (PlaywrightException e) {
            LoggingUtils.logError("Failed to launch Playwright or browser.", e);
            throw new RuntimeException("Failed to initialize browser.", e);
        }
    }

    public static Page createPage() {
        try {
            Page page = getBrowser().newPage();
            LoggingUtils.logInfo("New page created.");
            return page;
        } catch (Exception e) {
            LoggingUtils.logError("Failed to create new page.", e);
            throw new RuntimeException("Failed to create page.", e);
        }
    }

    public static void closeBrowser() {
        try {
            if (browser != null) {
                browser.close();
                browser = null;
                LoggingUtils.logInfo("Browser closed.");
            }
            if (playwright != null) {
                playwright.close();
                playwright = null;
                LoggingUtils.logInfo("Playwright instance closed.");
            }
        } catch (Exception e) {
            LoggingUtils.logError("Failed to close browser or playwright.", e);
            throw new RuntimeException("Failed to close browser or playwright.", e);
        }
    }
}
