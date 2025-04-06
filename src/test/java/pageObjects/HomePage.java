package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

	public HomePage(WebDriver driver) {
		super(driver);
	}

	// Locators

	// Locators

		@FindBy(xpath = "//img[@alt='nopCommerce demo store']")
		WebElement content_HomePage;

		// Locators
		@FindBy(xpath = "//input[@id='small-searchterms']")
		WebElement search_input_Homepage;

		@FindBy(xpath = "//button[normalize-space()='Search']")
		WebElement btn_search;

		@FindBy(xpath = "//a[normalize-space()='Search']")
		WebElement link_search_footer;
		

	// Action Methods
		
		
		public String confirmHomepage() {
			return content_HomePage.getText();
		}
		
		public void clickSearchInput() {
			search_input_Homepage.click();
		}


		public void setkeyword(String keyword) {
			search_input_Homepage.sendKeys(keyword);
		}
		
		public void clickSearchButton() {
			btn_search.click();
		}
		
		// This clicks the footer "Search" link 
		public void clickSearchFooterLink() {
			link_search_footer.click();
		}

		

}
