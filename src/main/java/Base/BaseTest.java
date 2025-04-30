package Base;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.LoggerUtil;

import java.sql.Driver;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp(ITestContext context){
        LoggerUtil.logInfo("Setting up the test environment");
        String browser = ConfigReader.getInstance().get("browser");
        DriverFactory driverFactory = new DriverFactory();
        driver=driverFactory.initDriver(browser);
        context.setAttribute("driver",driver);
    }

    @AfterMethod
    public void tearDown(){
        LoggerUtil.logInfo("cleaning up the driver");
        if(driver!=null){
            driver.quit();
        }
    }
}
