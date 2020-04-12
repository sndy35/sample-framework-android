package com.telstra.android.pages;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.telstra.android.utils.AndroidActions;
import com.telstra.reporting.Logger;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class CheckoutProduct {
	private AppiumDriver driver;
	private WebDriverWait wait;
	private AndroidActions action;
	private Logger logger;
	
	@AndroidFindBy(xpath = "//android.widget.Button[contains(@text,'Add to Cart')]")
	private AndroidElement addTocartButton;
	
	@AndroidFindBy(xpath = "//android.widget.Button[contains(@text,'See All Buying Options')]")
	private AndroidElement buyingOptionsButton;

	@AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/action_bar_cart_count")
	private AndroidElement cartIcon;

	@AndroidFindBy(xpath = "//android.widget.Button[@text='Proceed to Checkout']")
	private AndroidElement checkoutButton;
	
	
	@AndroidFindBy(xpath = "//android.widget.Image[@class='android.widget.Image']")
	private AndroidElement ItemDetailsImage;
	
	@AndroidFindBy(xpath = "//android.widget.Button[@text='Proceed to Checkout']")
	private AndroidElement deliverButton;

	/*
	 * Constructor to initialize the CheckoutProduct class with driver object
	 */
	public CheckoutProduct(AppiumDriver driver, Logger logger) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 30);
		this.action = new AndroidActions(driver, logger);
		this.logger = logger;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	/*
	 * Function add Item to the cart
	 */
	public boolean addItemToCart() {
		checkBuyingOptions();
		action.scrollUp();
		try {
			wait.until(ExpectedConditions.visibilityOf(addTocartButton));
			addTocartButton.click();
		} catch(Exception e) {
			logger.info("addItemToCart: Add to cart button is not visible");
			return false;
		}
		if (cartIcon.getText().equalsIgnoreCase("1")) {
			return true;
		}
		return false;
	}
	
	/*
	 * Function to check buying options is visible
	 */
	private void checkBuyingOptions() {
		action.scrollUp();
		try {
			wait.until(ExpectedConditions.visibilityOf(buyingOptionsButton));
			buyingOptionsButton.click();	
		} catch(Exception e) {		
			logger.info("checkBuyingOptions: Buying Options Element is not visible");
		}
		
	}

	/*
	 * Function to click on cart once the item is added
	 */
	public void clickOnBasket() {
		wait.until(ExpectedConditions.visibilityOf(cartIcon));
		cartIcon.click();
		wait.until(ExpectedConditions.visibilityOf(checkoutButton));
		
	}
	
	/*
	 * Function to get the name of the product added in the cart
	 */
	public String getProductNameFromCheckoutScreen() {
		
		wait.until(ExpectedConditions.visibilityOf(ItemDetailsImage));
		return ItemDetailsImage.getAttribute("text");		
	}
	
	/*
	 * Function to checkout the cart
	 */
	public void checkout() {
		wait.until(ExpectedConditions.visibilityOf(checkoutButton));
		checkoutButton.click();
		wait.until(ExpectedConditions.visibilityOf(deliverButton));
		
	}
}
