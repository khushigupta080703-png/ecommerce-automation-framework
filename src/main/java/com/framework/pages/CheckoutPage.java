package com.framework.pages;

import com.framework.base.BasePage;
import com.framework.utils.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutPage extends BasePage {

    // --- Step One: customer information ---
    @FindBy(id = "first-name")
    private WebElement firstNameField;

    @FindBy(id = "last-name")
    private WebElement lastNameField;

    @FindBy(id = "postal-code")
    private WebElement postalCodeField;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    // --- Step Two: overview ---
    @FindBy(id = "finish")
    private WebElement finishButton;

    @FindBy(className = "summary_total_label")
    private WebElement totalLabel;

    // --- Step Three: confirmation ---
    @FindBy(css = "[data-test='complete-header']")
    private WebElement confirmationHeader;

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void enterCustomerInfo(String firstName, String lastName, String postalCode) {
        WaitUtils.waitForVisibility(driver, firstNameField).sendKeys(firstName);
        lastNameField.sendKeys(lastName);
        postalCodeField.sendKeys(postalCode);
    }

    public CheckoutPage clickContinue() {
        WaitUtils.waitForClickable(driver, continueButton).click();
        return this;
    }

    /**
     * Use after clickContinue() when a successful transition to step two
     * (the order overview) is actually expected. Kept separate from
     * clickContinue() because that method is also used in the validation-failure
     * path, where the page intentionally stays on step one.
     */
    public CheckoutPage waitForOverviewPage() {
        WaitUtils.waitForUrlContains(driver, "checkout-step-two");
        return this;
    }

    public String getFormErrorMessage() {
        return WaitUtils.waitForVisibility(driver, errorMessage).getText();
    }

    public String getOrderTotal() {
        return WaitUtils.waitForVisibility(driver, totalLabel).getText();
    }

    public void clickFinish() {
        WaitUtils.waitForClickable(driver, finishButton).click();
    }

    public String getConfirmationMessage() {
        return WaitUtils.waitForVisibility(driver, confirmationHeader).getText();
    }

    /**
     * Full happy-path checkout: fills customer info, continues past
     * the overview step, and finishes the order.
     */
    public String completeCheckout(String firstName, String lastName, String postalCode) {
        enterCustomerInfo(firstName, lastName, postalCode);
        clickContinue();
        waitForOverviewPage();
        clickFinish();
        return getConfirmationMessage();
    }
}
