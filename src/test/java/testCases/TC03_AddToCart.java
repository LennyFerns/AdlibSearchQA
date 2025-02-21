package testCases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AddToCartPage;
import pageObjects.CategoryPage;
import pageObjects.HomePage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;

public class TC03_AddToCart extends BaseClass {

    private static final Logger logger = LogManager.getLogger(TC03_AddToCart.class); // Initialize logger

    @Test(groups = {"smoke", "regression"}, retryAnalyzer = RetryAnalyzer.class)
    public void testAddAnItemToWishList() {
        logger.debug("Starting testAddAnItemToWishList"); // Debug log

        try {
            // Navigate and select product
            HomePage hp = new HomePage(getDriver());
            logger.debug("HomePage object created"); // Debug log

            hp.navigateToLaptopsAndNotebooks();
            logger.debug("Navigated to Laptops and Notebooks category"); // Debug log

            CategoryPage cp = new CategoryPage(getDriver());
            logger.debug("CategoryPage object created"); // Debug log

            cp.selectProduct();
            logger.debug("Product selected"); // Debug log

            AddToCartPage atcp = new AddToCartPage(getDriver());
            logger.debug("AddToCartPage object created"); // Debug log

            // Set delivery date and add to cart
            atcp.setDeliveryDate();
            logger.debug("Delivery date set"); // Debug log

            atcp.clickAddToCart();
            logger.debug("Clicked on Add to Cart button"); // Debug log

            // Validate success message
            boolean successMessageDisplayed = atcp.verifySuccessMessage();
            logger.debug("Success message displayed: {}", successMessageDisplayed); // Debug log

            // Assertion with try-catch block
            try {
                Assert.assertTrue(successMessageDisplayed, "Product was not added successfully!");
                logger.info("Assertion passed: Product added to cart successfully"); // Info log
            } catch (AssertionError e) {
                logger.error("Assertion failed: {}", e.getMessage(), e); // Error log
                throw e; // Re-throw the assertion error for RetryAnalyzer
            }

        } catch (Exception e) {
            logger.error("Exception occurred in testAddAnItemToWishList: {}", e.getMessage(), e); // Error log
            throw e; // Re-throw the exception for RetryAnalyzer
        }

        logger.debug("testAddAnItemToWishList completed successfully"); // Debug log
    }
}