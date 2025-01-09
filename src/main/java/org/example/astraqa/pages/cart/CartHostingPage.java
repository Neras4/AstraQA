package org.example.astraqa.pages.cart;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.example.astraqa.pages.planSelection.DedicatedHostingServicesPage;
import org.example.astraqa.utils.ActionUtils;
import org.example.astraqa.utils.LoggingUtils;
import org.example.astraqa.utils.UIElement;

import java.util.ArrayList;
import java.util.List;

import static org.example.astraqa.utils.CommonUtils.parsePrice;

public class CartHostingPage {
    private final Page page;

    public CartHostingPage(Page page)  {this.page = page;}

    @Step("Change Term length")
    public void changeTermLength(String termLength) {
        try {
            LoggingUtils.logInfoWithScreenshot(page, "Change Term length");
            Locator termLengthDropdown = page.getByText("Change Term Length");
            Locator contractTermDropdownListValues = page.locator("select.form-control.plan-select");
            String selectedContractTermCode = contractTermDropdownListValues.locator(String.format("option:text('%s')", termLength))
                    .getAttribute("value");
            Locator selectedContractTermRadiobutton = page.locator(String.format("table.table-plans div:has(input.choose-plan[data-id='%s'])",
                    selectedContractTermCode));

            ActionUtils.performActionWithScreenshot(
                    page,
                    "Change Term length",
                    termLengthDropdown::click,
                    termLengthDropdown
            );

            ActionUtils.performActionWithScreenshot(
                    page,
                    "Select Term length",
                    () -> contractTermDropdownListValues.selectOption(termLength),
                    contractTermDropdownListValues
            );

            ActionUtils.performActionWithScreenshot(
                    page,
                    "Select option in Term length dropdown",
                    () -> selectedContractTermRadiobutton.click(new Locator.ClickOptions().setForce(true)),
                    selectedContractTermRadiobutton
            );
        } catch (Exception e) {
            LoggingUtils.logError("Error during changeTermLength", e);
            throw new RuntimeException("Failed to change Term length on Cart page", e);
        }
    }

    @Step("Customize addon with name: {productName}")
    public void customizeAddon(String addonGroupName, String addonOptionName) {
        try {
            LoggingUtils.logInfoWithScreenshot(page, "Customize option with name " + addonGroupName);
            Locator addonGroup = page.locator(String.format("div.form-group.addons:has(h3:has-text('%s'))", addonGroupName));
            Locator selectedAddon = addonGroup.locator(String.format("div:has(span:has-text('%s'))", addonOptionName)).first();

            ActionUtils.performActionWithScreenshot(
                    page,
                    "Scrolling to addon group with name " + addonGroupName,
                    addonGroup::scrollIntoViewIfNeeded,
                    addonGroup
            );

            ActionUtils.performActionWithScreenshot(
                    page,
                    "Choose addon with name " + addonOptionName,
                    () -> selectedAddon.click(new Locator.ClickOptions().setForce(true)),
                    selectedAddon
            );
        } catch (Exception e) {
            LoggingUtils.logError("Error during chooseDataCenterLocation", e);
            throw new RuntimeException("Failed to choose Data Center Location on Cart page", e);
        }
    }

    @Step("Calculate prices for selected options")
    public boolean calculatePrices() {
        try {
            LoggingUtils.logInfoWithScreenshot(page, "Calculate prices for selected options");
            Locator tableRowsWithItems = page.locator("table.item").locator("tr");
            double subTotalPriceInTable = 0;

            for(int i = 0; i < tableRowsWithItems.count(); i++) {
                Locator item = tableRowsWithItems.nth(i);
                Locator priceElement = item.locator("td.price-point .price");

                if(priceElement.isVisible()) {
                    String priceText = priceElement.innerText();
                    String[] priceParts = priceText.split("\n");

                    if(priceParts.length > 0 && !(priceParts[0].equals("Included") || priceParts[0].equals("FREE"))) {
                        double actualPrice = parsePrice(priceParts[0]);
                        subTotalPriceInTable += actualPrice;
                    }
                }
            }

            if(page.locator("div.col-md-12.text-right").isVisible()) {
                String[] totalPriceDetails = page.locator("div.col-md-12.text-right")
                        .locator("h3").first().innerText().split("\n");

                if(totalPriceDetails.length > 2) {
                    double subTotalPriceInPriceDetails = parsePrice(totalPriceDetails[0].split(":")[1]);
                    double taxesAndFeesPrice = parsePrice(totalPriceDetails[1].split(":")[1]);
                    double totalPrice = parsePrice(totalPriceDetails[3].split(":")[1]);

                    final double EPSILON = 0.01;
                    if (Math.abs(subTotalPriceInTable - subTotalPriceInPriceDetails) < EPSILON &&
                            Math.abs(subTotalPriceInPriceDetails + taxesAndFeesPrice - totalPrice) < EPSILON) {
                        LoggingUtils.logInfo("Prices are calculated correctly");
                        return true;
                    }
                }
            }

            LoggingUtils.logInfo("Prices are calculated incorrectly");
            return false;
        } catch (Exception e) {
            LoggingUtils.logError("Error during calculatePrices", e);
            throw new RuntimeException("Failed to calculate prices on Cart page", e);
        }
    }

    @Step("Continue process to choose domain name")
    public OrderProcessDomainNamePage clickContinueButton() {
        try {
            LoggingUtils.logInfoWithScreenshot(page, "Click on 'Continue' button");
            Locator continueButton = page.locator(".sidebar-total").locator("input[value='Continue']");

            ActionUtils.performActionWithScreenshot(
                    page,
                    "Scroll to 'Continue' button",
                    continueButton::scrollIntoViewIfNeeded,
                    continueButton
            );

            ActionUtils.performActionWithScreenshot(
                    page,
                    "Click on 'Continue' button",
                    continueButton::click,
                    continueButton
            );

            return new OrderProcessDomainNamePage(page);
        } catch (Exception e) {
            LoggingUtils.logError("Error during clickContinueButton", e);
            throw new RuntimeException("Failed to click on 'Continue' button on Cart page", e);
        }
    }

    @Step("Empty Cart")
    public Object emptyCart(boolean isCompleteEmptyCart) {
        Object resultPage = page;

        try {
            LoggingUtils.logInfoWithScreenshot(page, "Initiating empty cart process");
            Locator emptyCartButton = page.locator("a:text('Empty Cart')");
            Locator emptyCartConfirmAlert = page.locator("div.showSweetAlert");

            ActionUtils.performActionWithScreenshot(
                    page,
                    "Scroll to Empty Cart button",
                    emptyCartButton::scrollIntoViewIfNeeded,
                    emptyCartButton
            );

            ActionUtils.performActionWithScreenshot(
                    page,
                    "Click on Empty Cart",
                    emptyCartButton::click,
                    emptyCartButton
            );

            if(emptyCartConfirmAlert.isVisible()) {
                LoggingUtils.logInfoWithScreenshot(page, "Empty Cart confirmation window appeared.");

                if(isCompleteEmptyCart) {
                    ActionUtils.performActionWithScreenshot(
                            page,
                            "Confirm Empty Cart",
                            emptyCartConfirmAlert.locator("button:text('Yes, empty the cart!')")::click,
                            emptyCartConfirmAlert.locator("button:text('Yes, empty the cart!')")
                    );

                    resultPage =  new DedicatedHostingServicesPage(page);
                } else {
                    ActionUtils.performActionWithScreenshot(
                            page,
                            "Cancel Empty Cart",
                            emptyCartConfirmAlert.locator("button:text('Cancel')")::click,
                            emptyCartConfirmAlert.locator("button:text('Cancel')")
                    );
                }
            }

            return resultPage;
        } catch (Exception e) {
            LoggingUtils.logError("Error during emptyCart process", e);
            throw new RuntimeException("Failed to empty Cart", e);
        }
    }

    public List<UIElement> getEssentialUIElementsOnCartHostingPage() {
        List<UIElement> elements = new ArrayList<>();
        elements.add(new UIElement("#change-plan-accordion",
                "Change Term Length option is missing on Cart Hosting Page"));
        elements.add(new UIElement(".configure-server",
                "There is no fields to update addons on Cart Hosting Page"));
        elements.add(new UIElement("#login-btn",
                "Login button is missed on Cart Hosting Page"));
        elements.add(new UIElement(".empty-cart",
                "Empty Cart button is missed on Cart Hosting Page"));
        elements.add(new UIElement(".cart-container",
                "Order Summary cart is missed on Cart Hosting Page"));
        elements.add(new UIElement("//h3[contains(text(), 'Subtotal')]",
                "Price Summary is missed on Cart Hosting Page"));
        elements.add(new UIElement("input[value='Continue'][id='sidebar-next-step']",
                "Continue button is missed on Cart Hosting Page"));

        return elements;
    }
}