package Com.qa.Pages;

import Com.qa.Util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
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
        int explicitWait = Utils.getIntProperty("explicitWait", 15);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
        // Initialize PageFactory WebElements
        PageFactory.initElements(driver, this);
    }

    protected WebElement waitForVisibility(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected WebElement waitForClickability(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForClickability(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void click(WebElement element) {
        try {
            waitForClickability(element).click();
        } catch (ElementClickInterceptedException e) {
            log.warn("Click intercepted on WebElement, falling back to JavaScript click.");
            Utils.clickViaJS(driver, element);
        }
    }

    protected void click(By locator) {
        try {
            waitForClickability(locator).click();
        } catch (ElementClickInterceptedException e) {
            log.warn("Click intercepted on By locator {}, falling back to JavaScript click.", locator);
            Utils.clickViaJS(driver, driver.findElement(locator));
        }
    }

    protected String getText(WebElement element) {
        return waitForVisibility(element).getText().trim();
    }

    protected String getText(By locator) {
        return waitForVisibility(locator).getText().trim();
    }

    protected boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    protected boolean isDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    protected boolean hasClass(WebElement element, String className) {
        try {
            String classes = element.getAttribute("class");
            return classes != null && classes.contains(className);
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
        Utils.pause(millis);
    }
}
