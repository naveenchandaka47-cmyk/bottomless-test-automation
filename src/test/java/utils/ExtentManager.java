package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {

    private static ExtentReports extent;

    private ExtentManager() {
    }

    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String reportPath = "reports/Bottomless_Regression_Report_" + timestamp + ".html";

            File reportsDir = new File("reports");
            if (!reportsDir.exists()) {
                reportsDir.mkdirs();
            }

            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setTheme(Theme.DARK);
            sparkReporter.config().setDocumentTitle("Bottomless - Regression Automation Report");
            sparkReporter.config().setReportName("Bottomless Web Application Regression Suite");
            sparkReporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Application", "Bottomless — An Infinite Descent");
            extent.setSystemInfo("Target URL", "https://naveenchandaka47-cmyk.github.io/bottomless/");
            extent.setSystemInfo("Platform", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("Automation Framework", "Selenium WebDriver + TestNG + POM");
        }
        return extent;
    }
}
