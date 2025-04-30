import Base.BaseTest;
import Base.ConfigReader;
import dataprovider.UserDataProvider;
import org.junit.Assert;
import org.springframework.context.annotation.DependsOn;
import org.testng.annotations.Test;
import pages.automationexercise.HomePage;
import pages.automationexercise.LoginPage;
import pojos.User;
import utils.LoggerUtil;

import java.util.logging.Logger;

public class loginPageTest extends BaseTest {
    private String loginUrl= ConfigReader.getInstance().get("baseUrl");

    @Test
    public void loginTest(){
        LoggerUtil.logInfo("Starting login test...");
        driver.get(loginUrl);
        LoggerUtil.logInfo("Navigated to Login Page");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs(ConfigReader.getInstance().get("email"),ConfigReader.getInstance().get("password"));
        LoggerUtil.logInfo("Login completed with configured credentials.");

    }
    @Test(dataProvider = "userDataProvider", dataProviderClass = UserDataProvider.class)
    public void registerUser(User userData){
        LoggerUtil.logInfo("Starting registration test with email: " + userData.getEmail());
        driver.get(loginUrl);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.registerNewuser(userData.getEmail(), userData.getPassword());
        LoggerUtil.logInfo("User registration completed for: " + userData.getEmail());

    }

    @Test(dataProvider = "IncorrectUserData", dataProviderClass = UserDataProvider.class)
    public void loginWithIncorrectCreds(String email, String pwd){
        LoggerUtil.logInfo("Testing login with incorrect credentials: " + email);
        driver.get(loginUrl);
        HomePage page = new HomePage(driver);
        LoginPage loginPage=page.clickSignUpButton();
        loginPage.loginAs(email, pwd);
        boolean isErrorDisplayed = page.errorMessageDisplayed();
        LoggerUtil.logInfo("Error message displayed: " + isErrorDisplayed);
        Assert.assertTrue("error message not present",page.errorMessageDisplayed());

    }

    @Test
    public void logoutTest() {
        LoggerUtil.logInfo("Starting logout test...");
        driver.get(loginUrl);
        LoginPage loginpage = new LoginPage(driver);
        HomePage homePage=loginpage.loginAs(ConfigReader.getInstance().get("email"),ConfigReader.getInstance().get("password"));
        homePage.logOutFromHomePage();
        boolean isLoginVisible = loginpage.isLoginButtonDisplayed();
        LoggerUtil.logInfo("Logout status - Login button visible: " + isLoginVisible);
        Assert.assertTrue("Logout failed - Login button not displayed.",loginpage.isLoginButtonDisplayed());
    }

    @Test()
    public void registerUserWithSameEmail(){
        LoggerUtil.logInfo("Testing registration with an already registered email");
        driver.get(loginUrl);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.registerNewuser(ConfigReader.getInstance().get("email"),ConfigReader.getInstance().get("name"));
        boolean errorDisplayed = loginPage.errorMessageForExistingEmail();
        LoggerUtil.logInfo("Duplicate email error displayed: " + errorDisplayed);
        Assert.assertTrue("Error message is not displayed",loginPage.errorMessageForExistingEmail());

    }


}
