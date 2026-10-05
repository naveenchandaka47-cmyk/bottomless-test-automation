package utils;

import Com.qa.Base.BaseTest;
import Com.qa.Base.ParallelBaseTest;
import Com.qa.Util.Utils;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentReportHelper implements ITestListener {

    private static final Logger log = LogManager.getLogger(ExtentReportHelper.class);
    private static final ThreadLocal<ExtentTest> testThreadLocal = new ThreadLocal<>();

    public static ExtentTest getTest() {
        return testThreadLocal.get();
    }

    public static void setTest(ExtentTest test) {
        testThreadLocal.set(test);
    }

    public static void unload() {
        testThreadLocal.remove();
    }

    @Override
    public void onStart(ITestContext context) {
        log.info("Starting Test Suite: {}", context.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();
        log.info("Executing Test: {} - Description: {}", testName, description);

        ExtentTest test = ExtentManager.getInstance().createTest(testName, description);
        setTest(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("Test PASSED: {}", result.getMethod().getMethodName());
        if (getTest() != null) {
            getTest().pass("Test passed successfully.");
            unload();
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        log.error("Test FAILED: {} - Reason: {}", testName, result.getThrowable().getMessage());

        if (getTest() != null) {
            getTest().fail(result.getThrowable());

            WebDriver driver = null;
            Object currentClass = result.getInstance();
            if (currentClass instanceof BaseTest) {
                driver = ((BaseTest) currentClass).getDriver();
            } else if (currentClass instanceof ParallelBaseTest) {
                driver = ParallelBaseTest.getDriver();
            }

            if (driver != null) {
                try {
                    String base64 = Utils.getBase64Screenshot(driver);
                    if (!base64.isEmpty()) {
                        getTest().fail("Failure Screenshot",
                                MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build());
                    }
                } catch (Exception e) {
                    log.warn("Failed to attach failure screenshot: {}", e.getMessage());
                }
            }
            unload();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("Test SKIPPED: {}", result.getMethod().getMethodName());
        if (getTest() != null) {
            getTest().skip(result.getThrowable());
            unload();
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        log.info("Completed Test Suite: {}", context.getName());
        try {
            ExtentManager.getInstance().flush();
            log.info("Extent report written to disk.");
        } catch (Exception e) {
            log.error("Error writing Extent report: {}", e.getMessage());
        }
    }
}
