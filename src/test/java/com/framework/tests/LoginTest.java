package com.framework.tests;

import com.framework.base.BaseTest;
import com.framework.pages.LoginPage;
import com.framework.pages.ProductsPage;
import com.framework.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(groups = {"smoke", "regression"}, description = "Valid credentials should land the user on the products page")
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = loginPage.login(
                ConfigReader.get("validUsername"),
                ConfigReader.get("validPassword"));

        Assert.assertTrue(productsPage.isPageDisplayed(), "Products page was not displayed after valid login");
    }

    @Test(groups = {"smoke", "regression"}, description = "Invalid credentials should show an error and block login")
    public void testInvalidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.attemptLogin(
                ConfigReader.get("invalidUsername"),
                ConfigReader.get("invalidPassword"));

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Expected an error message for invalid login");
        Assert.assertTrue(
                loginPage.getErrorMessage().contains("Username and password do not match"),
                "Unexpected error message text");
    }

    @Test(groups = {"regression"}, description = "Locked-out user should be blocked with the correct error")
    public void testLockedOutUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.attemptLogin(
                ConfigReader.get("lockedUsername"),
                ConfigReader.get("validPassword"));

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Expected an error message for locked-out user");
        Assert.assertTrue(
                loginPage.getErrorMessage().contains("locked out"),
                "Unexpected error message text for locked-out user");
    }

    @Test(groups = {"regression"}, description = "Blank credentials should be rejected")
    public void testEmptyCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.attemptLogin("", "");

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Expected an error message for empty credentials");
    }
}
