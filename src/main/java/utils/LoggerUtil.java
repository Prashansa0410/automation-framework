package utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import java.io.*;
import java.util.*;
import java.util.logging.*;

public class LoggerUtil {

    private static final Logger logger = Logger.getLogger(LoggerUtil.class.getName());
    private static final String LOG_FILE_PATH = "logs/test-log.log";

    // --- Static block to set up file logging ---
    static {
        try {
            File logDir = new File("logs");
            if (!logDir.exists()) logDir.mkdir();

            FileHandler fileHandler = new FileHandler(LOG_FILE_PATH, true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            System.err.println("Logger setup failed: " + e.getMessage());
        }
    }

    public static void setCurrentTest(ExtentTest extentTest) {
        try {
            if (extentTest != null) {
                LoggerUtil.logger.info("Setting current test: " + extentTest.getModel().getName());
            }
        } catch (Exception e) {
            LoggerUtil.logger.severe("Failed to set current test: " + e.getMessage());
        }
    }

    // --- Internal ExtentTest fetcher ---
    public static ExtentTest getCurrentTest() {
        try {
            return ExtentReportManager.getTest(); // Ensure ExtentReportManager uses ThreadLocal
        } catch (Exception e) {
            return null; // Fail-safe
        }
    }

    // --- Basic Logging ---
    public static void logInfo(String message) {
        logger.info(message);
        if (getCurrentTest() != null) getCurrentTest().log(Status.INFO, message);
    }


    public static void logWarning(String message) {
        logger.warning(message);
        if (getCurrentTest() != null) getCurrentTest().log(Status.WARNING, message);
    }

    public static void logError(String message) {
        logger.severe(message);
        if (getCurrentTest() != null) getCurrentTest().log(Status.FAIL, message);
    }

    public static void logException(Exception e) {
        logger.log(Level.SEVERE, e.getMessage(), e);
        if (getCurrentTest() != null) getCurrentTest().log(Status.FAIL, e);
    }

    // --- API-specific logging ---
    public static void logApiStatus(String endpoint, int statusCode, String responseBody) {
        String message = String.format("API call to [%s] returned status code [%d]. Response: %s", endpoint, statusCode, responseBody);
        if (statusCode >= 400) {
            logError(message);
        } else {
            logInfo(message);
        }
    }

    // --- Selenium-specific logging ---
    public static void logSeleniumError(String stepDescription, Exception e) {
        String message = String.format("Selenium Step Failed: [%s] | Exception: %s", stepDescription, e.toString());
        logError(message);
    }

    // --- Post-run Log Analysis ---
    public static void summarizeErrorsByType(String type) {
        Map<String, Integer> errorCounts = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                boolean matched = false;

                // Match API codes
                if ((type.equalsIgnoreCase("API") || type.equalsIgnoreCase("ALL")) &&
                        line.matches(".*\\b(4\\d{2}|5\\d{2})\\b.*")) {
                    matched = true;
                    String code = line.replaceAll(".*\\b(\\d{3})\\b.*", "$1");
                    errorCounts.put("HTTP_" + code, errorCounts.getOrDefault("HTTP_" + code, 0) + 1);
                }

                // Match Selenium exceptions
                if ((type.equalsIgnoreCase("UI") || type.equalsIgnoreCase("ALL")) &&
                        line.matches(".*(NoSuchElementException|TimeoutException|WebDriverException).*")) {
                    matched = true;
                    for (String keyword : Arrays.asList("NoSuchElementException", "TimeoutException", "WebDriverException")) {
                        if (line.contains(keyword)) {
                            errorCounts.put(keyword, errorCounts.getOrDefault(keyword, 0) + 1);
                        }
                    }
                }

                // Log matched lines to report
                if (matched && getCurrentTest() != null) {
                    getCurrentTest().log(Status.WARNING, "Log match: " + line);
                }
            }

            // Print summary to report
            if (getCurrentTest() != null) {
                getCurrentTest().info("--- Log Summary ---");
                errorCounts.forEach((k, v) -> getCurrentTest().warning(k + ": " + v + " occurrence(s)"));
            }

        } catch (IOException e) {
            logException(e);
        }
    }
}
