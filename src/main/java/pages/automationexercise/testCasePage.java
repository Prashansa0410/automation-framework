package pages.automationexercise;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class testCasePage {

    private WebDriver driver;
    private WebDriverWait wait;

    public testCasePage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath="//b[contains(text(),'Test Cases')]")
    private WebElement testcasesheading;

    public Boolean testCaseHeadingDisplayed(){
        return testcasesheading.isDisplayed();
    }
}
