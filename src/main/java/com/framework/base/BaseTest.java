package com.framework.base;

import com.framework.utils.ConfigReader;
import com.framework.utils.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Every test class extends this. Handles browser launch/teardown
 * around each @Test method so tests stay independent of each other.
 */
public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        DriverManager.initDriver();
        driver = DriverManager.getDriver();
        driver.get(ConfigReader.get("url"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
