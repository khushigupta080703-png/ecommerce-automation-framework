package com.framework.pages;

import com.framework.base.BasePage;
import com.framework.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class CartPage extends BasePage {

    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;

    @FindBy(css = "[data-test='inventory-item-name']")
    private List<WebElement> cartItemNames;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public int getCartItemCount() {
        return cartItems.size();
    }

    public List<String> getCartItemNames() {
        return cartItemNames.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public boolean isProductInCart(String productName) {
        return getCartItemNames().contains(productName);
    }

    public void removeProduct(String productName) {
        By removeButton = By.xpath(
                "//div[text()='" + productName + "']/ancestor::div[@class='cart_item']" +
                        "//button[contains(@class,'cart_button')]");
        WaitUtils.waitForClickable(driver, removeButton).click();
    }

    public CheckoutPage clickCheckout() {
        WaitUtils.waitForClickable(driver, checkoutButton).click();
        return new CheckoutPage(driver);
    }

    public ProductsPage continueShopping() {
        WaitUtils.waitForClickable(driver, continueShoppingButton).click();
        return new ProductsPage(driver);
    }
}
