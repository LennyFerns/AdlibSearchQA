package testCases;

import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;
import com.applitools.eyes.RectangleSize;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import testBase.BaseClass;
import com.aventstack.extentreports.Status;

import java.io.IOException;
import java.time.Duration;

public class TC09_UIValidation extends BaseClass {

    @Test(groups = {"visual", "ui","smoke"})
    public void testSearchPageUI() throws IOException {

        WebDriver driver = getDriver();

        // Applitools setup
        Eyes eyes = new Eyes();
        eyes.setApiKey(System.getProperty("APPLITOOLS_API_KEY")); // Optional if already set in BaseClass

        try {
            //Start Eyes test
            eyes.open(driver, "NopCommerce Demo", "TC09 - Search Page UI Test", new RectangleSize(1200, 800));

            //Navigate to Search page
            String appURL = p.getProperty("appURL");
            driver.get(appURL);
            
            Thread.sleep(1000);
            
         // Click the "Advanced Search" checkbox
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement advSearchCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='advs']")));
            if (!advSearchCheckbox.isSelected()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", advSearchCheckbox);
                advSearchCheckbox.click();
            }


            //Visual checkpoint with Applitools
            eyes.checkWindow("Search Page UI");

            // Take local screenshot (for ExtentReport)
            String screenshotPath = captureScreen("SearchPageUI");
            
         //  Specific UI element checks
            eyes.check("Logo", Target.region(By.cssSelector("img[alt='nopCommerce demo store']")));
            eyes.check("Search Input", Target.region(By.id("q")));
            eyes.check("Advanced Search Checkbox", Target.region(By.id("advs")));
            eyes.check("Category Dropdown", Target.region(By.id("cid")));
            eyes.check("Sub category Checkbox", Target.region(By.id("isc")));
            eyes.check("Manufacturer Dropdown", Target.region(By.id("mid")));
            eyes.check("Search In Description Checkbox", Target.region(By.id("sid")));
            eyes.check("Search Button", Target.region(By.cssSelector("button.search-button")));

            // Log it to ExtentReport
            test = extent.createTest("TC09 - Search Page UI Test");
            test.log(Status.INFO, "Search Page visually validated using Applitools");
            test.addScreenCaptureFromPath(screenshotPath);

            //Close Eyes
            eyes.close();

        } catch (Exception e) {
            e.printStackTrace();
            test.log(Status.FAIL, "Exception during visual test: " + e.getMessage());
        } finally {
            eyes.abortIfNotClosed();
        }
    }
}
