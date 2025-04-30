import Base.BaseTest;
import Base.ConfigReader;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.automationexercise.HomePage;
import pages.automationexercise.contactUsPage;

public class contactUsTest extends BaseTest {
    private String loginUrl= ConfigReader.getInstance().get("baseUrl");

    @Test
    public void validateContactUsFormTest(){
        driver.get(loginUrl);
        HomePage page = new HomePage(driver);
        contactUsPage contact= page.clickContactUs();
        contact.fillContactUsForm(ConfigReader.getInstance().get("name"),ConfigReader.getInstance().get("email"),"hello","hiiiiiii","/Users/prashansa/Downloads/test.webp" );
        contact.acceptAllertMessage();
        Assert.assertTrue(contact.successMessage(),"successmessage not displayed");
        page=contact.clickHomePage();
        Assert.assertTrue(page.homePageHeading(),"Didnt land to home page");


    }


}
