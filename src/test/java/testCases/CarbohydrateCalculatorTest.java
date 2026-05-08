package testCases;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.testng.Assert;

import base.BaseTest;
import pages.CarbohydrateCalculatorPage;
import utils.ConfigReader;
import utils.NutritionCalculator;
public class CarbohydrateCalculatorTest extends BaseTest {
	CarbohydrateCalculatorPage page;
	@BeforeMethod(alwaysRun = true)
	public void setupPage() {
	    page = new CarbohydrateCalculatorPage(getDriver());
	    page.navigateTo(ConfigReader.get("app.url"));
	}
	// TC-001: Golden-path happy-path positive test (Imperial units, average male)
    @Test(groups = {"functional", "positive", "happy-path"})
    public void TC001_calculateCarbsForAverageAdultMale_Imperial() {
    	
        page.selectUSUnits();
        page.selectMale();
        page.enterAge("30");
        page.enterHeightFeet("5");
        page.enterHeightInches("10");
        page.enterWeightPounds("175");
        page.selectActivity("Moderate: exercise 4-5 times/week");
       
        page.clickCalculate();
        AssertJUnit.assertTrue(page.isResultDisplayed());
        AssertJUnit.assertTrue(page.getResultText().contains("carbohydrate") || page.getResultText().contains("Carbohydrate"));
    }
 // TC-008: Weight field empty (should show validation error)
    @Test(groups = {"functional", "negative", "input-validation"})
    public void TC008_emptyWeightField_ShouldShowValidationError() {
        page.selectUSUnits();
        page.selectMale();
        page.enterAge("30");
        page.enterHeightFeet("5");
        page.enterHeightInches("10");
        page.enterWeightPounds("");   // Leave weight empty
        page.selectActivity("Moderate: exercise 4-5 times/week");
        page.clickCalculate();
        Assert.assertTrue(page.isErrorDisplayed());
    }
    // TC-018: Special characters in Age field (should reject/validate)
    @Test(groups = {"input-validation", "negative"})
    public void TC018_specialCharactersInAgeField_ShouldShowValidationError() {
        page.selectUSUnits();
        page.selectMale();
        page.enterAge("!@#$");
        page.enterHeightFeet("5");
        page.enterHeightInches("10");
        page.enterWeightPounds("175");
        page.selectActivity("Moderate: exercise 4-5 times/week");
        page.clickCalculate();
        Assert.assertTrue(page.isErrorDisplayed());
    }
    // TC-023: 0 Pound Weight, valid inputs
    @Test(groups = {"boundary", "input-validation"})
    public void TC023_minValidHeight_Imperial_ShouldYieldResult() {
        page.selectUSUnits();
        page.selectMale();
        page.enterAge("30");
        page.enterHeightFeet("4");
        page.enterHeightInches("0");
        page.enterWeightPounds("0");
        page.selectActivity("Moderate: exercise 4-5 times/week");
        page.clickCalculate();
        Assert.assertFalse(page.isResultDisplayed());
    }
    
   // TC-028: Unit conversion - same data in US and metric (~5'10, 175 lbs = 178cm, 79.4kg)
    @Test(groups = {"unit-conversion", "consistency"})
    public void TC028_equivalentImperialAndMetricInputs_ResultsWithinTolerance() throws IOException {
    	        // Imperial
    page.selectUSUnits();
    page.selectMale();
    page.enterAge("30");
    page.enterHeightFeet("5");
    page.enterHeightInches("10");
    page.enterWeightPounds("175");
    page.selectActivity("Moderate: exercise 4-5 times/week");
    page.clickCalculate();
    String usResult = page.getResultText();	        
    
    getDriver().switchTo().newWindow(WindowType.TAB);
    CarbohydrateCalculatorPage pageMetric = new CarbohydrateCalculatorPage(getDriver());
    pageMetric.navigateTo(ConfigReader.get("app.url"));
    pageMetric.selectMetricUnits();
    pageMetric.selectMale();
    pageMetric.enterAge("30");
    pageMetric.enterHeightCm("178");
    pageMetric.enterWeightKg("79.4");
    pageMetric.selectActivity("Moderate: exercise 4-5 times/week");
    pageMetric.clickCalculate();
    String metricResult = pageMetric.getResultText();

    // Extract grams from both results and assert within +-5
    int usGrams = extractGrams(usResult);
    int metricGrams = extractGrams(metricResult);
    Assert.assertTrue(Math.abs(usGrams - metricGrams) <= 5,
            "US and Metric carbs are within (+-)5g: US=" + usGrams + ", Metric=" + metricGrams);	        
    	        
    	    }
    	
    // TC-029: Calculation accuracy (manual formula verification, 30M, 5'10", 175lb, sedentary)
    @Test(groups = {"calculation-accuracy", "formula"})
    public void TC029_MifflinStJeor_BMRFormulaVerification() {
        page.selectUSUnits();
        page.selectMale();
        page.enterAge("30");
        page.enterHeightFeet("5");
        page.enterHeightInches("10");
        page.enterWeightPounds("175");
        page.selectActivity("Sedentary: little or no exercise");
        page.clickCalculate();

        int actualGrams = page.getWeightMaintenanceCarbsGrams();

        int expectedGrams = NutritionCalculator.calculateExpectedCarbs(
            true,                  // isMale
            30,                    // age
            175,                   // lbs
            5,                     // feet
            10,                    // inches
            1.2,                   // sedentary factor
            0.55                   // 55% carbs
        );
        int TOLERANCE = 20;
        Assert.assertTrue(
            Math.abs(actualGrams - expectedGrams) <= TOLERANCE, // +-20g for rounding
            "Carb grams off: actual=" + actualGrams + ", expected=" + expectedGrams
        );
    }
    // TC-033: Page loads within 3 seconds (performance)
    @Test(groups = {"performance"})
    public void TC033_pageLoadsInUnder3Seconds() {
        long start = System.currentTimeMillis();
        getDriver().get(ConfigReader.get("app.url"));
        long duration = System.currentTimeMillis() - start;
        Assert.assertTrue(duration < 3000, "Page load <= 3s, was: " + duration + "ms");
    }

    // TC-036: Accessibility - All fields have <label>
    @Test(groups = {"accessibility"})
    public void TC036_allFieldsHaveAssociatedLabels() {
        Assert.assertTrue(hasLabelFor("cage"),        "Missing label for: Age (cage)");
        Assert.assertTrue(hasLabelFor("cheightfeet"), "Missing label for: Height - Feet (cheightfeet)");
        Assert.assertTrue(hasLabelFor("cheightinch"), "Missing label for: Height - Inches (cheightinch)");
        Assert.assertTrue(hasLabelFor("cpound"),      "Missing label for: Weight - Pounds (cpound)");
        Assert.assertTrue(hasLabelFor("cactivity"),   "Missing label for: Activity (cactivity)");
    }

    // TC-050: Print/share result - print preview looks clean
    @Test(groups = {"usability", "manual"})
    public void TC050_printResultSectionIsClean() {
        page.selectUSUnits();
        page.selectMale();
        page.enterAge("30");
        page.enterHeightFeet("5");
        page.enterHeightInches("10");
        page.enterWeightPounds("175");
        page.selectActivity("Moderate: exercise 4-5 times/week");
        page.clickCalculate();
        // This is a manual step: to check if print preview shows clean result area (no code assertion)
        System.out.println("Manual Check: Press Ctrl+P and ensure result area prints neatly.");
        Assert.assertTrue(page.isResultDisplayed());
    }

    // TC-048: Fresh page load : result not visible before any calculation
    @Test(groups = {"ui-ux"})
    public void TC048_resultNotShownBeforeFirstCalculation() {
        Assert.assertFalse(page.isResultSectionPresentAndDisplayed(), "Result section should not display on fresh page load");
    }
    
    private int extractGrams(String resultText) {
        Matcher m = Pattern.compile("(\\d{2,4})\\s*g").matcher(resultText);
        return m.find() ? Integer.parseInt(m.group(1)) : 0;
    }
    private boolean hasLabelFor(String fieldId) {
        return getDriver().findElements(
            By.xpath("//label[@for='" + fieldId + "']")
        ).size() > 0;
    }	    


}
