package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import base.BasePage;

public class CarbohydrateCalculatorPage extends BasePage {

	protected CarbohydrateCalculatorPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
    @FindBy(xpath="//a[normalize-space()='US Units']")
    public WebElement tabUSUnits;

    @FindBy(xpath="//a[normalize-space()='Metric Units']")
    public WebElement tabMetricUnits;

    @FindBy(id="cage")
    public WebElement ageInput;

    @FindBy(css="input[name='csex'][value='1']")
    public WebElement genderMaleRadio;

    @FindBy(css="input[name='csex'][value='2']")
    public WebElement genderFemaleRadio;

    @FindBy(id="cheightfeet")
    public WebElement heightFeetInput;

    @FindBy(id="cheightinch")
    public WebElement heightInchesInput;

    @FindBy(id="cheightcm")
    public WebElement heightCmInput;

    @FindBy(id="cweightpound")
    public WebElement weightPoundsInput;

    @FindBy(id="cweightkg")
    public WebElement weightKgInput;

    @FindBy(id="cactivity")
    public WebElement activityDropdown;

    @FindBy(xpath="//input[@type='submit' and contains(@value,'Calculate')] | //button[contains(text(),'Calculate')]")
    public WebElement calculateButton;
  
    @FindBy(xpath = "//h2[normalize-space()='Result']")
    public WebElement resultHeading;

    @FindBy(xpath = "//p[@class='bigtext']")
    public WebElement result;

    @FindBy(xpath="//*[contains(@class,'error') or contains(@class,'alert')]")
    public WebElement errorMessage;


    public void selectUSUnits() { tabUSUnits.click(); }
    public void selectMetricUnits() { tabMetricUnits.click(); }

    public void enterAge(String age) { ageInput.clear(); ageInput.sendKeys(age); }
    public void selectMale() { genderMaleRadio.click(); }
    public void selectFemale() { genderFemaleRadio.click(); }
    public void enterHeightFeet(String feet) { heightFeetInput.clear(); heightFeetInput.sendKeys(feet); }
    public void enterHeightInches(String inches) { heightInchesInput.clear(); heightInchesInput.sendKeys(inches); }
    public void enterHeightCm(String cm) { heightCmInput.clear(); heightCmInput.sendKeys(cm); }
    public void enterWeightPounds(String lbs) { weightPoundsInput.clear(); weightPoundsInput.sendKeys(lbs); }
    public void enterWeightKg(String kg) { weightKgInput.clear(); weightKgInput.sendKeys(kg); }
    public void selectActivity(String value) { 
        Select sel = new Select(activityDropdown);
        sel.selectByVisibleText(value);
    }
    public void clickCalculate() { calculateButton.click(); }
    public boolean isResultDisplayed() { return resultHeading.isDisplayed(); }
    public boolean isErrorDisplayed() { return errorMessage.isDisplayed(); }
    public String getResultText() { return result.getText(); }
    public String getErrorText() { return errorMessage.getText(); }
}
