package org.example.astraqa;

import com.microsoft.playwright.*;
import org.testng.annotations.Test;

public class GoogleTest {

    @Test
    public void testGoogleSearch() {
        try (Playwright playwright = Playwright.create()) {

            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
            BrowserContext context = browser.newContext(new Browser.NewContextOptions().setLocale("en-US"));
            Page page = context.newPage();

            page.navigate("https://www.google.com");
            Locator searchField = page.locator("textarea[role='combobox']");
            searchField.fill("Test Automation");
            Locator buttonSearch = page.locator("div.FPdoLc input[name='btnK']");
            buttonSearch.click();
            page.waitForSelector("h3");

            String title = page.title();
            assert title.contains("Test Automation");

            browser.close();
        }
    }
}