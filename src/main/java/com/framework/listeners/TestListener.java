package com.framework.listeners;

import com.framework.utils.DriverManager;
import com.framework.utils.ScreenshotUtils;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Registered via testng.xml <listeners> so it applies to every suite.
 * On failure: grabs a screenshot tagged with the test name for debugging.
 */
public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("STARTING TEST: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("PASSED: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        System.out.println("FAILED: " + testName);

        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            String path = ScreenshotUtils.captureScreenshot(driver, testName);
            if (path != null) {
                System.out.println("Screenshot saved to: " + path);
                // Attach to TestNG's HTML report
                org.testng.Reporter.log("<a href='" + path + "'>Screenshot</a>");
            }
        }

        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            System.out.println("Failure reason: " + throwable.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("SKIPPED: " + result.getMethod().getMethodName());
    }

    @Override
    public void onStart(org.testng.ITestContext context) {
        System.out.println("=== Test Suite Started: " + context.getName() + " ===");
    }

    @Override
    public void onFinish(org.testng.ITestContext context) {
        System.out.println("=== Test Suite Finished: " + context.getName() + " ===");
        System.out.println("Passed: " + context.getPassedTests().size()
                + " | Failed: " + context.getFailedTests().size()
                + " | Skipped: " + context.getSkippedTests().size());
    }
}
