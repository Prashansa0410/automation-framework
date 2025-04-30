package pages.automationexercise;

import Base.ConfigReader;
import enums.WaitType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WaitUtils;

import java.time.Duration;
import java.util.List;

public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;
    public HomePage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        PageFactory.initElements(driver,this);
    }

    private String loginUrl= ConfigReader.getInstance().get("baseUrl")+ConfigReader.getInstance();

    @FindBy(xpath="//a[contains(text(),'Signup / Login')]")
    private WebElement loginButton;

    @FindBy(xpath="//a[contains(text(),'Home')]")
    private WebElement homePage;

    @FindBy(xpath="//a[contains(text(),'Logout')]")
    private WebElement logoutButton;

    @FindBy(xpath="//a[contains(text(),'Delete Account')]")
    private WebElement deleteAccount;

    @FindBy(xpath="//a[contains(text(),'API Testing')]")
    private WebElement apiTesting;

    @FindBy(xpath="//a[contains(text(),'Video Tutorials')]")
    private WebElement videoTutorials;

    @FindBy(xpath="//img[src='/static/images/home/logo.png']")
    private WebElement logo;

    @FindBy(linkText = "Contact us")
    private WebElement contactusLink;

    @FindBy(xpath="//a[contains(text(),' Test Cases')]")
    private WebElement testcasesLink;

    @FindBy(linkText = " Products")
    private WebElement productsLink;

    @FindBy(linkText = " Cart")
    private WebElement cartLink;

    @FindBy(id="accordian")
    private List<WebElement> categoryProducts;

    @FindBy(xpath="//div[class='left-sidebar']")
    private WebElement categotySelection;

    @FindBy(xpath="//div[id='slider-carousel']//img")
    private WebElement sliderImages;

    @FindBy(id="footer")
    private WebElement footer;


    @FindBy(className = ".nav.nav-pills.nav-stacked")
    private WebElement brandName;

    @FindBy(className = "features_items")
    private WebElement featureItems;

    @FindBy(id="susbscribe_email")
    private WebElement subscribeEmail;

    @FindBy(xpath="//h2[text()='Subscription']")
    private WebElement subscriptionHeading;

    @FindBy(id="subscribe")
    private WebElement buttonSubscribe;

    @FindBy(xpath="//p[contains(text(),'Your email or password is incorrect!')]")
    private WebElement errorMessage;

    public void clickSubscribe(){
        WaitUtils.waitForElemenetToBeClickable(driver,buttonSubscribe,10, WaitType.EXPLICIT).click();
    }
    public void inputSubscribeEmail(String email){
        WaitUtils.waitForVisibility(driver,subscribeEmail,10,WaitType.EXPLICIT).sendKeys(email);
    }

    public void categorySelection(String category){
        WaitUtils.waitForVisibility(driver,categoryProducts.get(0),10, WaitType.EXPLICIT).getText();
    }

    public LoginPage clickSignUpButton(){
        WaitUtils.waitForVisibility(driver,loginButton,10,WaitType.EXPLICIT).click();
        return new LoginPage(driver);
    }

    public Boolean homePageHeading(){
        WaitUtils.waitForVisibility(driver,homePage,10,WaitType.EXPLICIT);
        return homePage.isDisplayed();
    }

    public Boolean errorMessageDisplayed(){

        return errorMessage.isDisplayed();
    }

    public void logOutFromHomePage(){
        wait.until(ExpectedConditions.visibilityOf(logoutButton)).click();
    }

    public Boolean signUpVisible(){
        return WaitUtils.waitForVisibility(driver,loginButton,10,WaitType.EXPLICIT).isDisplayed();
    }

    public contactUsPage clickContactUs(){
        WaitUtils.waitForVisibility(driver,contactusLink,10,WaitType.EXPLICIT).click();
        return new contactUsPage(driver);
    }

    public testCasePage clickTestCaseLink(){
        WaitUtils.waitForVisibility(driver,testcasesLink,10,WaitType.EXPLICIT);
        return new testCasePage(driver);
    }

}
