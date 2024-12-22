package org.example.astraqa.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.example.astraqa.utils.ActionUtils;
import org.example.astraqa.utils.LoggingUtils;

public class StartPage {
    private final Page page;

    public StartPage(Page page)  {
        this.page = page;
    }

    @Step("Comparing plans for product: {productName}")
    public void comparePlans(String productName) {
        try {
            LoggingUtils.logInfoWithScreenshot(page, "Comparing plans for product " + productName);
            Locator card = page.locator("div.imh-rostrum-card")
                    .filter(new Locator.FilterOptions().setHasText(productName))
                    .first();
            Locator comparePlansButton = card.locator(".imh-switcher.active").locator("a:has-text('Compare Plans')");

            ActionUtils.performActionWithScreenshot(
                    page,
                    "Scrolling to product " + productName,
                    card::scrollIntoViewIfNeeded,
                    card
            );

            ActionUtils.performActionWithScreenshot(
                    page,
                    "CLick on " + productName,
                    comparePlansButton::click,
                    comparePlansButton
            );
        } catch (Exception e) {
            LoggingUtils.logError("Error during comparePlans for product " + productName, e);
            throw new RuntimeException("Failed to complete compare plan action for " + productName, e);
        }
    }

    @Step("Searching for domain: {text}")
    public void searchDomain(String text) {
        try {
            LoggingUtils.logInfoWithScreenshot(page, "Searching for domain with text " + text);
            Locator searchField = page.locator("#imh-search-form");
            Locator searchInput = searchField.getByPlaceholder("What domain are you searching for?");
            Locator searchButton = searchField.locator("button[type='submit']");

            ActionUtils.performActionWithScreenshot(
                    page,
                    "Scrolling to search field",
                    searchField::scrollIntoViewIfNeeded,
                    searchField
            );

            ActionUtils.performActionWithScreenshot(
                    page,
                    "Filling search field with text" + text,
                    () -> searchInput.fill(text),
                    searchInput
            );

            ActionUtils.performActionWithScreenshot(
                    page,
                    "Clicking Go button",
                    searchButton::click,
                    searchButton
            );
        } catch (Exception e) {
            LoggingUtils.logError("Error during searchDomain with text " + text, e);
            throw new RuntimeException("Failed to complete domain search for text: " + text, e);
        }
    }

    @Step("Choosing menu '{menuName}' with product type '{productType}'")
    public void chooseMenu(String menuName, String productType) {
        try {
            LoggingUtils.logInfoWithScreenshot(page, "Choosing menu " + menuName + " with product type " + productType);

            Locator menuItem = page.locator(String.format("a[title='%s']", menuName));
            Locator parent = page.locator("li", new Page.LocatorOptions().setHas(menuItem));
            String classAttribute = parent.getAttribute("class");

            if((classAttribute.equals("nav-item") || classAttribute.equals("nav-item dropdown"))
                    && productType == null) {
                ActionUtils.performActionWithScreenshot(
                        page,
                        "Clicking menu " + menuName,
                        menuItem::click,
                        menuItem
                );
            } else {
                Locator submenuItem = parent.locator(String.format("a[title='%s']", productType))
                        .locator("span.subnav-title");

                ActionUtils.performActionWithScreenshot(
                        page,
                        "Hovering over menu " + menuName,
                        menuItem::hover,
                        menuItem
                );
                ActionUtils.performActionWithScreenshot(
                        page,
                        "Clicking submenu " + productType,
                        submenuItem::click,
                        submenuItem
                );
            }
        } catch (Exception e) {
            LoggingUtils.logError("Error during chooseMenu with menu: " + menuName + " and product type: " + productType, e);
            throw new RuntimeException("Failed to choose menu: " + menuName + " with product type: " + productType, e);
        }
    }
}
