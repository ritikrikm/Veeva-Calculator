package base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import utils.WaitUtils;

public abstract class BasePage {
	 protected final WebDriver driver;
	    protected final WaitUtils wait;
	    private static final Logger log = LogManager.getLogger(BasePage.class);

	    protected BasePage(WebDriver driver) {
	        this.driver = driver;
	        this.wait   = new WaitUtils(driver);
	        PageFactory.initElements(driver, this);
	        log.debug("Page object initialised: {}", getClass().getSimpleName());
	    }
	    
	    public void navigateTo(String url) {
	        log.info("Navigating to: {}", url);
	        driver.get(url);
	    }
	    public String getPageTitle() { return driver.getTitle(); }	//Not used

	    public String getCurrentUrl() { return driver.getCurrentUrl(); } //NOT USED
	    //here we can create methods like click, JSclick, type, select by value, selecy by txt , getText, get attricute
}
