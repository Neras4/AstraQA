package org.example.astraqa.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.example.astraqa.pages.pageInterfaces.PlanPage;
import org.example.astraqa.pages.cart.CartHostingPage;
import org.example.astraqa.utils.ActionUtils;
import org.example.astraqa.utils.LoggingUtils;
import org.example.astraqa.utils.UIElement;
import org.example.astraqa.utils.factories.PlanFactory;

import java.util.ArrayList;
import java.util.List;

public class StartPage {
    private final Page page;

    public StartPage(Page page)  {
        this.page = page;
    }

    @Step("Open Login page")
    public LoginPage openLoginPage() {
        try {
            LoggingUtils.logInfoWithScreenshot(page, "Opening Login page");
            Locator loginButton = page.locator("a:text('Login')");

            ActionUtils.performActionWithScreenshot(
                    page,
                    "Clicking on Login button",
                    loginButton::click,
                    loginButton
            );

            return new LoginPage(page);
        } catch (Exception e) {
            LoggingUtils.logError("Error during openLoginPage", e);
            throw new RuntimeException("Failed to open Login page", e);
        }
    }

    @Step("Open Cart page")
    public CartHostingPage openCartPage() {
        try {
            LoggingUtils.logInfoWithScreenshot(page, "Opening Cart page");
            Locator cartButton = page.locator("div.upper-menu-section")
                    .getByLabel("Shopping Cart", new Locator.GetByLabelOptions().setExact(true));
            Locator cartItem = cartButton.locator("span.cartitems");

            if(cartItem.innerText().equals("0")) {
                LoggingUtils.logInfo("Cart is empty. No need to open Cart page.");
                return null;
            }

            ActionUtils.performActionWithScreenshot(
                    page,
                    "Clicking on Cart button",
                    cartButton::click,
                    cartButton
            );

            return new CartHostingPage(page);
        } catch (Exception e) {
            LoggingUtils.logError("Error during openCartPage", e);
            throw new RuntimeException("Failed to open Cart page", e);
        }
    }

    @Step("Choose service type {productName}")
    public PlanPage chooseServiceType(String productName) {
        try {
            LoggingUtils.logInfoWithScreenshot(page, "Choose service type " + productName);
            Locator card = page.locator("div.imh-rostrum-card")
                    .filter(new Locator.FilterOptions().setHasText(productName))
                    .first();
            Locator comparePlansButton = card.locator(".imh-switcher.active")
                    .locator("a:has-text('Compare Plans')");

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

            PlanFactory factory = new PlanFactory(page);
            return factory.getPlanPage(productName);
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

    @Step("Choose data center: {dataCenter}")
    public void chooseDataCenter(String dataCenter) {
        try {
            LoggingUtils.logInfoWithScreenshot(page, "Choosing data center " + dataCenter);

            Locator dataCenterObject = page.locator(".bg-box")
                    .filter(new Locator.FilterOptions().setHasText(dataCenter));
            Locator chooseDataCenterButton = dataCenterObject.getByText("See Servers Available Here");

            ActionUtils.performActionWithScreenshot(
                    page,
                    "Scroll to certain datacenter " + dataCenter,
                    dataCenterObject::scrollIntoViewIfNeeded,
                    dataCenterObject
            );

            ActionUtils.performActionWithScreenshot(
                    page,
                    "Click on button to check server availability",
                    chooseDataCenterButton::click,
                    chooseDataCenterButton
            );
        } catch (Exception e) {
            LoggingUtils.logError("Error during chooseDataCenter with data center: " + dataCenter, e);
            throw new RuntimeException("Failed to choose data center: " + dataCenter, e);
        }
    }

    @Step("Click on button with text: {buttonText}")
    public void clickButton(String buttonText) {
        try {
            LoggingUtils.logInfoWithScreenshot(page, "Clicking button with text " + buttonText);

            Locator button = page.locator("a.ppb-button.btn-primary", new Page.LocatorOptions().setHasText(buttonText));

            ActionUtils.performActionWithScreenshot(
                    page,
                    "Scroll to button with text " + buttonText,
                    button::scrollIntoViewIfNeeded,
                    button
            );

            ActionUtils.performActionWithScreenshot(
                    page,
                    "Clicking button with text " + buttonText,
                    button::click,
                    button
            );
        } catch (Exception e) {
            LoggingUtils.logError("Error during clickButton with text: " + buttonText, e);
            throw new RuntimeException("Failed to click button with text: " + buttonText, e);
        }
    }

    @Step("Click on button with text 'Chat with us' and index {index}")
    public void clickChatWithUsButton(int index) {
        try {
            LoggingUtils.logInfoWithScreenshot(page, "Clicking Chat with us button");

            Locator chatWithUsButton = page.locator(String.format("a[aria-label='Chat with us %d']", index))
                    .nth(index - 1);

            ActionUtils.performActionWithScreenshot(
                    page,
                    "Scroll to Chat with us button",
                    chatWithUsButton::scrollIntoViewIfNeeded,
                    chatWithUsButton
            );

            ActionUtils.performActionWithScreenshot(
                    page,
                    "Clicking Chat with us button",
                    chatWithUsButton::click,
                    chatWithUsButton
            );
        } catch (Exception e) {
            LoggingUtils.logError("Error during clickChatWithUsButton", e);
            throw new RuntimeException("Failed to click Chat with us button", e);
        }
    }

    public List<UIElement> getEssentialUIElementsOnStartPage() {
        List<UIElement> elements = new ArrayList<>();
        elements.add(new UIElement("img[alt='InMotion Hosting Logo']", "Logo is missing on Start Page"));
        elements.add(new UIElement("#navbarNavDropdown", "Menubar is missing on Start Page"));
        elements.add(new UIElement("a:text('Login')", "Main menu is missing on Start Page"));
        elements.add(new UIElement("div.upper-menu-section", "Shopping Cart is missing on Start Page"));
        elements.add(new UIElement("div.boldgrid-section #home-rostrum-3 div.imh-rostrum-container",
                "The hosting selection menu at the top of the page is missing on Start Page"));
        elements.add(new UIElement("div.boldgrid-section #home-rostrum-2 div.imh-rostrum-container",
                "The hosting selection menu at the bottom of the page is missing on Start Page"));
        elements.add(new UIElement("#imh-search-form", "Search field is missing on Start Page"));
        elements.add(new UIElement("#datacenters div.container", "Datacenters container is missing on Start Page"));

        return elements;
    }
}
