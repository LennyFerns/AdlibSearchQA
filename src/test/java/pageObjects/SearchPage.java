package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class SearchPage extends BasePage {

	public SearchPage(WebDriver driver) {
		super(driver);
	}

	// Locators

	@FindBy(xpath = "//h1[normalize-space()='Search']")
	WebElement searchPage_Header;

	// Locators
	@FindBy(xpath = "//input[@id='q']")
	WebElement search_input;
	
	@FindBy(xpath = "//input[@id='advs']")
	WebElement chk_AdvancedSearch;
	
	@FindBy(id = "cid")
	WebElement dropdown_Category;

	
	@FindBy(id = "isc")
	WebElement chk_SubCategory;
	
	@FindBy(id = "mid")
	WebElement dropdown_Manufacturer;
	
	@FindBy(id = "sid")
	WebElement chk_SearchDescription;
	
	@FindBy(css = "button.search-button")
	WebElement btn_Search;
	


	// Action Methods
	
	public String getSearchHeaderText() {
	    return searchPage_Header.getText();
	}

	public void clickKeywordField() {
		search_input.click();
	}
	
	public void setkeyword(String keyword) {
	    wait.until(ExpectedConditions.elementToBeClickable(search_input));
	    search_input.clear();

	    for (char c : keyword.toCharArray()) {
	        search_input.sendKeys(Character.toString(c));
	        try {
	            Thread.sleep(50); // wait between keystrokes to simulate typing
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	public void clickAdvancedSearch() {
	    wait.until(ExpectedConditions.elementToBeClickable(chk_AdvancedSearch));
	    if (!chk_AdvancedSearch.isSelected()) {
	        chk_AdvancedSearch.click();
	    }
	}
	
	


	public void selectCategory(String categoryVisibleText) {
	    Select select = new Select(dropdown_Category);
	    select.selectByVisibleText(categoryVisibleText);
	}
	
	public void setSubCategory(boolean enable) {
	    if (chk_SubCategory.isSelected() != enable) {
	        chk_SubCategory.click();
	        try {
	            Thread.sleep(500); // short pause to allow UI/JS to respond
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	public void selectManufacturer(String manufacturerText) {
	    wait.until(ExpectedConditions.visibilityOf(dropdown_Manufacturer));
	    Select select = new Select(dropdown_Manufacturer);

	    String normalizedValue = manufacturerText.trim();

	    boolean found = false;
	    for (WebElement option : select.getOptions()) {
	        if (option.getText().trim().equalsIgnoreCase(normalizedValue)) {
	            select.selectByVisibleText(option.getText().trim());
	            found = true;
	            break;
	        }
	    }

	    if (!found) {
	        System.out.println("Manufacturer option not found: [" + manufacturerText + "]");
	    }
	}
	
	public void setSearchInDescription(boolean enable) {
	    wait.until(ExpectedConditions.visibilityOf(chk_SearchDescription)); // wait for visibility
	    if (chk_SearchDescription.isSelected() != enable) {
	        chk_SearchDescription.click();
	        try {
	            Thread.sleep(500); //wait to allow UI to update
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	public void clickSearchButton() {
	    wait.until(ExpectedConditions.elementToBeClickable(btn_Search)).click();
	}

	
}
