package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;



public class WaitUtils {

	 private static final Logger log = LogManager.getLogger(WaitUtils.class);

	    private final WebDriver driver;
	    private final int defaultTimeout;

	    public WaitUtils(WebDriver driver) {
	        this.driver = driver;
	        this.defaultTimeout = Integer.parseInt(
	                ConfigReader.get("explicit.wait.sec", "15"));
	    }
	    
	    public WebElement waitForVisible(By locator) {
	        log.debug("Waiting for visible: {}", locator);
	        return makeWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
	    }

	    public WebElement waitForVisible(WebElement element) {
	        log.debug("Waiting for element to become visible");
	        return makeWait().until(ExpectedConditions.visibilityOf(element));
	    }

	    public WebElement waitForVisible(By locator, int timeoutSec) {
	        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSec))
	                .until(ExpectedConditions.visibilityOfElementLocated(locator));
	    }
	    
	    public WebElement waitForClickable(By locator) {
	        log.debug("Waiting for clickable: {}", locator);
	        return makeWait().until(ExpectedConditions.elementToBeClickable(locator));
	    }

	    public WebElement waitForClickable(WebElement element) {
	        return makeWait().until(ExpectedConditions.elementToBeClickable(element));
	    }
	    
	    public void waitForTextPresent(By locator, String text) {
	        log.debug("Waiting for text '{}' in: {}", text, locator);
	        makeWait().until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
	    }

	    public void waitForPresence(By locator) {
	        log.debug("Waiting for presence in DOM: {}", locator);
	        makeWait().until(ExpectedConditions.presenceOfElementLocated(locator));
	    }
	    
	    public void waitForPageLoad() {
	    	makeWait().until(wd ->
	                "complete".equals(((org.openqa.selenium.JavascriptExecutor) wd)
	                        .executeScript("return document.readyState")));
	    }
	    private WebDriverWait makeWait() {
	        return new WebDriverWait(driver, Duration.ofSeconds(defaultTimeout));
	    }
}
