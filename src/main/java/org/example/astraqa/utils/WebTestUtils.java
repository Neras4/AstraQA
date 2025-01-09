package org.example.astraqa.utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.example.astraqa.pages.cart.CartHostingPage;
import org.example.astraqa.pages.cart.CheckoutDomainNamePage;
import org.example.astraqa.pages.planSelection.DedicatedHostingServicesPage;
import org.example.astraqa.pages.StartPage;
import org.testng.Assert;

public class WebTestUtils {
    public static void navigateAndHandleCookies(Page page, String url, int timeout) {
        try {
            LoggingUtils.logInfo("Navigated to page and accepted cookies.");

            page.navigate(url, new Page.NavigateOptions().setTimeout(timeout));
            page.waitForTimeout(500);

            Locator cookieBanner = page.locator("#onetrust-accept-btn-handler");
            if (cookieBanner.isVisible()) {
                cookieBanner.click();
            }
        } catch (Exception e) {
            LoggingUtils.logError("Error during page navigation", e);
            throw new RuntimeException("Failed to navigate to page", e);
        }
    }

    public static void clearCookies(Page page) {
        try {
            LoggingUtils.logInfo("Cleared cookies after test.");

            page.context().clearCookies();
        } catch (Exception e) {
            LoggingUtils.logError("Error clearing cookies", e);
        }
    }

    public static CartHostingPage selectHostingServiceAndGoToCart(Page page) {
        try {
            LoggingUtils.logInfo("Navigated to Cart Hosting Page.");

            StartPage startPage = new StartPage(page);
            DedicatedHostingServicesPage dedicatedHostingServicesPage = (DedicatedHostingServicesPage)
                    startPage.chooseServiceType ("Dedicated Hosting");
            dedicatedHostingServicesPage.selectPlanType("Commercial Class");
            dedicatedHostingServicesPage.selectTerm("6 Month");
            CartHostingPage cartHostingPage = dedicatedHostingServicesPage.selectOption("CC-4000");
            page.waitForLoadState(LoadState.LOAD);

            CheckoutDomainNamePage checkoutDomainNamePage = new CheckoutDomainNamePage(page);
            startPage = checkoutDomainNamePage.clickOnLogo();

            page.waitForSelector("img[alt='InMotion Hosting Logo']");
            Locator cartItems = page.locator("div.upper-menu-section")
                    .locator("span.cartitems").first();
            cartItems.waitFor(new Locator.WaitForOptions().setTimeout(5000));

            startPage.openCartPage();
            page.waitForSelector("div:text('Change Term Length')");

            return cartHostingPage;
        } catch (Exception e) {
            LoggingUtils.logError("Error during test setup", e);
            throw new RuntimeException("Failed to set up test", e);
        }
    }

    public static void validateElementVisibility(Locator element, int timeoutMs, String errorMessage) {
        boolean isVisible = waitForElementVisible(element, timeoutMs);
        Assert.assertTrue(isVisible, errorMessage);
    }

    public static boolean waitForElementVisible(Locator locator, int timeout) {
        try {
            locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(timeout));
            return locator.isVisible();
        } catch (Exception e) {
            return false;
        }
    }
}
