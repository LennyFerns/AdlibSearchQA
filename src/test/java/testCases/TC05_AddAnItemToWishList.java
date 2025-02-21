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

public class TC05_AddAnItemToWishList extends BaseClass {

    private static final Logger logger = LogManager.getLogger(TC05_AddAnItemToWishList.class);

    @Test(groups = "regression", retryAnalyzer = RetryAnalyzer.class)
    public void testAddToCart() {
        try {
            logger.debug("Starting test: testAddToCart");

            // Navigate and select product
            HomePage hp = new HomePage(getDriver());
            logger.debug("Navigating to Laptops and Notebooks category");
            hp.navigateToLaptopsAndNotebooks();

            CategoryPage cp = new CategoryPage(getDriver());
            logger.debug("Selecting a product");
            cp.selectProduct();

            AddToCartPage atcp = new AddToCartPage(getDriver());
            logger.debug("Adding product to wishlist");
            atcp.addToWishList();

            boolean isMessageDisplayed = atcp.isWishListSuccessMessageDisplayed();
            logger.debug("Wishlist success message displayed: " + isMessageDisplayed);

            // Assertion with try-catch block
            try {
                Assert.assertTrue(isMessageDisplayed, "Success message not displayed");
                logger.info("Product successfully added to wishlist");
            } catch (AssertionError e) {
                logger.error("Wishlist success message validation failed", e);
                throw e;
            }
        } catch (Exception e) {
            logger.error("An error occurred during test execution", e);
            throw e;
        }
    }
}
