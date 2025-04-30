import Base.BaseTest;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import pages.automationexercise.LoginPage;

public class loginTest extends BaseTest {


    @Test
    public void loginTest(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs("email","password");
    }

    @Test
    public void registerUser(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs("email","password");
    }
}
