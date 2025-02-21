package testCases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountPage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.DataProviders;
import utilities.RetryAnalyzer;

public class TC02_Login extends BaseClass {

    private static final Logger logger = LogManager.getLogger(TC02_Login.class); // Initialize logger

    @Test(groups = {"smoke", "regression", "datadriven"}, dataProvider = "LoginData", dataProviderClass = DataProviders.class, retryAnalyzer = RetryAnalyzer.class)
    void testLogin(String email, String pwd) {
        logger.debug("Starting testLogin with email: {}", email); // Debug log

        try {
            HomePage hp = new HomePage(getDriver());
            logger.debug("HomePage object created"); // Debug log

            hp.clickMyAccount();
            logger.debug("Clicked on My Account"); // Debug log

            hp.goToLogin();
            logger.debug("Navigated to Login page"); // Debug log

            LoginPage lp = new LoginPage(getDriver());
            logger.debug("LoginPage object created"); // Debug log

            lp.setEmail(email);
            logger.debug("Entered email: {}", email); // Debug log

            lp.setPwd(pwd);
            logger.debug("Entered password"); // Debug log

            lp.clickLogin();
            logger.debug("Clicked on Login button"); // Debug log

            AccountPage ap = new AccountPage(getDriver());
            logger.debug("AccountPage object created"); // Debug log

            boolean status = ap.getMyAccountConfirmation().isDisplayed();
            logger.debug("My Account confirmation status: {}", status); // Debug log

            if (status) {
                logger.info("Login successful for email: {}", email); // Info log

                ap.clickMyAccountDropDown();
                logger.debug("Clicked on My Account dropdown"); // Debug log

                ap.clickLogout();
                logger.debug("Clicked on Logout"); // Debug log

                // Assertion with try-catch block
                try {
                    Assert.assertTrue(status, "Login failed: My Account confirmation not displayed");
                    logger.info("Assertion passed: My Account confirmation is displayed"); // Info log
                } catch (AssertionError e) {
                    logger.error("Assertion failed: {}", e.getMessage(), e); // Error log
                    throw e; // Re-throw the assertion error for RetryAnalyzer
                }

            } else {
                logger.error("Login failed for email: {}", email); // Error log

                // Assertion with try-catch block
                try {
                    Assert.assertTrue(false, "Login failed: My Account confirmation not displayed");
                } catch (AssertionError e) {
                    logger.error("Assertion failed: {}", e.getMessage(), e); // Error log
                    throw e; // Re-throw the assertion error for RetryAnalyzer
                }
            }

        } catch (Exception e) {
            logger.error("Exception occurred in testLogin: {}", e.getMessage(), e); // Error log
            throw e; // Re-throw the exception for RetryAnalyzer
        }

        logger.debug("testLogin completed for email: {}", email); // Debug log
    }
}