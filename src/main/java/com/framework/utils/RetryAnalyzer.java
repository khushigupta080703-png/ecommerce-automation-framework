package com.framework.utils;

import org.testng.ITestResult;
import org.testng.IRetryAnalyzer;

/**
 * Automatically retries a failed test once before letting it count as a
 * real failure. Useful for external/live sites (like SauceDemo) where an
 * occasional slow page load can cause a one-off timeout that isn't a
 * real bug in the test or the framework.
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int MAX_RETRY_COUNT = 2;

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < MAX_RETRY_COUNT) {
            retryCount++;
            System.out.println("Retrying test: " + result.getMethod().getMethodName()
                    + " | Attempt: " + (retryCount + 1));
            return true;
        }
        return false;
    }
}
