package org.example.astraqa.pages.planSelection;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.example.astraqa.pages.pageInterfaces.PlanPage;
import org.example.astraqa.pages.cart.CartHostingPage;
import org.example.astraqa.utils.ActionUtils;
import org.example.astraqa.utils.LoggingUtils;

public class DedicatedHostingServicesPage extends PlanPage {
    public DedicatedHostingServicesPage(Page page) {
        super(page);
    }
    @Step("Select Plan Type {planType}")
    public void selectPlanType(String planType) {
        try {
            LoggingUtils.logInfoWithScreenshot(page, "Choose Plan Type");
            Locator planTypeButton = page.locator(String.format("span:text('%s')", planType)).first();

            ActionUtils.performActionWithScreenshot(
                    page,
                    "Scroll to Plan Type",
                    planTypeButton::scrollIntoViewIfNeeded,
                    planTypeButton
            );

            ActionUtils.performActionWithScreenshot(
                    page,
                    "Choose Plan Type",
                    planTypeButton::click,
                    planTypeButton
            );
        } catch (Exception e) {
            LoggingUtils.logError("Error during choosePlanType", e);
            throw new RuntimeException("Failed to choose Plan Type", e);
        }
    }

    @Override
    @Step("Select term {term}")
    public void selectTerm(String term) {
        try {
            LoggingUtils.logInfoWithScreenshot(page, "Choose Term");
            Locator termButton = page.locator("div.container.imh-rostrum-switcher.active")
                    .locator(String.format("button:text('%s')", term));

            ActionUtils.performActionWithScreenshot(
                    page,
                    "Scroll to Term",
                    termButton::scrollIntoViewIfNeeded,
                    termButton
            );

            ActionUtils.performActionWithScreenshot(
                    page,
                    "Choose Term",
                    termButton::click,
                    termButton
            );
        } catch (Exception e) {
            LoggingUtils.logError("Error during chooseTerm", e);
            throw new RuntimeException("Failed to choose Term", e);
        }
    }

    @Override
    @Step("Select option {optionName}")
    public CartHostingPage selectOption(String optionName) {
        try {
            LoggingUtils.logInfoWithScreenshot(page, "Choose option");
            Locator option = page.locator("div.container.imh-rostrum-switcher.active")
                    .locator(String.format("div.imh-rostrum-card:has-text('%s')", optionName));
            Locator optionSelectButton = option.locator("div.active")
                    .locator("a:text('Select')");

            ActionUtils.performActionWithScreenshot(
                    page,
                    "Scroll to option",
                    option::scrollIntoViewIfNeeded,
                    option
            );

            ActionUtils.performActionWithScreenshot(
                    page,
                    "Select option",
                    optionSelectButton::click,
                    optionSelectButton
            );

            return new CartHostingPage(page);
        } catch (Exception e) {
            LoggingUtils.logError("Error during chooseOption", e);
            throw new RuntimeException("Failed to choose option", e);
        }
    }
}
