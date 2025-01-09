package org.example.astraqa.tests.ST;

import com.microsoft.playwright.Locator;
import io.qameta.allure.*;
import org.example.astraqa.pages.cart.CartHostingPage;
import org.example.astraqa.pages.StartPage;
import org.example.astraqa.utils.*;
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
        WebTestUtils.navigateAndHandleCookies(page, "https://www.inmotionhosting.com/", 20000);
        StartPage startPage = new StartPage(page);
        EssentialUIValidator.validateEssentialUIElements(page, startPage.getEssentialUIElementsOnStartPage());
    }

    @AfterMethod
    public void tearDownTest() {
        WebTestUtils.clearCookies(page);
    }

    @Test(description = "Test Open Login page functionality")
    @Story("Open Login page")
    @Severity(SeverityLevel.BLOCKER)
    public void testOpenLoginPage() {
        Locator loginPageHeader = page.locator("h2:text('AMP Login')");

        StartPage startPage = new StartPage(page);
        startPage.openLoginPage();

        Assert.assertEquals(page.url(), "https://secure1.inmotionhosting.com/index/login",
                "Failed to navigate to the Login page. The current URL is: " + page.url());
        WebTestUtils.validateElementVisibility(loginPageHeader, 4000,
                "Expected header 'AMP Login' is not visible on Login page.");
    }

    @Test(description = "Test Open Cart page functionality")
    @Story("Open Cart page")
    @Severity(SeverityLevel.BLOCKER)
    public void testOpenCartPage() {
        Locator cartPageHeader = page.locator("h2:text('Order Summary')");

        StartPage startPage = new StartPage(page);
        CartHostingPage cartHostingPagePage = startPage.openCartPage();

        if(cartHostingPagePage == null) {
            Assert.assertFalse(cartPageHeader.isVisible(),"Cart page should not be opened when the cart is empty.");
        } else {
            Assert.assertEquals(page.url(), "https://secureorder.inmotionhosting.com/order-process#hosting",
                    "Failed to navigate to the Cart Hosting page. The current URL is: " + page.url());
            WebTestUtils.validateElementVisibility(cartPageHeader, 3000,
                    "Expected header 'Order Summary' is not visible.");
        }
    }

    @Test(description = "Test Choose Service Plan")
    @Story("Choose Service Plan")
    @Severity(SeverityLevel.CRITICAL)
    public void testChooseServiceType () {
        Locator vpsHostingPageHeader = page.locator("h2:text('VPS Hosting Plans Designed with Your Business in Mind')");

        StartPage startPage = new StartPage(page);
        startPage.chooseServiceType("VPS Hosting");

        Assert.assertEquals(page.url(), "https://www.inmotionhosting.com/vps-hosting",
                "Failed to navigate to the VPS Hosting page. The current URL is: " + page.url());
        WebTestUtils.validateElementVisibility(vpsHostingPageHeader, 3000,
                "Expected header with text 'VPS Hosting Plans Designed with Your Business in Mind' is not visible on the VPS Hosting page.");
    }

    @Test(description = "Test Search Domain functionality")
    @Story("Search for a domain")
    @Severity(SeverityLevel.NORMAL)
    public void testSearchDomain() {
        Locator searchDomainTable = page.locator("table.available-domains-table");

        StartPage startPage = new StartPage(page);
        startPage.searchDomain("Dedicated Hosting");

        Assert.assertEquals(page.url(), "https://www.inmotionhosting.com/domains",
                "Failed to navigate to the Search Domain page. The current URL is: " + page.url());
        WebTestUtils.validateElementVisibility(searchDomainTable, 5000,
                "Expected table with available domains is not visible after search");
    }

    @Test(description = "Test Menu Bar functionality")
    @Story("Navigate through menu bar")
    @Severity(SeverityLevel.NORMAL)
    public void testMenuBar() {
        Locator sharedHostingPlansHeader = page.locator("h2:text('Buy Shared Web Hosting Plans')");

        StartPage startPage = new StartPage(page);
        startPage.chooseMenu("Web Hosting", "Shared Hosting");

        Assert.assertEquals(page.url(), "https://www.inmotionhosting.com/shared-hosting",
                "Failed to navigate to the Shared Hosting Plans page. The current URL is: " + page.url());
        WebTestUtils.validateElementVisibility(sharedHostingPlansHeader, 3000,
                "Expected header 'Buy Shared Web Hosting Plans' is not visible.");
    }

    @Test(description = "Choose data center")
    @Story("Choose data center")
    @Severity(SeverityLevel.NORMAL)
    public void testChooseDataCenter() {
        Locator dedicatedServerPlansHeader = page.locator("h2:text('Standard Dedicated Server Plans')");

        StartPage startPage = new StartPage(page);
        startPage.chooseDataCenter("Los Angeles, CA");

        Assert.assertEquals(page.url(), "https://www.inmotionhosting.com/dedicated-servers",
                "Failed to navigate to the Dedicated Hosting page. The current URL is: " + page.url());
        WebTestUtils.validateElementVisibility(dedicatedServerPlansHeader, 3000,
                "Expected header with text 'Standard Dedicated Server Plans' is not visible.");
    }

    @Test(description = "Click button")
    @Story("Click button")
    @Severity(SeverityLevel.NORMAL)
    public void testClickButton() {
        Locator sharedHostingPlansHeader = page.locator("h2:text('Buy Shared Web Hosting Plans')");

        StartPage startPage = new StartPage(page);
        startPage.clickButton("Shared Hosting Plans");

        Assert.assertEquals(page.url(), "https://www.inmotionhosting.com/shared-hosting",
                "Failed to navigate to the Shared Hosting Plans page. The current URL is: " + page.url());
        WebTestUtils.validateElementVisibility(sharedHostingPlansHeader, 3000,
                "Expected header with text 'Buy Shared Web Hosting Plans' is not visible.");
    }

    @Test(description = "Click button")
    @Story("Click button")
    @Severity(SeverityLevel.NORMAL)
    public void testClickChatWIthUsButton() {
        StartPage startPage = new StartPage(page);
        Page popupPage = page.waitForPopup(() -> startPage.clickChatWithUsButton(1));

        Locator chatWithUsHeader = popupPage.locator("h1:text('Chat with Sales')");
        WebTestUtils.validateElementVisibility(chatWithUsHeader, 7000,
                "Expected header with text 'Chat with Sales' is not visible.");
        popupPage.close();
    }
}
