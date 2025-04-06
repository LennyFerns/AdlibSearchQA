package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.SearchPage;
import testBase.BaseClass;

public class TC08_Advance_Search_Page extends BaseClass {
	@Test(dataProvider = "SearchData", dataProviderClass = utilities.DataProviders.class, groups = {"smoke", "regression"})
    public void testAdvanceSearch(String keyword, String category, String subCategories, 
                                  String manufacturer, String searchDescriptions, String expectedResult) {

       

     

        SearchPage sp = new SearchPage(getDriver());

        // Verify we are on the Search page
        String actualHeader = sp.getSearchHeaderText();
        Assert.assertEquals(actualHeader, "Search", "Search page header mismatch");

        // Set keyword
        sp.clickKeywordField();
        sp.setkeyword(keyword);
        
     //Click "Advanced Search" checkbox
        sp.clickAdvancedSearch();

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

        if (expectedResult.contains(",")) {
            // Multiple expected results
            String[] expectedItems = expectedResult.split(",");
            for (String item : expectedItems) {
                Assert.assertTrue(pageSource.toLowerCase().contains(item.trim().toLowerCase()), 
                    "Expected item not found: " + item);
            }
        } else {
            // Single result case
            Assert.assertTrue(pageSource.toLowerCase().contains(expectedResult.toLowerCase()), 
                "Expected result not found: " + expectedResult);
        }

  
    }
}

