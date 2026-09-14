package com.framework.tests;

import com.framework.base.BaseTest;
import com.framework.pages.CartPage;
import com.framework.pages.LoginPage;
import com.framework.pages.ProductsPage;
import com.framework.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AddToCartTest extends BaseTest {

    private ProductsPage productsPage;

    private static final String PRODUCT_1 = "Sauce Labs Backpack";
    private static final String PRODUCT_2 = "Sauce Labs Bike Light";

    @BeforeMethod(alwaysRun = true, dependsOnMethods = "setUp")
    public void loginBeforeEachTest() {
        LoginPage loginPage = new LoginPage(driver);
        productsPage = loginPage.login(
                ConfigReader.get("validUsername"),
                ConfigReader.get("validPassword"));
    }

    @Test(groups = {"smoke", "regression"}, description = "Adding a single product should update the cart badge")
    public void testAddSingleProductToCart() {
        productsPage.addProductToCart(PRODUCT_1);
        Assert.assertEquals(productsPage.getCartCount(), 1, "Cart badge count did not update correctly");
    }

    @Test(groups = {"regression"}, description = "Adding multiple products should reflect the correct total count")
    public void testAddMultipleProductsToCart() {
        productsPage.addProductToCart(PRODUCT_1);
        productsPage.addProductToCart(PRODUCT_2);
        Assert.assertEquals(productsPage.getCartCount(), 2, "Cart badge did not reflect two added products");
    }

    @Test(groups = {"smoke", "regression"}, description = "Cart page should list exactly the products that were added")
    public void testCartValidationShowsCorrectProducts() {
        productsPage.addProductToCart(PRODUCT_1);
        productsPage.addProductToCart(PRODUCT_2);

        CartPage cartPage = productsPage.goToCart();

        Assert.assertEquals(cartPage.getCartItemCount(), 2, "Cart page item count mismatch");
        Assert.assertTrue(cartPage.isProductInCart(PRODUCT_1), PRODUCT_1 + " missing from cart");
        Assert.assertTrue(cartPage.isProductInCart(PRODUCT_2), PRODUCT_2 + " missing from cart");
    }

    @Test(groups = {"regression"}, description = "Removing a product from the cart page should update the cart")
    public void testRemoveProductFromCart() {
        productsPage.addProductToCart(PRODUCT_1);
        CartPage cartPage = productsPage.goToCart();

        cartPage.removeProduct(PRODUCT_1);

        Assert.assertEquals(cartPage.getCartItemCount(), 0, "Product was not removed from the cart");
    }
}
