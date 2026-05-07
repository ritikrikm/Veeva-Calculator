package listeners;

import base.BaseTest;
import base.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import utils.ScreenshotUtils;

/**
 * TestNG listener that connects test lifecycle events to ExtentReports and Log4j2.
 *
 *
 * <p>On failure it automatically captures a screenshot and embeds it in the
 * HTML report
 */
public class TestListener implements ITestListener {
	private static final Logger log = LogManager.getLogger(TestListener.class);
	
	@Override
    public void onStart(ITestContext context) {
        log.info("=== Suite starting: {} ===", context.getName());
    }
	
	  @Override
	    public void onTestStart(ITestResult result) {
	        String name = result.getMethod().getMethodName();
	        String desc = result.getMethod().getDescription();
	        log.info("TEST START: {}", name);

	        ExtentTest extentTest = BaseTest.extent.createTest(
	                name,
	                (desc != null && !desc.isEmpty()) ? desc : name);
	        BaseTest.setExtentTest(extentTest);
	    }
	  
	  @Override
	    public void onTestSuccess(ITestResult result) {
	        log.info("PASSED: {}", result.getMethod().getMethodName());
	        BaseTest.getExtentTest()
	                .pass(MarkupHelper.createLabel("TEST PASSED", ExtentColor.GREEN));
	    }
	  @Override
	    public void onTestFailure(ITestResult result) {
	        String name = result.getMethod().getMethodName();
	        log.error("FAILED: {} - {}", name,
	                result.getThrowable() != null ? result.getThrowable().getMessage() : "");

	        ExtentTest extentTest = BaseTest.getExtentTest();
	        extentTest.fail(MarkupHelper.createLabel("TEST FAILED", ExtentColor.RED));
	        if (result.getThrowable() != null) {
	            extentTest.fail(result.getThrowable());
	        }

	        // Embed screenshot
	        try {
	            WebDriver driver = DriverManager.getDriver();
	            String b64 = ScreenshotUtils.captureBase64(driver);
	            if (b64 != null) {
	                extentTest.addScreenCaptureFromBase64String(b64,
	                        "Failure screenshot – " + name);
	                log.info("Screenshot embedded in report for: {}", name);
	            }
	        } catch (Exception e) {
	            log.warn("Could not attach screenshot on failure: {}", e.getMessage());
	        }
	    }
	  @Override
	    public void onTestSkipped(ITestResult result) {
	        log.warn("SKIPPED: {}", result.getMethod().getMethodName());
	        BaseTest.getExtentTest()
	                .skip(MarkupHelper.createLabel("TEST SKIPPED", ExtentColor.YELLOW));
	    }
	  @Override
	    public void onFinish(ITestContext context) {
	        log.info("===Suite finished - Passed: {}, Failed: {}, Skipped: {} ===",
	                context.getPassedTests().size(),
	                context.getFailedTests().size(),
	                context.getSkippedTests().size());
	    }
	  @Override
	    public void onTestFailedButWithinSuccessPercentage(ITestResult result) { /* not using this */ }
}
