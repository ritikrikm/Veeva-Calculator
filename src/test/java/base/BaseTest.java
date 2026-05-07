package base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import listeners.TestListener;
import utils.ConfigReader;
import utils.ExtentReportManager;

/**
 * Base class for all test classes.
 *
 * <p>Handles WebDriver lifecycle (init before each test, quit after), ExtentReports
 * wiring, and provides accessor methods that sub-classes use.
 *
 */
@Listeners(TestListener.class)
public abstract class BaseTest {
	private static final Logger log = LogManager.getLogger(BaseTest.class);
	
	 public static final ExtentReports extent = ExtentReportManager.getInstance();

	 private static final ThreadLocal<ExtentTest> extentTestThreadLocal = new ThreadLocal<>();
	 @AfterSuite(alwaysRun = true)
	    public void tearDownSuite(ITestContext ctx) {
	        log.info("Suite complete – flushing ExtentReports. " +
	                 "Passed={}, Failed={}, Skipped={}",
	                 ctx.getPassedTests().size(),
	                 ctx.getFailedTests().size(),
	                 ctx.getSkippedTests().size());
	        extent.flush();
	    }
	 
	 /**
	     * Initialises WebDriver and opens the calculator URL before every test method.
	     * The browser is read from {config.properties} (key: {browser}).
	     */
	 @BeforeMethod(alwaysRun = true)
	    public void setUp() {
	        String browser = ConfigReader.get("browser", "chrome");
	        log.info("setUp : browser={}", browser);
	        DriverManager.initDriver(browser);
	    }
	  @AfterMethod(alwaysRun = true)
	    public void tearDown() {
	        log.info("tearDown : quitting driver");
	        DriverManager.quitDriver();
	    }
	  
	  protected WebDriver getDriver() {
	        return DriverManager.getDriver();
	    }
	  public static ExtentTest getExtentTest() {
	        return extentTestThreadLocal.get();
	    }
	  public static void setExtentTest(ExtentTest test) {
	        extentTestThreadLocal.set(test);
	    }

}
