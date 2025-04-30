package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {

    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    // === Initialize Spark report (called once per suite) ===
    public static void initReport() {
        if (extent == null) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            File dir = new File("reports");
            if(!dir.exists()){
                dir.mkdir();
            }
            String reportPath = "reports/ExtentReport_" + timestamp + ".html";

            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setTheme(Theme.STANDARD);
            sparkReporter.config().setDocumentTitle("Automation Report");
            sparkReporter.config().setReportName("Execution Report");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("Tester", "Your Name");
        }
    }

    // === Create test node for current thread ===
    public static void createTest(String testName) {
        ExtentTest test = extent.createTest(testName);
        extentTest.set(test);
    }

    // === Get current test for logging ===
    public static ExtentTest getTest() {
        return extentTest.get();
    }

    // === Flush final report (usually in onFinish) ===
    public static void flushReport() {
        if (extent != null) {
            extent.flush();  // This will flush the reports correctly
        }
    }

    // === Clean up ThreadLocal ===
    public static void unload() {
        extentTest.remove();
    }
}
