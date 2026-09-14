package com.framework.pages;

import com.framework.base.BasePage;
import com.framework.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProductsPage extends BasePage {

    @FindBy(className = "inventory_item")
    private List<WebElement> productItems;

    @FindBy(css = ".shopping_cart_link")
    private WebElement cartIcon;

    @FindBy(css = ".shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(css = "[data-test='title']")
    private WebElement pageTitle;

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isPageDisplayed() {
        return WaitUtils.waitForVisibility(driver, pageTitle).isDisplayed();
    }

    /**
     * Adds a product to the cart by its visible name, e.g. "Sauce Labs Backpack".
     * Locator is built dynamically from the product name so this method
     * works for any product on the page without needing one @FindBy per item.
     */
    public void addProductToCart(String productName) {
        By addButton = By.xpath(
                "//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']" +
                        "//button[contains(@class,'btn_inventory')]");
        WaitUtils.waitForClickable(driver, addButton).click();
    }

    public void removeProductFromCart(String productName) {
        By removeButton = By.xpath(
                "//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']" +
                        "//button[contains(@class,'btn_inventory')]");
        WaitUtils.waitForClickable(driver, removeButton).click();
    }

    public int getProductCount() {
        return productItems.size();
    }

    public int getCartCount() {
        try {
            return Integer.parseInt(WaitUtils.waitForVisibility(driver, cartBadge).getText());
        } catch (Exception e) {
            return 0;
        }
    }

    public CartPage goToCart() {
        WaitUtils.waitForClickable(driver, cartIcon).click();
        return new CartPage(driver);
    }
}
