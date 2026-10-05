package Com.qa.Util;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

public class Utils {

    private static final Logger log = LogManager.getLogger(Utils.class);
    private static final Properties prop = new Properties();
    public static final String CONFIG_PATH = "src/main/java/Com/qa/Config/Config.properties";

    static {
        try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
            prop.load(fis);
            log.info("Loaded config from: {}", CONFIG_PATH);
        } catch (IOException e) {
            log.warn("Could not load config from {}: {}", CONFIG_PATH, e.getMessage());
        }
    }

    public static String getProperty(String key) {
        String sysProp = System.getProperty(key);
        if (sysProp != null && !sysProp.trim().isEmpty()) {
            return sysProp.trim();
        }
        return prop.getProperty(key, "").trim();
    }

    public static String getProperty(String key, String defaultValue) {
        String val = getProperty(key);
        return val.isEmpty() ? defaultValue : val;
    }

    public static int getIntProperty(String key, int defaultValue) {
        try {
            return Integer.parseInt(getProperty(key));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static boolean getBooleanProperty(String key, boolean defaultValue) {
        String val = getProperty(key);
        if (val.isEmpty()) return defaultValue;
        return Boolean.parseBoolean(val);
    }

    // Explicit wait utilities
    public static WebElement waitForVisibility(WebDriver driver, By locator, int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickability(WebDriver driver, By locator, int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    // JavaScript utilities
    public static void clickViaJS(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public static void simulateWheelScroll(WebDriver driver, int deltaY) {
        String script = "window.dispatchEvent(new WheelEvent('wheel', { deltaY: arguments[0] }));";
        ((JavascriptExecutor) driver).executeScript(script, deltaY);
    }

    public static void setSliderValue(WebDriver driver, WebElement slider, double value) {
        String script = "arguments[0].value = arguments[1]; " +
                        "arguments[0].dispatchEvent(new Event('input', { bubbles: true })); " +
                        "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));";
        ((JavascriptExecutor) driver).executeScript(script, slider, String.valueOf(value));
    }

    // Screenshot utilities
    public static String getBase64Screenshot(WebDriver driver) {
        if (driver == null) return "";
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
    }

    public static String takeScreenshotToFile(WebDriver driver, String testName) {
        if (driver == null) return "";
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String destPath = "reports/screenshots/" + testName + "_" + timestamp + ".png";
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File(destPath));
            log.info("Saved screenshot to {}", destPath);
        } catch (IOException e) {
            log.error("Failed to capture screenshot: {}", e.getMessage());
        }
        return destPath;
    }

    public static void pause(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
