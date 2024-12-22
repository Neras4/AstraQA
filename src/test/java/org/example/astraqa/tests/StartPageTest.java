package org.example.astraqa.tests;

import io.qameta.allure.*;
import org.example.astraqa.pages.StartPage;
import org.example.astraqa.utils.BrowserManager;
import org.example.astraqa.utils.LoggingUtils;
import org.example.astraqa.utils.ScreenshotUtils;
import org.testng.Assert;
import org.testng.annotations.*;
import com.microsoft.playwright.Page;

@Epic("UI Tests")
@Feature("Start Page Tests")
public class StartPageTest {
    private Page page;

    @BeforeClass
    public void setUp() {
        page = BrowserManager.createPage();
    }

    @AfterClass
    public void tearDown() {
        BrowserManager.closeBrowser();
    }

    @BeforeMethod
    public void setUpTest() {
        try {
            page.navigate("https://www.inmotionhosting.com/");
            page.locator("#onetrust-accept-btn-handler").click();
            LoggingUtils.logInfo("Navigated to Start Page and accepted cookies.");
        } catch (Exception e) {
            LoggingUtils.logError("Error during test setup", e);
            throw new RuntimeException("Failed to set up test", e);
        }
    }

    @AfterMethod
    public void tearDownTest() {
        try {
            page.context().clearCookies();
            LoggingUtils.logInfo("Cookies cleared after test.");
        } catch (Exception e) {
            LoggingUtils.logError("Error clearing cookies", e);
        }
    }

    @Test(description = "Test Compare Plans functionality")
    @Story("Compare hosting plans")
    @Severity(SeverityLevel.CRITICAL)
    public void testComparePlans() {
        StartPage startPage = new StartPage(page);
        startPage.comparePlans("VPS Hosting");
        page.waitForSelector("span:text('Professional Grade')");
        page.locator("span:text('Professional Grade')").scrollIntoViewIfNeeded();
        ScreenshotUtils.takeScreenshot(page, "comparePlans_after_execution");
        Assert.assertTrue(page.locator("span:text('Professional Grade')").isVisible(),
                "Expected span with text 'Professional Grade' is not presented on page");
    }

    @Test(description = "Test Search Domain functionality")
    @Story("Search for a domain")
    @Severity(SeverityLevel.NORMAL)
    public void testSearchDomain() {
        StartPage startPage = new StartPage(page);
        startPage.searchDomain("Dedicated Hosting");
        page.waitForSelector("table.available-domains-table");
        ScreenshotUtils.takeScreenshot(page, "searchDomain_after_execution");
        Assert.assertTrue(page.locator("table.available-domains-table").isVisible(),
                "Expected table with available domains is not visible after search");
    }

    @Test(description = "Test Menu Bar functionality")
    @Story("Navigate through menu bar")
    @Severity(SeverityLevel.NORMAL)
    public void testMenuBar() {
        StartPage startPage = new StartPage(page);
        startPage.chooseMenu("Web Hosting", "Shared Hosting");
        page.waitForSelector("h2:text('Buy Shared Web Hosting Plans')");
        page.locator("h2:text('Buy Shared Web Hosting Plans')").scrollIntoViewIfNeeded();
        ScreenshotUtils.takeScreenshot(page, "testMenuBar_after_execution");
        Assert.assertTrue(page.locator("h2:text('Buy Shared Web Hosting Plans')").isVisible(),
                "Expected header 'Buy Shared Web Hosting Plans' is not visible.");
    }
}
