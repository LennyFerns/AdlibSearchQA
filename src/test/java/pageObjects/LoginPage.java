package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//input[@id='Email']")
	WebElement txt_Email;

	@FindBy(xpath = "//input[@id='Password']")
	WebElement txt_Password;

	@FindBy(xpath = "//button[normalize-space()='Log in']")
	WebElement btn_Login;

	public void setEmail(String email) {
		txt_Email.sendKeys(email);
	}

	public void setPwd(String pwd) {
		txt_Password.sendKeys(pwd);
	}

	public void clickLogin() {
		btn_Login.click();
	}

}
