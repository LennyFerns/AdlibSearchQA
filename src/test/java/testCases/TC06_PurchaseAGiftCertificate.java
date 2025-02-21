package testCases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.GiftCertificatePage;
import pageObjects.HomePage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;

public class TC06_PurchaseAGiftCertificate extends BaseClass {

    private static final Logger logger = LogManager.getLogger(TC06_PurchaseAGiftCertificate.class);

    @Test(groups = {"regression"}, retryAnalyzer = RetryAnalyzer.class)
    void testPurchaseAGiftCertificate() {
        try {
            logger.debug("Starting test: testPurchaseAGiftCertificate");

            HomePage hp = new HomePage(getDriver());
            logger.debug("Clicking on Gift Certificate link");
            hp.clickGiftCertificateLink();

            GiftCertificatePage gcp = new GiftCertificatePage(getDriver());
            logger.debug("Entering recipient details");
            gcp.enterRecipientDetails("John", "john@gmail.com");

            logger.debug("Entering sender details");
            gcp.enterSenderDetails("Sid", "sid@cloudberry.services");

            logger.debug("Selecting gift theme");
            gcp.selectGiftTheme();

            logger.debug("Agreeing to terms and conditions");
            gcp.agreeToTerms();

            logger.debug("Clicking continue button");
            gcp.clickContinue();

            boolean isMessageDisplayed = gcp.isPurchaseSuccessMessageDisplayed();
            logger.debug("Purchase success message displayed: " + isMessageDisplayed);

            // Assertion with try-catch block
            try {
                Assert.assertTrue(isMessageDisplayed, "Gift Certificate purchase failed!");
                logger.info("Gift Certificate purchased successfully");
            } catch (AssertionError e) {
                logger.error("Assertion failed: Purchase success message not displayed", e);
                throw e;
            }
        } catch (Exception e) {
            logger.error("An error occurred during test execution", e);
            throw e;
        }
    }
}
