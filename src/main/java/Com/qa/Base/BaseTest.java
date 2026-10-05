package Com.qa.Base;

import Com.qa.Util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {

    protected static final Logger log = LogManager.getLogger(BaseTest.class);
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        String browser = Utils.getProperty("browser", "chrome").toLowerCase();
        boolean headless = Utils.getBooleanProperty("headless", false);
        log.info("Launching browser: {} | Headless: {}", browser, headless);

        switch (browser) {
            case "firefox":
                FirefoxOptions fOptions = new FirefoxOptions();
                if (headless) fOptions.addArguments("-headless");
                driver = new FirefoxDriver(fOptions);
                break;
            case "edge":
                EdgeOptions eOptions = new EdgeOptions();
                if (headless) eOptions.addArguments("--headless=new");
                eOptions.addArguments("--remote-allow-origins=*");
                driver = new EdgeDriver(eOptions);
                break;
            case "chrome":
            default:
                ChromeOptions cOptions = new ChromeOptions();
                if (headless) cOptions.addArguments("--headless=new");
                cOptions.addArguments("--remote-allow-origins=*");
                cOptions.addArguments("--disable-gpu");
                cOptions.addArguments("--no-sandbox");
                cOptions.addArguments("--disable-dev-shm-usage");
                cOptions.addArguments("--window-size=1920,1080");
                driver = new ChromeDriver(cOptions);
                break;
        }

        int implicit = Utils.getIntProperty("implicitWait", 10);
        int pageLoad = Utils.getIntProperty("pageLoadTimeout", 30);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicit));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoad));

        if (!headless) {
            driver.manage().window().maximize();
        }

        String targetUrl = Utils.getProperty("url", "https://naveenchandaka47-cmyk.github.io/bottomless/");
        log.info("Navigating to target URL: {}", targetUrl);
        driver.get(targetUrl);

        int explicit = Utils.getIntProperty("explicitWait", 15);
        new WebDriverWait(driver, Duration.ofSeconds(explicit))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("particle-canvas")));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            log.info("Closing browser session");
            driver.quit();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}
