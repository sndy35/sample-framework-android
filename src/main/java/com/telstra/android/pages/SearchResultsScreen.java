package com.telstra.android.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.telstra.android.utils.AndroidActions;
import com.telstra.reporting.Logger;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class SearchResultsScreen {
	private AppiumDriver driver;
	private WebDriverWait wait;
	private Logger logger;

	@AndroidFindBy(id = "add-to-cart-button")
	private AndroidElement addToCartButton;

	@AndroidFindBy(xpath = "//android.view.View[1]/android.view.View")
	private List<AndroidElement> searchResults;

	/*
	 * Constructor to initialize the SearchResultsScreen with driver
	 */
	public SearchResultsScreen(AppiumDriver driver, Logger logger) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 30);
		this.logger = logger;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	/*
	 * This function selects the items randomly from list of items in search screen
	 */
	public String selectRandomItemFromList(String searchText) {
		logger.info("selectRandomItemFromList: selecting random item from list of results");
		wait.until(ExpectedConditions.visibilityOf(searchResults.get(0)));
		logger.info("selectRandomItemFromList: Total number of search results "+searchResults.size());
		try {
			List<String> validItems = new ArrayList<String>();
			for (MobileElement el : searchResults) {
				String txtAttribute = el.getAttribute("text");
				if(txtAttribute.contains("TV")) {
					validItems.add("//android.view.View/android.view.View/android.view.View[@text='" + txtAttribute + "']");
				}
			}
			int validResults = validItems.size();
			logger.info("selectRandomItemFromList: Valid search results "+validResults);
			logger.info("selectRandomItemFromList: Selecting random item from search list");
			int randomIndex = (int)(Math.random()*validResults);
			AndroidActions actions = new AndroidActions(driver, logger);
			MobileElement searchItem = (MobileElement) driver.findElementByXPath(validItems.get(randomIndex));
			actions.scrollToElement(searchItem);
			String productName = searchItem.getAttribute("text");
			driver.findElementByXPath(validItems.get((int)(Math.random()*validItems.size()))).click();
			logger.info("selectRandomItemFromList: Selected Item "+searchText);
			return productName;
		} catch(Exception e) {
			logger.info("selectRandomItemFromList: "+e.getMessage());
		}
		return null;
	}

}
