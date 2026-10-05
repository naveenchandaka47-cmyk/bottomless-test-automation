package com.bottomless.base;

import com.bottomless.constants.FrameworkConstants;
import com.bottomless.driver.DriverFactory;
import com.bottomless.driver.DriverManager;
import com.bottomless.listeners.TestListener;
import com.bottomless.utils.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import java.time.Duration;

@Listeners(TestListener.class)
public abstract class BaseTest {

    protected final Logger log = LogManager.getLogger(getClass());

    @BeforeMethod
    public void setUp() {
        WebDriver driver = DriverFactory.createDriver();
        DriverManager.setDriver(driver);

        String targetUrl = ConfigReader.get("app.url", FrameworkConstants.DEFAULT_URL);
        log.info("Navigating to target URL: {}", targetUrl);
        driver.get(targetUrl);

        // Ensure root app container has loaded
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(FrameworkConstants.DEFAULT_EXPLICIT_WAIT));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("particle-canvas")));
    }

    @AfterMethod
    public void tearDown() {
        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            log.info("Tearing down WebDriver session");
            driver.quit();
            DriverManager.unload();
        }
    }

    protected WebDriver getDriver() {
        return DriverManager.getDriver();
    }
}
