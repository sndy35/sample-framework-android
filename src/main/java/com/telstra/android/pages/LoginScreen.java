package com.telstra.android.pages;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.telstra.android.utils.HelperClass;
import com.telstra.reporting.Logger;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class LoginScreen {
	private AppiumDriver driver;
	private WebDriverWait wait;
	private Logger logger;

	@AndroidFindBy(xpath = "//android.widget.Button[@text='Skip sign in']")
	private AndroidElement skipSignInButton;

	@AndroidFindBy(xpath = "//android.widget.Button[contains(@text,'customer')]")
	private AndroidElement signInButton;
	
	@AndroidFindBy(xpath= "//android.widget.EditText[@resource-id='ap_email_login']")
	private AndroidElement usrInputBox;
	
	@AndroidFindBy(xpath= "//android.widget.EditText[@resource-id='ap_password']")
	private AndroidElement pwdInputBox;
	
	@AndroidFindBy(xpath= "//android.widget.Button[@resource-id='continue']")
	private AndroidElement continueBtn;
	
	@AndroidFindBy(xpath = "//android.widget.Button[@resource-id='signInSubmit']")
	private AndroidElement submitBtn;
	/*
	 * Constructor to initialize the loginScreen with driver
	 */
	public LoginScreen(AppiumDriver driver, Logger logger) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 30);
		this.logger = logger;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	/*
	 * This function skips the login and takes to the home screen of the app
	 */
	public void skipLogin() {
		logger.info("skipLogin: skipping Login");
		try {
			logger.info("skipLogin: checking visibility of skiplogin button");
			wait.until(ExpectedConditions.visibilityOf(skipSignInButton));
			skipSignInButton.click();
			logger.info("skipLogin: clicked on Skip SignIn button");
		} catch(Exception e) {
			logger.info("skipLogin: skiplogin button is not visible");
		}
	}
	
	/*
	 * This function will login to app using the credentials passed
	 */
	public void login(String userName, String passwordEncrypted) {
		logger.info("login:  Login to app with user "+userName);
		try {
			wait.until(ExpectedConditions.visibilityOf(signInButton));
			signInButton.click();
			wait.until(ExpectedConditions.visibilityOf(usrInputBox));
			logger.info("login:  Entering username");
			usrInputBox.sendKeys(userName);
			HelperClass.androidKeyPress("ENTER", driver);
			wait.until(ExpectedConditions.visibilityOf(continueBtn));
			continueBtn.click();
			logger.info("login:  Entering passwords");
			pwdInputBox.sendKeys(passwordEncrypted);
			HelperClass.androidKeyPress("ENTER", driver);
			wait.until(ExpectedConditions.visibilityOf(submitBtn));
			logger.info("login:  Clicking on submit button");
			submitBtn.click();
		} catch(Exception e) {
			logger.info("login: "+e.getMessage());
		}
		
	}
	
	

}
