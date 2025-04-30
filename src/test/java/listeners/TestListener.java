package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ExtentReportManager;
import utils.LoggerUtil;

import java.io.File;

public class TestListener implements ITestListener {

    private ExtentReports extentReports;


    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        ExtentReportManager.createTest(testName);  // Create a new test node in ExtentReports
        ExtentTest extentTest = ExtentReportManager.getTest();
        LoggerUtil.setCurrentTest(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // Log success in ExtentReports
        ExtentReportManager.getTest().pass("Test passed successfully");
    }


    @Override
    public void onTestFailure(ITestResult result) {
        Throwable throwable = result.getThrowable();
        String methodName = result.getMethod().getMethodName();
        String errorMessage = (throwable != null) ? throwable.getMessage() : "Unknown error";

        // Log failure
        LoggerUtil.logError("Test failed: " + methodName + " - " + errorMessage);

        ExtentTest extentTest = LoggerUtil.getCurrentTest();
        if (extentTest != null) {
            extentTest.fail("Test failed: " + errorMessage);
        }

        // Capture screenshot
        captureScreenshot(result);

        // Conditional logging based on test type
        String className = result.getTestClass().getName();
        if (className.contains("API")) {
            LoggerUtil.logApiStatus("/dummy/endpoint", 500, "Internal Server Error");
        } else if (className.contains("Selenium")) {
            LoggerUtil.logSeleniumError(methodName,
                    (throwable instanceof Exception) ? (Exception) throwable : new Exception(errorMessage));
        }
    }



    @Override
    public void onTestSkipped(ITestResult result) {
        // Log skipped tests
        ExtentReportManager.getTest().skip("Test skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onStart(ITestContext context) {
        // Initialize ExtentReports before starting tests
        ExtentReportManager.initReport();
    }

    @Override
    public void onFinish(ITestContext context) {
        // Flush ExtentReports after all tests are finished
        ExtentReportManager.flushReport();
    }

    // Utility function to capture screenshots
    private void captureScreenshot(ITestResult result) {
        WebDriver driver = ((WebDriver) result.getTestContext().getAttribute("driver"));
        if (driver instanceof TakesScreenshot) {
            try {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                String screenshotPath = "screenshots/" + result.getMethod().getMethodName() + ".png";
                File destination = new File(screenshotPath);
                FileUtils.copyFile(screenshot, destination);

                // Attach screenshot to ExtentReport
                ExtentReportManager.getTest().addScreenCaptureFromPath(screenshotPath);
            } catch (Exception e) {
                LoggerUtil.logException(e);
            }
        }
    }
}
