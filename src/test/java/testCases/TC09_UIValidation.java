package testCases;

import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.RectangleSize;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import testBase.BaseClass;
import com.aventstack.extentreports.Status;

import java.io.IOException;

public class TC09_UIValidation extends BaseClass {

    @Test(groups = {"visual", "ui"})
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

            //Visual checkpoint with Applitools
            eyes.checkWindow("Search Page UI");

            // Take local screenshot (for ExtentReport)
            String screenshotPath = captureScreen("SearchPageUI");

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
