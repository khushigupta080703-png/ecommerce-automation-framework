package com.framework.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Parent class for all Page Object classes.
 * Initializes @FindBy elements via PageFactory so subclasses just
 * declare WebElements and business methods.
 */
public abstract class BasePage {

    protected WebDriver driver;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
