package org.example.astraqa.tests.ST;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.*;
import org.example.astraqa.pages.cart.CartHostingPage;
import org.example.astraqa.utils.BrowserManager;
import org.example.astraqa.utils.EssentialUIValidator;
import org.example.astraqa.utils.WebTestUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("UI Tests")
@Feature("Cart Hosting Page Tests")
public class CartHostingPageEmptyCartTest {
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

    @Test(description = "Test Cancel Empty Cart functionality")
    @Story("Cart Hosting Page")
    @Severity(SeverityLevel.NORMAL)
    public void testCancelEmptyCart() {
        Locator serverAddons = page.locator(".configure-server");
        Locator orderSummaryCart = page.locator(".cart-container");

        CartHostingPage cartHostingPage = new CartHostingPage(page);
        cartHostingPage.emptyCart(false);

        Assert.assertEquals(page.url(), "https://secureorder.inmotionhosting.com/order-process#hosting",
                "Failed to Cancel Empty Cart, cannot return back to Cart Hosting Page. The current URL is: " + page.url());
        WebTestUtils.validateElementVisibility(serverAddons, 3000, "There are no fields to update addons on the Cart Hosting Page.");
        WebTestUtils.validateElementVisibility(orderSummaryCart, 3000,"Order Summary cart is missing on the Cart Hosting Page.");
    }

    @Test(description = "Test Empty Cart functionality")
    @Story("Cart Hosting Page")
    @Severity(SeverityLevel.CRITICAL)
    public void testEmptyCart() {
        Locator dedicatedServerHeader = page.locator("h2:text('Standard Dedicated Server Plans')");
        Locator cartItems = page.locator("div.upper-menu-section").locator("span.cartitems").first();

        CartHostingPage cartHostingPage = new CartHostingPage(page);
        cartHostingPage.emptyCart(true);

        Assert.assertEquals(page.url(), "https://www.inmotionhosting.com/dedicated-servers",
                "Failed to Empty Cart, cannot redirect back to Plan Selection Dedicated Hosting Page. The current URL is: " + page.url());
        WebTestUtils.validateElementVisibility(dedicatedServerHeader, 3000,"Expected header with text 'Standard Dedicated Server Plans' is not visible.");
        Assert.assertTrue(cartItems.isVisible(), "Cart items locator is not visible.");
        String cartItemText = cartItems.innerText();
        Assert.assertEquals(cartItemText, "0", "Cart items count is not zero after emptying the cart.");
    }

}
