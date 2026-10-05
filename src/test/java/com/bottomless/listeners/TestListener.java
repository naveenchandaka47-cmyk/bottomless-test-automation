package com.bottomless.listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.bottomless.driver.DriverManager;
import com.bottomless.reports.ExtentManager;
import com.bottomless.reports.ExtentTestManager;
import com.bottomless.utils.ScreenshotUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    private static final Logger log = LogManager.getLogger(TestListener.class);

    @Override
    public void onStart(ITestContext context) {
        log.info("Starting Test Suite: {}", context.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();
        log.info("Executing Test: {} - Description: {}", testName, description);

        ExtentTestManager.setTest(ExtentManager.getInstance().createTest(testName, description));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("Test PASSED: {}", result.getMethod().getMethodName());
        if (ExtentTestManager.getTest() != null) {
            ExtentTestManager.getTest().pass("Test passed successfully.");
            ExtentTestManager.unload();
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        log.error("Test FAILED: {} - Reason: {}", testName, result.getThrowable().getMessage());

        if (ExtentTestManager.getTest() != null) {
            ExtentTestManager.getTest().fail(result.getThrowable());
            try {
                String base64Screenshot = ScreenshotUtils.getBase64Screenshot(DriverManager.getDriver());
                if (!base64Screenshot.isEmpty()) {
                    ExtentTestManager.getTest().fail(
                            "Failure Screenshot",
                            MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build()
                    );
                }
            } catch (Exception e) {
                log.warn("Failed to attach failure screenshot: {}", e.getMessage());
            }
            ExtentTestManager.unload();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("Test SKIPPED: {}", result.getMethod().getMethodName());
        if (ExtentTestManager.getTest() != null) {
            ExtentTestManager.getTest().skip(result.getThrowable());
            ExtentTestManager.unload();
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        log.info("Completed Test Suite: {}", context.getName());
        try {
            ExtentManager.getInstance().flush();
            log.info("Extent report flushed to disk.");
        } catch (Exception e) {
            log.error("Error flushing ExtentReports: {}", e.getMessage());
        }
    }
}
