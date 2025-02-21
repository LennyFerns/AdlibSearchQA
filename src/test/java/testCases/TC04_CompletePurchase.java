package testCases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AddToCartPage;
import pageObjects.CategoryPage;
import pageObjects.CheckoutPage;
import pageObjects.ConfirmationPage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;

public class TC04_CompletePurchase extends BaseClass {

    private static final Logger logger = LogManager.getLogger(TC04_CompletePurchase.class);

    @Test(groups = {"smoke", "regression"}, retryAnalyzer = RetryAnalyzer.class)
    public void testCompletePurchase() {
        try {
            logger.debug("Starting test: testCompletePurchase");

            // Navigate to product and add to cart
            HomePage hp = new HomePage(getDriver());
            logger.debug("Navigating to Laptops and Notebooks category");
            hp.navigateToLaptopsAndNotebooks();

            CategoryPage cp = new CategoryPage(getDriver());
            logger.debug("Selecting a product");
            cp.selectProduct();

            AddToCartPage addToCartPage = new AddToCartPage(getDriver());
            logger.debug("Setting delivery date and adding product to cart");
            addToCartPage.setDeliveryDate();
            addToCartPage.clickAddToCart();

            // Proceed to checkout
            logger.debug("Proceeding to checkout");
            addToCartPage.goToCheckout();

            // Login to complete purchase
            LoginPage lp = new LoginPage(getDriver());
            logger.debug("Entering login credentials");
            lp.setEmail("sid@cloudberry.services");
            lp.setPwd("Test123");
            lp.clickLogin();

            CheckoutPage checkoutPage = new CheckoutPage(getDriver());
            logger.debug("Completing checkout process");
            checkoutPage.completeCheckout();

            // Validate order confirmation
            ConfirmationPage confp = new ConfirmationPage(getDriver());
            logger.debug("Verifying order confirmation");
            try {
                Assert.assertTrue(confp.verifyOrderConfirmation(), "Order was not placed successfully!");
                logger.info("Order placed successfully");
            } catch (AssertionError e) {
                logger.error("Order confirmation failed", e);
                throw e;
            }
        } catch (Exception e) {
            logger.error("An error occurred during test execution", e);
            throw e;
        }
    }
}
