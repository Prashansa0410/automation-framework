package utils;

import Base.ConfigReader;
import enums.WaitType;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

public class WaitUtils {
    private static final int DEFAULT_TIMEOUT=20;
    private static final int DEFAULT_POLLING=2;

   WebDriverWait wait;

    /**
     * Generic wait method supporting both explicit and fluent waits
     *
     * @param driver    WebDriver instance
     * @param condition Supplier that defines the wait condition
     * @param waitType  Enum that defines which wait to use
     * @param <T>       Return type of condition (usually WebElement or Boolean)
     * @return          The result of the supplier once condition is met
     */
    public static <T> T waitUntil(WebDriver driver, Supplier<T> condition, int timeOut,WaitType waitType){
        switch(waitType){
            case EXPLICIT:
                WebDriverWait explicitWait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
                explicitWait.until(d -> condition.get());

            case FLUENT:
                Wait<WebDriver> fluentWait = new FluentWait<>(driver)
                        .withTimeout(Duration.ofSeconds(DEFAULT_TIMEOUT))
                        .pollingEvery(Duration.ofSeconds(DEFAULT_POLLING))
                        .ignoring(NoSuchElementException.class)
                        .ignoring(StaleElementReferenceException.class);
                return fluentWait.until(d ->condition.get());

            default:
                throw new IllegalArgumentException("Invalid wait type provided.");
        }

        }
 //1.Wait for visibilty of element
    public static WebElement waitForVisibility(WebDriver driver, WebElement element,int timeOut,WaitType waitType ){
        return waitUntil(driver,() ->{
            if(element.isDisplayed()){
                return element;
            }
            else{
                throw new NoSuchElementException("Element not visible");
            }
        },timeOut,waitType );
    }

    //2.Wait for element to be clickable
    public static WebElement waitForElemenetToBeClickable(WebDriver driver, WebElement element,int timeout, WaitType type){
        return waitUntil(driver,()-> {
            if (element.isDisplayed() && element.isEnabled()) {
                return element;
            }
            return null;
        } ,timeout,type );
    }

    //3.wait for title conatins
    public static boolean waitForTitleContains(WebDriver driver,String title, int timeout, WaitType type){
        return waitUntil(driver,()-> driver.getTitle().contains(title),timeout,type);
    }

    //4.wait for element to be selected
    public static WebElement waitForElementSelected(WebDriver driver, WebElement element,int timeout, WaitType type){
        return waitUntil(driver,()->{
            if(element.isSelected()) {
                return element;
            }
            else {
                throw new NoSuchElementException("Element not selected");
            }
            },timeout,type);
    }

    //5.Wait for element to change text
    public static WebElement waitForElementTextToChange(WebDriver driver, WebElement element,String expected, int timeout, WaitType type){
        return waitUntil(driver,()->{
            if(element.getText().equals(expected)){
                return element;
            }
            else{
                throw new NoSuchElementException("Element text didnt convert");
            }
        },timeout,type);
    }

    //6. Wait for invisibilty of element
    public static void waitForInvisibilityOfElement(WebDriver driver, WebElement element, int timeout, WaitType type){
        waitUntil(driver,()->{
            if(!element.isDisplayed()){
                return true;
            }
            else{
                throw new NoSuchElementException("Element is still visible");
            }
        },timeout,type);
    }

    //7 Wait for Element attribute to contain value
    public static WebElement waitForElementAttributeToContainValue(WebDriver driver, WebElement element ,String attribute,String expectedValue, int timeout, WaitType type){
        return waitUntil(driver, ()->{
            if(element.getDomAttribute(attribute).contains(expectedValue)){
                return element;
            }
            else{
                 throw new NoSuchElementException("Element attribute did not contain the expected value");
            }
                }
        ,timeout,type);
    }

    //8.Wait for Element to be Present (Using By Locator):
    public static WebElement waitForElementPresence(WebDriver driver, By locator, int timeout, WaitType wait){
        return waitUntil(driver,()->{
            WebElement element = driver.findElement(locator);
            if(element.isDisplayed()){
                return element;
            }
            else{
                throw new NoSuchElementException("Element is not present");
            }
        },timeout,wait);
    }

    //9.Wait for JavaScript to be Executed:
    public static void waitForJavaScriptToBeExceuted(WebDriver driver, String script, int timeout, WaitType type){
        waitUntil(driver,()->{
            return ((JavascriptExecutor)driver).executeScript(script);
        },timeout,type);

    }

    //10.Wait for Element to Have Text:
    public static WebElement waitForElementToHaveText(WebDriver driver, WebElement element, String text, int timeout, WaitType type){
        return waitUntil(driver,() -> {
            if(element.getText().equals(text)){
                return element;
            }
            else{
                throw new NoSuchElementException("Element text did not match the expected value ");
            }
        },timeout,type);
    }


}
