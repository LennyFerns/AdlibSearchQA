package testCases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;

public class TC01_LaunchApplication extends BaseClass {

    private static final Logger logger = LogManager.getLogger(TC01_LaunchApplication.class); // Initialize logger

    @Test(groups = {"smoke", "regression"}, retryAnalyzer = RetryAnalyzer.class)
    public void testLaunchApplication() {
        logger.debug("Starting testLaunchApplication test"); // Debug log

        try {
            HomePage hp = new HomePage(getDriver());
            logger.debug("HomePage object created"); // Debug log

            String confirmation = hp.confirmHomepage();
            logger.debug("Homepage confirmation text retrieved: {}", confirmation); // Debug log

            // Assertion with try-catch block
            try {
                Assert.assertEquals(confirmation, "nopCommerce demo store");
                logger.info("Assertion passed: Homepage confirmation text is 'nopCommerce demo store'"); // Info log
            } catch (AssertionError e) {
                logger.error("Assertion failed: Expected 'nopCommerce demo store' but found '{}'", confirmation, e); // Error log
                throw e; // Re-throw the assertion error for RetryAnalyzer
            }

        } catch (Exception e) {
            logger.error("Exception occurred in testLaunchApplication: {}", e.getMessage(), e); // Error log
            throw e; // Re-throw the exception for RetryAnalyzer
        }

        logger.debug("testLaunchApplication test completed successfully"); // Debug log
    }
}