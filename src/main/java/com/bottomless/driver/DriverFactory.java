package com.bottomless.driver;

import com.bottomless.constants.FrameworkConstants;
import com.bottomless.utils.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public final class DriverFactory {

    private static final Logger log = LogManager.getLogger(DriverFactory.class);

    private DriverFactory() {
    }

    public static WebDriver createDriver() {
        String browser = ConfigReader.get("browser", "chrome").toLowerCase();
        boolean headless = ConfigReader.getBoolean("headless", false);

        log.info("Initializing WebDriver for browser: {} | Headless: {}", browser, headless);

        WebDriver driver;
        switch (browser) {
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("-headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) {
                    edgeOptions.addArguments("--headless=new");
                }
                edgeOptions.addArguments("--remote-allow-origins=*");
                edgeOptions.addArguments("--disable-gpu");
                edgeOptions.addArguments("--no-sandbox");
                driver = new EdgeDriver(edgeOptions);
                break;

            case "chrome":
            default:
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments("--headless=new");
                }
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--window-size=1920,1080");
                driver = new ChromeDriver(chromeOptions);
                break;
        }

        int implicitWait = ConfigReader.getInt("implicit.wait", FrameworkConstants.DEFAULT_IMPLICIT_WAIT);
        int pageLoadTimeout = ConfigReader.getInt("page.load.timeout", FrameworkConstants.DEFAULT_PAGE_LOAD_TIMEOUT);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));

        if (!headless) {
            driver.manage().window().maximize();
        }

        return driver;
    }
}
