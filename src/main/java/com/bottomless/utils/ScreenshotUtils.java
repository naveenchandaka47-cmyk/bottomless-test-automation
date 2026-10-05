package com.bottomless.utils;

import com.bottomless.constants.FrameworkConstants;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class ScreenshotUtils {

    private static final Logger log = LogManager.getLogger(ScreenshotUtils.class);

    private ScreenshotUtils() {
    }

    public static String getBase64Screenshot(WebDriver driver) {
        if (driver == null) {
            return "";
        }
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
    }

    public static String captureToFile(WebDriver driver, String screenshotName) {
        if (driver == null) {
            return "";
        }
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String destinationPath = FrameworkConstants.SCREENSHOTS_DIR + screenshotName + "_" + timestamp + ".png";

        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destFile = new File(destinationPath);

        try {
            FileUtils.copyFile(srcFile, destFile);
            log.info("Screenshot saved successfully to: {}", destinationPath);
        } catch (IOException e) {
            log.error("Failed to save screenshot to {}: {}", destinationPath, e.getMessage());
        }

        return destinationPath;
    }
}
