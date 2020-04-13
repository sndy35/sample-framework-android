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

public class HomeScreen {
	private AppiumDriver driver;
    private WebDriverWait wait;
    private Logger logger;
    
    @AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/sign_in_button")
    private AndroidElement skipSignInButton;

    @AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/rs_search_src_text")
    private AndroidElement searchBar;
    
    @AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/action_bar_cart_count")
    private AndroidElement cartButton;
 
    /*
     * Constructor to initialize the HomeScreen with driver
     */
    
    public HomeScreen(AppiumDriver driver, Logger logger){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 30);
        this.logger = logger;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
    
    /*
     * @param searchText- Item to the searched in the search bar 
     */
    public void searchItem(String searchText){
    		logger.info("searchItem: Searching for item "+searchText);
        wait.until(ExpectedConditions.visibilityOf(searchBar));        
        searchBar.sendKeys(searchText);
        HelperClass.androidKeyPress("ENTER", driver);
    }
    
    /*
     * This functions verifies if the cart is empty
     * @return true if the cart is empty
     */
    public boolean verifyCartIsEmpty() {
	    	logger.info("verifyCartIsEmpty: Veriyfing if the cart is empty");
	    	try {
	    		wait.until(ExpectedConditions.visibilityOf(cartButton));
	    		if(cartButton.getText().equalsIgnoreCase("0")) {
	    			logger.info("verifyCartIsEmpty: cart is empty");
	    			return true;
	    		}
	    	} catch(Exception e) {
	    		logger.info("verifyCartIsEmpty:cartButton is not visible");
	    	}
	    	logger.info("verifyCartIsEmpty: cart is not empty");
	    	return false;
	    	
	}
    
}
