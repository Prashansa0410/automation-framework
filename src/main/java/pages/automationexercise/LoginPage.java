package pages.automationexercise;

import Base.ConfigReader;
import enums.WaitType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WaitUtils;

public class LoginPage {

    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//h2[contains(text(),'Login to your account')]")
    private WebElement loginpageHeading;

    @FindBy(xpath = "//p[contains(text(),'Email Address already exist!')]")
    private WebElement errorMessageForExistingEmail;

    @FindBy(css = "input[data-qa='login-email']")
    private WebElement inputEmail;

    @FindBy(css = "input[data-qa='login-password']")
    private WebElement inputPassword;

    @FindBy(css = "button[data-qa='login-button']")
    private WebElement loginButton;

    @FindBy(xpath = "//src[alt='Website for automation practice']")
    private WebElement titleOfLogin;

    @FindBy(linkText = "Signup / Login")
    private WebElement loginLink;

    @FindBy(css = "input[data-qa='signup-name']")
    private WebElement signupName;

    @FindBy(css = "input[data-qa='signup-email']")
    private WebElement signupEmail;

    @FindBy(css = "button[data-qa='signup-button']")
    private WebElement buttonSignup;

    @FindBy(xpath = "//h2[text()='New User Signup!']")
    private WebElement newUsersignup;

    public void goToLoginPage() {
        WaitUtils.waitForElemenetToBeClickable(driver, loginLink, 10, WaitType.EXPLICIT).click();
    }

    public void inputEmail(String email) {
        WaitUtils.waitForVisibility(driver, inputEmail, 10, WaitType.EXPLICIT).sendKeys(email);
    }

    public void setInputPassword(String password) {
        WaitUtils.waitForVisibility(driver, inputPassword, 10, WaitType.EXPLICIT).sendKeys(password);
    }

    public void clickLogin() {
        WaitUtils.waitForElemenetToBeClickable(driver, loginButton, 10, WaitType.EXPLICIT).click();
    }

    public void inputSignupemail(String email) {
        WaitUtils.waitForVisibility(driver, signupEmail, 10, WaitType.EXPLICIT).sendKeys(email);
    }

    public void inputsignupName(String name) {
        WaitUtils.waitForVisibility(driver, signupName, 10, WaitType.EXPLICIT).sendKeys(name);
    }

    public void clickSignup() {
        WaitUtils.waitForElemenetToBeClickable(driver, buttonSignup, 10, WaitType.EXPLICIT).click();
    }

    public void signUpformvisible() {
        WaitUtils.waitForVisibility(driver, newUsersignup, 10, WaitType.EXPLICIT);
    }

    public HomePage loginAs(String userName, String password) {
        goToLoginPage();
        inputEmail(userName);
        setInputPassword(password);
        clickLogin();
        return new HomePage(driver);
    }

    public HomePage registerNewuser(String email, String name) {
        goToLoginPage();
        signUpformvisible();
        inputsignupName(name);
        inputSignupemail(email);
        clickSignup();
        return new HomePage(driver);
    }

    public Boolean isLoginButtonDisplayed() {
        return WaitUtils.waitForVisibility(driver, loginButton, 10, WaitType.EXPLICIT).isDisplayed();
    }

    public Boolean errorMessageForExistingEmail() {
        return WaitUtils.waitForVisibility(driver, errorMessageForExistingEmail, 10, WaitType.EXPLICIT).isDisplayed();
    }
}
