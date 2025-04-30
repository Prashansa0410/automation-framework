import Base.BaseTest;
import Base.ConfigReader;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.automationexercise.HomePage;
import pages.automationexercise.testCasePage;

public class testCaseTest extends BaseTest {

    private String loginUrl= ConfigReader.getInstance().get("baseUrl");

    @Test
    public void verifyTestCasePage(){
        driver.get(loginUrl);
        HomePage homePage = new HomePage(driver);
        testCasePage testcase=homePage.clickTestCaseLink();
        Assert.assertTrue(testcase.testCaseHeadingDisplayed(),"testcases page not landed");

    }
}
