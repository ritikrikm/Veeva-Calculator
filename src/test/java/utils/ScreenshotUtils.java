package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtils {
	 private static final Logger log = LogManager.getLogger(ScreenshotUtils.class);
	    private static final String DIR = "test-output/screenshots/";
	    
	    public static String capture(WebDriver driver, String testName) {
	        try {
	            Files.createDirectories(Paths.get(DIR));
	            String ts   = LocalDateTime.now()
	                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"));
	            String path = DIR + testName + "_" + ts + ".png";
	            File src  = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	            File dest = new File(path);
	            Files.copy(src.toPath(), dest.toPath());
	            log.info("Screenshot saved: {}", dest.getAbsolutePath());
	            return dest.getAbsolutePath();
	        } catch (IOException e) {
	            log.error("Failed to save screenshot for '{}'", testName, e);
	            return null;
	        }
	    }
	    
	    public static String captureBase64(WebDriver driver) {
	        try {
	            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
	        } catch (Exception e) {
	            log.error("Failed to capture Base64 screenshot", e);
	            return null;
	        }
	    }
}
