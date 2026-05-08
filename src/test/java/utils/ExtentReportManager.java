package utils;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {
	 private static final Logger log = LogManager.getLogger(ExtentReportManager.class);
	    private static ExtentReports instance;
	    private static String lastReportPath;
	    
	    private ExtentReportManager() {/* Singleton pattern always */ }
	    public static synchronized ExtentReports getInstance() {
	        if (instance == null) {
	            String ts = LocalDateTime.now()
	                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
	            String path = "test-output/ExtentReport_" + ts + ".html";
	            lastReportPath = path;
	            ExtentSparkReporter spark = new ExtentSparkReporter(path);
	            spark.config().setDocumentTitle("Carbohydrate Calculator Test Report");
	            spark.config().setReportName("calculator.net – Carbohydrate Calculator");
	            spark.config().setTheme(Theme.STANDARD);
	            spark.config().setEncoding("UTF-8");

	            instance = new ExtentReports();
	            instance.attachReporter(spark);
	            instance.setSystemInfo("URL",
	                    ConfigReader.get("app.url",
	                            "https://www.calculator.net/carbohydrate-calculator.html"));
	            instance.setSystemInfo("Browser", ConfigReader.get("browser", "chrome"));
	            instance.setSystemInfo("Environment", ConfigReader.get("env", "production"));
	            instance.setSystemInfo("Author", "QA Team (Ritik)");

	            log.info("ExtentReports initialised → {}", path);
	        }
	        return instance;
	    }
	    public static void openLatestReport(String reportPath) {
	        try {
	            File htmlFile = new File(reportPath);
	            if (Desktop.isDesktopSupported() && htmlFile.exists()) {
	                Desktop.getDesktop().browse(htmlFile.toURI());
	            }
	        } catch (IOException e) {
	            log.warn("Failed to open extent report in browser: {}", e.getMessage());
	        }
	    }
	    public static String getLastReportPath() {
	        return lastReportPath;
	    }
}
