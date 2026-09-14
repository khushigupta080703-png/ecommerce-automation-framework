package com.framework.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Captures a screenshot and saves it under /screenshots with a
 * timestamped, test-specific filename. Used by TestListener on failure.
 */
public class ScreenshotUtils {

    private static final String SCREENSHOT_DIR = "screenshots";

    private ScreenshotUtils() {
    }

    public static String captureScreenshot(WebDriver driver, String testName) {
        try {
            Files.createDirectories(Paths.get(SCREENSHOT_DIR));

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = testName + "_" + timestamp + ".png";
            String filePath = SCREENSHOT_DIR + File.separator + fileName;

            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destination = new File(filePath);
            Files.copy(source.toPath(), destination.toPath());

            return filePath;
        } catch (IOException e) {
            System.err.println("Failed to capture screenshot for test: " + testName);
            e.printStackTrace();
            return null;
        }
    }
}
