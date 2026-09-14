package com.framework.tests;

import com.framework.base.BaseTest;
import com.framework.pages.CartPage;
import com.framework.pages.CheckoutPage;
import com.framework.pages.LoginPage;
import com.framework.pages.ProductsPage;
import com.framework.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CheckoutTest extends BaseTest {

    private ProductsPage productsPage;
    private static final String PRODUCT_1 = "Sauce Labs Backpack";

    @BeforeMethod(alwaysRun = true, dependsOnMethods = "setUp")
    public void loginBeforeEachTest() {
        LoginPage loginPage = new LoginPage(driver);
        productsPage = loginPage.login(
                ConfigReader.get("validUsername"),
                ConfigReader.get("validPassword"));
    }

    @Test(groups = {"smoke", "regression"}, description = "End-to-end happy path: add product, checkout, confirm order")
    public void testCompleteCheckoutFlow() {
        productsPage.addProductToCart(PRODUCT_1);
        CartPage cartPage = productsPage.goToCart();
        CheckoutPage checkoutPage = cartPage.clickCheckout();

        String confirmation = checkoutPage.completeCheckout("John", "Doe", "12345");

        Assert.assertEquals(confirmation, "Thank you for your order!", "Order confirmation message mismatch");
    }

    @Test(groups = {"regression"}, description = "Checkout should reject submission when required fields are blank")
    public void testCheckoutFormValidation() {
        productsPage.addProductToCart(PRODUCT_1);
        CartPage cartPage = productsPage.goToCart();
        CheckoutPage checkoutPage = cartPage.clickCheckout();

        checkoutPage.clickContinue(); // no info entered

        Assert.assertTrue(
                checkoutPage.getFormErrorMessage().contains("First Name is required"),
                "Expected validation error for missing first name");
    }

    @Test(groups = {"regression"}, description = "Order total should be visible on the checkout overview step")
    public void testOrderTotalIsDisplayed() {
        productsPage.addProductToCart(PRODUCT_1);
        CartPage cartPage = productsPage.goToCart();
        CheckoutPage checkoutPage = cartPage.clickCheckout();

        checkoutPage.enterCustomerInfo("Jane", "Smith", "54321");
        checkoutPage.clickContinue();
        checkoutPage.waitForOverviewPage();

        Assert.assertTrue(checkoutPage.getOrderTotal().contains("Total"), "Order total not displayed on overview page");
    }
}
