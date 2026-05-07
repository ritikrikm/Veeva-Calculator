package pages;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import base.BasePage;
import utils.ConfigReader;

public class CarbohydrateCalculatorPage extends BasePage {

	public CarbohydrateCalculatorPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

    public void selectUSUnits() { wait.waitForClickable(By.xpath("//a[normalize-space()='US Units']")).click(); }
    public void selectMetricUnits() { wait.waitForClickable(By.xpath("//a[normalize-space()='Metric Units']")).click();}

    public void enterAge(String age) { 
    	  WebElement ageInput = wait.waitForVisible(By.id("cage"));
          ageInput.clear();
          ageInput.sendKeys(age);
    }
    public void selectMale() { wait.waitForClickable(By.xpath("//label[@for='csex1']")).click(); }
    public void selectFemale() { wait.waitForClickable(By.xpath("//label[@for='csex2']")).click(); }
    public void enterHeightFeet(String feet) { 
        WebElement element = wait.waitForVisible(By.id("cheightfeet"));
        element.clear();
        element.sendKeys(feet);
    }
    public void enterHeightInches(String inches) { 
        WebElement element = wait.waitForVisible(By.id("cheightinch"));
        element.clear();
        element.sendKeys(inches);
    }
    public void enterHeightCm(String cm) { 
        WebElement element = wait.waitForVisible(By.id("cheightmeter"));
        element.clear();
        element.sendKeys(cm);
    }
    public void enterWeightPounds(String lbs) { 
        WebElement element = wait.waitForVisible(By.id("cpound"));
        element.clear();
        element.sendKeys(lbs);
    }
    public void enterWeightKg(String kg) {
        WebElement element = wait.waitForVisible(By.id("ckg"));
        element.clear();
        element.sendKeys(kg);
    }
    public void selectActivity(String value) { 
        WebElement dropdown = wait.waitForVisible(By.id("cactivity"));
        new Select(dropdown).selectByVisibleText(value);
    }
    public void clickCalculate() { 
    	wait.waitForClickable(By.xpath("//input[@type='submit' and contains(@value,'Calculate')]")).click();
    }
    public boolean isResultDisplayed() {
        try {
            wait.waitForVisible(By.xpath("//h2[normalize-space()='Result']"), 5);
            return driver.findElement(By.xpath("//h2[normalize-space()='Result']")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    public boolean isErrorDisplayed() {
        try {
            wait.waitForVisible(By.cssSelector("font[color='red']"), 3);
            return driver.findElement(By.cssSelector("font[color='red']")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    public int getWeightMaintenanceCarbsGrams() {
        WebElement cell = wait.waitForVisible(By.xpath("//table//tr[td='Weight Maintenance']/td[4]"));
        String cellText = cell.getText();
        Matcher m = Pattern.compile("(\\d+)\\s*grams").matcher(cellText);
        return m.find() ? Integer.parseInt(m.group(1)) : 0;
    }
    public boolean isResultSectionPresentAndDisplayed() {
        try {
            wait.waitForVisible(By.xpath("//h2[normalize-space()='Result']"), 5);
            return driver.findElement(By.xpath("//h2[normalize-space()='Result']")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    public String getResultText() {
        return wait.waitForVisible(By.xpath("//p[@class='bigtext']")).getText();
    }
    public String getErrorText() {
        return wait.waitForVisible(By.cssSelector("font[color='red']")).getText();

    }
}
