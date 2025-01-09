package org.example.astraqa.pages.cart;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.example.astraqa.pages.StartPage;
import org.example.astraqa.utils.ActionUtils;

public class CheckoutDomainNamePage {
    private final Page page;

    public CheckoutDomainNamePage(Page page) {
        this.page = page;
    }

    public StartPage clickOnLogo() {
        Locator logoButton = page.locator("a:has(img[src=\"assets/svg/imh-logo.svg\"])");

        ActionUtils.performActionWithScreenshot(
                page,
                "Click on Logo",
                logoButton::click,
                logoButton
        );

        return new StartPage(page);
    }
}
