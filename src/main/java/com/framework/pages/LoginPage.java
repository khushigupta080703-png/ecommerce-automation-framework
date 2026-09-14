package com.framework.pages;

import com.framework.base.BasePage;
import com.framework.utils.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(id = "user-name")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void enterUsername(String username) {
        WaitUtils.waitForVisibility(driver, usernameField).clear();
        usernameField.sendKeys(username);
    }

    public void enterPassword(String password) {
        WaitUtils.waitForVisibility(driver, passwordField).clear();
        passwordField.sendKeys(password);
    }

    public ProductsPage clickLogin() {
        WaitUtils.waitForClickable(driver, loginButton).click();
        return new ProductsPage(driver);
    }

    /**
     * Convenience method for a full valid-login flow.
     */
    public ProductsPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        return clickLogin();
    }

    /**
     * Use for negative test cases where login is expected to fail
     * and the user should remain on the login page.
     */
    public void attemptLogin(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        WaitUtils.waitForClickable(driver, loginButton).click();
    }

    public String getErrorMessage() {
        return WaitUtils.waitForVisibility(driver, errorMessage).getText();
    }

    public boolean isErrorDisplayed() {
        try {
            return WaitUtils.waitForVisibility(driver, errorMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
