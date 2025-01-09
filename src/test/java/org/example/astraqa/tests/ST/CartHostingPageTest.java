package org.example.astraqa.tests.ST;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.*;
import org.example.astraqa.pages.cart.CartHostingPage;
import org.example.astraqa.utils.*;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("UI Tests")
@Feature("Cart Hosting Page Tests")
public class CartHostingPageTest {
    private Page page;

    @BeforeClass
    public void setUp() {
        page = BrowserManager.createPage();
        WebTestUtils.navigateAndHandleCookies(page, "https://www.inmotionhosting.com/", 20000);
        CartHostingPage cartHostingPage = WebTestUtils.selectHostingServiceAndGoToCart(page);
        EssentialUIValidator.validateEssentialUIElements(page, cartHostingPage.getEssentialUIElementsOnCartHostingPage());
    }

    @AfterClass
    public void tearDown() {
        BrowserManager.closeBrowser();
    }

    @Test(description = "Test Change Term Length functionality")
    @Story("Cart Hosting Page")
    @Severity(SeverityLevel.CRITICAL)
    public void testChangeTermLength() {
        String termLengthInCart = page.locator(".plan-term").innerText();

        CartHostingPage cartHostingPage = new CartHostingPage(page);
        cartHostingPage.changeTermLength("1 Year");
        Assert.assertEquals(termLengthInCart,"1 Year Subscription",
                "Term length was not changed to 1 Year");
        Assert.assertTrue(cartHostingPage.calculatePrices(), "Prices are calculated incorrectly");
    }

    @Test(description = "Test select Center Location")
    @Story("Cart Hosting Page")
    @Severity(SeverityLevel.BLOCKER)
    public void testSelectDataCenterLocation() {
        Locator dataCenterCheckbox = page.locator("div.form-group.addons")
                .locator("div:has(span:text('Washington, D.C. (US East Coast)')) >> input");

        CartHostingPage cartHostingPage = new CartHostingPage(page);
        cartHostingPage.customizeAddon("Data Center", "Washington, D.C.");
        Assert.assertTrue(dataCenterCheckbox.isChecked(),"Data Center Location was not selected");
        Assert.assertTrue(cartHostingPage.calculatePrices(), "Prices are calculated incorrectly");
    }

    @Test(description = "Test select Control panel option")
    @Story("Cart Hosting Page")
    @Severity(SeverityLevel.BLOCKER)
    public void testSelectControlPanelOption() {
        Locator controlPanelCheckbox = page.locator("div.form-group.addons")
                .locator("div:has(span:text('cPanel Dedicated Premier - 100 Accounts + $479.88')) >> input");
        Locator controlPanelOptionInCart = page.locator("tr.sub-item:has(div:text('cPanel Dedicated Premier - 100 Accounts'))");
        Locator controlPlanePrice = controlPanelOptionInCart.locator("td.price-point")
                .locator("div:has-text('$479.88')");

        CartHostingPage cartHostingPage = new CartHostingPage(page);
        cartHostingPage.customizeAddon("Control Panel Options", "cPanel Dedicated Premier - 100 Accounts");
        Assert.assertTrue(controlPanelCheckbox.isChecked(),"Control panel option was not selected");

        controlPanelOptionInCart.scrollIntoViewIfNeeded();
        ScreenshotUtils.takeScreenshot(page, "testSelectActiveSecurity_option_in_Cart");
        Assert.assertTrue(controlPanelOptionInCart.isVisible(), "Control panel option is not visible in the cart");
        Assert.assertTrue(controlPlanePrice.isVisible(), "Control panel option price is not visible in the cart");
        Assert.assertTrue(cartHostingPage.calculatePrices(), "Prices are calculated incorrectly");
    }

    @Test(description = "Test select Operation System addon")
    @Story("Cart Hosting Page")
    @Severity(SeverityLevel.CRITICAL)
    public void testSelectOperationSystem() {
        Locator operationSystemCheckbox = page.locator("div.form-group.addons")
                .locator("div:has(span:text('Ubuntu 22.04 LTS + free')) >> input");
        Locator operationSystemCheckboxInCart = page.locator("tr.sub-item:has(div:text('Ubuntu 22.04 LTS'))");
        Locator operationSystemPrice = operationSystemCheckboxInCart.locator("td.price-point")
                .locator("div:has-text('FREE')");

        CartHostingPage cartHostingPage = new CartHostingPage(page);
        cartHostingPage.customizeAddon("Operating System", "Ubuntu 22.04 LTS");
        Assert.assertTrue(operationSystemCheckbox.isChecked(),"Operation System addon was not selected");

        operationSystemCheckboxInCart.scrollIntoViewIfNeeded();
        ScreenshotUtils.takeScreenshot(page, "testSelectActiveSecurity_option_in_Cart");
        Assert.assertTrue(operationSystemCheckboxInCart.isVisible(),"Operation System addon is not visible in the cart");
        Assert.assertTrue(operationSystemPrice.isVisible(), "Operation System addon price is not visible in the cart");
        Assert.assertTrue(cartHostingPage.calculatePrices(), "Prices are calculated incorrectly");
    }

    @Test(description = "Test select Bandwidth addon")
    @Story("Cart Hosting Page")
    @Severity(SeverityLevel.BLOCKER)
    public void testSelectBandwidth() {
        Locator bandwidthAddonCheckbox = page.locator("div.form-group.addons")
                .locator("div:has(span:text('10 Gbps Uplink Port + $11,999.88')) >> input");
        Locator bandwidthAddonInCart = page.locator("tr.sub-item:has(div:text('10 Gbps Uplink Port'))");
        Locator bandwidthAddonPrice = bandwidthAddonInCart.locator("td.price-point")
                .locator("div:has-text('$11,999.88')");

        CartHostingPage cartHostingPage = new CartHostingPage(page);
        cartHostingPage.customizeAddon("Bandwidth", "10 Gbps Uplink Port");
        Assert.assertTrue(bandwidthAddonCheckbox.isChecked(),"Bandwidth addon was not selected");

        bandwidthAddonInCart.scrollIntoViewIfNeeded();
        ScreenshotUtils.takeScreenshot(page, "testSelectActiveSecurity_option_in_Cart");
        Assert.assertTrue(bandwidthAddonInCart.isVisible(),"Bandwidth addon is not visible in the cart");
        Assert.assertTrue(bandwidthAddonPrice.isVisible(), "Bandwidth addon price is not visible in the cart");
        Assert.assertTrue(cartHostingPage.calculatePrices(), "Prices are calculated incorrectly");
    }

    @Test(description = "Test select Active Security addon")
    @Story("Cart Hosting Page")
    @Severity(SeverityLevel.NORMAL)
    public void testSelectActiveSecurity() {
        Locator activeSecurityAddonCheckbox = page.locator("div.form-group.addons")
                .locator("div:has(span:text('Monarx Security - Active Protection'))")
                .locator("input.config-addon-group-selector");
        Locator activeSecurityAddonSubOptionCheckbox = page.locator("div #addon-group-531")
                .locator("div:has(span:has-text(' Monarx Security - Active Protection - 1 Month + $19.99')) >> input");
        Locator activeSecurityAddonInCart = page.locator("tr.sub-item:has(div:text('Monarx Security - Active Protection'))");
        Locator activeSecurityAddonPrice = activeSecurityAddonInCart.locator("td.price-point")
                .locator("div:has-text('$19.99')");

        CartHostingPage cartHostingPage = new CartHostingPage(page);
        cartHostingPage.customizeAddon("Monarx Security - Active Protection", "Monarx Security");
        Assert.assertTrue(activeSecurityAddonCheckbox.isChecked(),"Active Security addon was not selected");
        Assert.assertTrue(activeSecurityAddonSubOptionCheckbox.isChecked(),"Active Security Sub option addon was not selected");

        activeSecurityAddonInCart.scrollIntoViewIfNeeded();
        ScreenshotUtils.takeScreenshot(page, "testSelectActiveSecurity_option_in_Cart");
        Assert.assertTrue(activeSecurityAddonInCart.isVisible(),"Active Security addon is not visible in the cart");
        Assert.assertTrue(activeSecurityAddonPrice.isVisible(), "Active Security addon price is not visible in the cart");
        Assert.assertTrue(cartHostingPage.calculatePrices(), "Prices are calculated incorrectly");
    }

    @Test(description = "Test Continue Order Process")
    @Story("Cart Hosting Page")
    @Severity(SeverityLevel.BLOCKER)
    public void testContinueButton() {
        Locator domainNameHeader = page.locator("h2:text('Choose your domain name.')");
        Locator domainNameOptions = page.locator("form.domain-search");

        CartHostingPage cartHostingPage = new CartHostingPage(page);
        cartHostingPage.clickContinueButton();
        boolean domainNameHeaderIsVisible = WebTestUtils.waitForElementVisible(domainNameHeader, 3000);
        boolean domainNameOptionsIsVisible = WebTestUtils.waitForElementVisible(domainNameOptions, 3000);

        Assert.assertEquals(page.url(), "https://secureorder.inmotionhosting.com/order-process#domain",
                "Failed to navigate to the Order Process Domain Name page. The current URL is: " + page.url());
        Assert.assertTrue(domainNameHeaderIsVisible,"Expected header with text 'Choose your domain name.' is not visible.");
        Assert.assertTrue(domainNameOptionsIsVisible,"Expected Domain Name Options are not visible.");
    }
}
