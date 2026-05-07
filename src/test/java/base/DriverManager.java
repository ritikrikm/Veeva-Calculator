package base;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverManager {
	  private static final Logger log = LogManager.getLogger(DriverManager.class);
	    private static final ThreadLocal<WebDriver> DRIVER_THREAD_LOCAL = new ThreadLocal<>();
	    
	    private DriverManager() { /* Forcing singleton pattern */ }

	    public static void initDriver(String browser) {
	        log.info("Initialising WebDriver for browser: '{}'", browser);
	        WebDriver driver = createDriver(browser.toLowerCase().trim());
	        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0)); // rely on explicit waits
	        driver.manage().window().maximize();
	        DRIVER_THREAD_LOCAL.set(driver);
	        log.info("WebDriver ready.");
	    }
	    public static WebDriver getDriver() {
	        WebDriver driver = DRIVER_THREAD_LOCAL.get();
	        if (driver == null) {
	            throw new IllegalStateException(
	                    "WebDriver is not initialised for this thread. " +
	                    "Call DriverManager.initDriver(browser) before using the driver.");
	        }
	        return driver;
	    }
	    public static void quitDriver() {
	        WebDriver driver = DRIVER_THREAD_LOCAL.get();
	        if (driver != null) {
	            log.info("Quitting WebDriver.");
	            try {
	                driver.quit();
	            } catch (Exception e) {
	                log.warn("Exception while quitting driver (ignored): {}", e.getMessage());
	            } finally {
	                DRIVER_THREAD_LOCAL.remove();
	            }
	        }
	    }
	    private static WebDriver createDriver(String browser) {
	        switch (browser) {
	            case "firefox": {
	                WebDriverManager.firefoxdriver().setup();
	                return new FirefoxDriver();
	            }
	            case "firefox-headless": {
	                WebDriverManager.firefoxdriver().setup();
	                FirefoxOptions opts = new FirefoxOptions();
	                opts.addArguments("-headless");
	                return new FirefoxDriver(opts);
	            }
	            case "edge": {
	                WebDriverManager.edgedriver().setup();
	                return new EdgeDriver();
	            }
	            case "chrome-headless": {
	                WebDriverManager.chromedriver().setup();
	                ChromeOptions opts = new ChromeOptions();
	                opts.addArguments(
	                        "--headless=new",
	                        "--no-sandbox",
	                        "--disable-dev-shm-usage",
	                        "--disable-gpu",
	                        "--window-size=1920,1080");
	                return new ChromeDriver(opts);
	            }
	            case "chrome":
	            default: {
	                WebDriverManager.chromedriver().setup();
	                ChromeOptions opts = new ChromeOptions();
	                opts.addArguments(
	                        "--no-sandbox",
	                        "--disable-dev-shm-usage",
	                        "--window-size=1920,1080");
	                return new ChromeDriver(opts);
	            }
	        }
	    }
}
