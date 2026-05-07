package testCases;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.CarbohydrateCalculatorPage;
public class CarbohydrateCalculatorTest extends BaseTest {
	CarbohydrateCalculatorPage page;
	
	 // TC-001: Golden-path happy-path positive test (Imperial units, average male)
    @Test
    public void TC001_calculateCarbsForAverageAdultMale_Imperial() {
        page.selectUSUnits();
        page.selectMale();
        page.enterAge("30");
        page.enterHeightFeet("5");
        page.enterHeightInches("10");
        page.enterWeightPounds("175");
        page.selectActivity("Moderately active");
       
        page.clickCalculate();
        AssertJUnit.assertTrue(page.isResultDisplayed());
        AssertJUnit.assertTrue(page.getResultText().contains("carbohydrate") || page.getResultText().contains("Carbohydrate"));
    }
}
