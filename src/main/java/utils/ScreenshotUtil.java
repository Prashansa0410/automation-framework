package utils;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    private static final String SCREENSHOT_DIR = "screenshots/";

    static {
        try {
            Files.createDirectories(Paths.get(SCREENSHOT_DIR));
        } catch (IOException e) {
            LoggerUtil.logException(e);
        }
    }

    public static String takeScreenshot(WebDriver driver, String screenshotName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filePath = SCREENSHOT_DIR + screenshotName + "_" + timestamp + ".png";

        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Path dest = Paths.get(filePath);
            Files.copy(src.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
            LoggerUtil.logInfo("Screenshot taken: " + filePath);
            return filePath;
        } catch (IOException e) {
            LoggerUtil.logException(e);
            return null;
        }
    }

    public static void attachScreenshotToReport(WebDriver driver, String screenshotName) {
        String path = takeScreenshot(driver, screenshotName);
        if (path != null) {
            LoggerUtil.getCurrentTest().log(
                    Status.INFO,
                    "Screenshot: " + screenshotName,
                    MediaEntityBuilder.createScreenCaptureFromPath(path).build()
            );
        }
    }

    public static void attachFailureScreenshot(WebDriver driver, String stepName) {
        String path = takeScreenshot(driver, "FAIL_" + stepName);
        if (path != null) {
            LoggerUtil.getCurrentTest().fail(
                    "Failure Screenshot for: " + stepName,
                    MediaEntityBuilder.createScreenCaptureFromPath(path).build()
            );
        }
    }
}
