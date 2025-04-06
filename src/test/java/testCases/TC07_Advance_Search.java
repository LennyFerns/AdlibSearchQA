package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.SearchPage;
import testBase.BaseClass;

public class TC07_Advance_Search extends BaseClass {

	@Test(dataProvider = "SearchData", dataProviderClass = utilities.DataProviders.class, groups = {"smoke", "regression"})
    public void testAdvanceSearch(String keyword, String category, String subCategories, 
                                  String manufacturer, String searchDescriptions, String expectedResult) {

       

     // Navigate to Home Page  Could not work with as the page verification Pop up keeps coming up 
        HomePage hp = new HomePage(getDriver());

        // // Click search input field
        hp.clickSearchInput(); // You should have this method in HomePage
        hp.setkeyword(keyword);
        hp.clickSearchButton();

        SearchPage sp = new SearchPage(getDriver());

        // Verify we are on the Search page
        String actualHeader = sp.getSearchHeaderText();
        Assert.assertEquals(actualHeader, "Search", "Search page header mismatch");

        // Set keyword
        sp.clickKeywordField();
        sp.setkeyword(keyword);

        // Set Category dropdown
        sp.selectCategory(category);

        // Set Subcategory checkbox
        sp.setSubCategory(Boolean.parseBoolean(subCategories));

        // Set Manufacturer dropdown
        sp.selectManufacturer(manufacturer);

        // Set "Search in product descriptions" checkbox
        sp.setSearchInDescription(Boolean.parseBoolean(searchDescriptions));

        // Click Search button
        sp.clickSearchButton();

        // Wait and validate search result - You can extend this part based on your results page
        String pageSource = getDriver().getPageSource();
        Assert.assertTrue(pageSource.contains(expectedResult), 
            "Expected result not found: " + expectedResult);

  
    }
}


