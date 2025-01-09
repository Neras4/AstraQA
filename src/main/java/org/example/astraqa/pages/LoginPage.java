package org.example.astraqa.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.example.astraqa.utils.ActionUtils;
import org.example.astraqa.utils.LoggingUtils;

public class LoginPage {
    private final Page page;

    public LoginPage(Page page) {this.page = page;}

    @Step("Enter email for login: {email}")
    public void enterEmail(String email) {
        try {
            LoggingUtils.logInfoWithScreenshot(page, "Enter email");
            Locator emailField = page.getByPlaceholder("email address");

            ActionUtils.performActionWithScreenshot(
                    page,
                    "Enter email",
                    () -> emailField.fill(email),
                    emailField
            );
        } catch (Exception e) {
            LoggingUtils.logError("Error during enterEmail", e);
            throw new RuntimeException("Failed to enter email on login page", e);
        }
    }

    @Step("Enter password for login: {password}")
    public void enterPassword(String password) {
        try {
            LoggingUtils.logInfoWithScreenshot(page, "Enter password");
            Locator passwordField = page.getByPlaceholder("password");

            ActionUtils.performActionWithScreenshot(
                    page,
                    "Enter password",
                    () -> passwordField.fill(password),
                    passwordField
            );
        } catch (Exception e) {
            LoggingUtils.logError("Error during enterPassword", e);
            throw new RuntimeException("Failed to enter password on login page", e);
        }
    }

    @Step("Click on 'Login' button")
    public void clickOnLoginButton() {
        try {
            LoggingUtils.logInfoWithScreenshot(page, "Click on 'Login' button");
            Locator loginButton = page.locator("button:text('Login')");

            if(loginButton.getAttribute("disabled") == null) {
                ActionUtils.performActionWithScreenshot(
                        page,
                        "Click on 'Login' button",
                        loginButton::click,
                        loginButton
                );
            } else {
                LoggingUtils.logInfo("Login button is disabled. Please enter Capcha.");
            }
        } catch (Exception e) {
            LoggingUtils.logError("Error during clickOnLoginButton", e);
            throw new RuntimeException("Failed to click on 'Login' button", e);
        }
    }

    @Step("Click on 'I dont remember my password' link")
    public void clickOnForgotPasswordLink() {
        try {
            LoggingUtils.logInfoWithScreenshot(page, "Click on 'I dont remember my password' link");
            Locator forgotPasswordLink = page.locator("a:text('I don’t remember my password')");

            ActionUtils.performActionWithScreenshot(
                    page,
                    "Click on 'I dont remember my password' link",
                    forgotPasswordLink::click,
                    forgotPasswordLink
            );
        } catch (Exception e) {
            LoggingUtils.logError("Error during clickOnForgotPasswordLink", e);
            throw new RuntimeException("Failed to click on 'I dont remember my password' link", e);
        }
    }

    @Step("Click on 'Need help logging in?' link")
    public void clickOnNeedHelpLoggingInLink() {
        try {
            LoggingUtils.logInfoWithScreenshot(page, "Click on 'Need help logging in?' link");
            Locator needHelpLoggingInLink = page.locator("a:text('Need help logging in?')");

            ActionUtils.performActionWithScreenshot(
                    page,
                    "Click on 'Need help logging in?' link",
                    needHelpLoggingInLink::click,
                    needHelpLoggingInLink
            );
        } catch (Exception e) {
            LoggingUtils.logError("Error during clickOnNeedHelpLoggingInLink", e);
            throw new RuntimeException("Failed to click on 'Click on 'Need help logging in?' link", e);
        }
    }
}
