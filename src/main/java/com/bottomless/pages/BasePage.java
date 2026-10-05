package com.bottomless.pages;

import com.bottomless.constants.FrameworkConstants;
import com.bottomless.utils.JavaScriptUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class BasePage {

    protected final Logger log = LogManager.getLogger(getClass());
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(FrameworkConstants.DEFAULT_EXPLICIT_WAIT));
    }

    protected WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForClickability(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected boolean waitForInvisibility(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected void click(By locator) {
        try {
            waitForClickability(locator).click();
        } catch (ElementClickInterceptedException e) {
            log.warn("Standard click intercepted on {}, executing JS click fallback.", locator);
            JavaScriptUtils.click(driver, driver.findElement(locator));
        }
    }

    protected String getText(By locator) {
        return waitForVisibility(locator).getText().trim();
    }

    protected boolean isDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    protected boolean hasClass(By locator, String className) {
        try {
            String classes = driver.findElement(locator).getAttribute("class");
            return classes != null && classes.contains(className);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public void pause(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
