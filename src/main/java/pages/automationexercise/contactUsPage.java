package pages.automationexercise;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class contactUsPage {

    private WebDriver driver;
    private WebDriverWait wait ;

    public contactUsPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath="//h2[contains(text(),'Get In Touch')]")
    private WebElement contactusPage;

    @FindBy(css="input[data-qa='name']")
    private WebElement name;

    @FindBy(css="input[data-qa='email']")
    private WebElement email;

    @FindBy(css="input[data-qa='subject']")
    private WebElement subject;

    @FindBy(xpath="//div[contains(text(),'Success! Your details have been submitted successfully.')]")
    private WebElement successMessage;

    @FindBy(id="message")
    private WebElement message;

    @FindBy(name ="upload_file")
    private WebElement fileUpload;

    @FindBy(css="input[data-qa='submit-button']")
    private WebElement submitButton;

   @FindBy(xpath="//a[contains(@class,'btn-success')]")
    private WebElement clickHome;

    public void verifyContactUsPage(){
        contactusPage.isDisplayed();
    }

    public void inputName(String inputname){
        wait.until(ExpectedConditions.visibilityOf(name)).sendKeys(inputname);
    }

    public void inputEmail(String inputemail){
        wait.until(ExpectedConditions.visibilityOf(email)).sendKeys(inputemail);
    }

    public void inputMessage(String inputmessage){
        wait.until(ExpectedConditions.visibilityOf(message)).sendKeys(inputmessage);
    }

    public void uploadFile(String filePath){
        wait.until(ExpectedConditions.visibilityOf(fileUpload)).sendKeys(filePath);
    }

    public void clickSubmitButton(){
        submitButton.click();
    }

    public void fillContactUsForm(String inputName, String inputEmail, String inputsubject,String inputmessage, String filePath){
        inputName(inputName);
        inputEmail(inputEmail);
        inputSubject(inputsubject);
        inputMessage(inputmessage);
        uploadFile(filePath);
        clickSubmitButton();
    }

    public void acceptAllertMessage(){
        driver.switchTo().alert().accept();
    }

    public Boolean successMessage(){
        wait.until(ExpectedConditions.visibilityOf(successMessage));
        return successMessage.isDisplayed();
    }

    public HomePage clickHomePage(){
        clickHome.click();
        return new HomePage(driver);
    }

    public void inputSubject(String inputsubject){
        subject.sendKeys(inputsubject);
    }



}
