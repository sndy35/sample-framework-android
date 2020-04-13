package com.telstra.android.search;


import org.testng.Assert;
import org.testng.annotations.Test;
import com.telstra.android.testdata.TestParameters;
import com.telstra.android.BaseTest;
import com.telstra.android.pages.CheckoutProduct;
import com.telstra.android.pages.HomeScreen;
import com.telstra.android.pages.LoginScreen;
import com.telstra.android.pages.SearchResultsScreen;

public class SearchProductTest extends BaseTest {
	
	@Test(dataProvider = "testData",retryAnalyzer=com.telstra.android.listeners.RetryListener.class)
	public void searchProduct(String testName, TestParameters test) throws InterruptedException {
		String searchText = test.getSearchText();
		logger.info("searchProduct: Running Test to search for "+searchText);
		LoginScreen loginScreen = new LoginScreen(driver, logger);
		loginScreen.skipLogin();
		String actual = null;
		String expected = null;
		HomeScreen homeScreen = new HomeScreen(driver, logger);
		boolean cartEmpty = homeScreen.verifyCartIsEmpty();
		if(!cartEmpty) {
			logger.fail("searchProduct: cart is not empty after login");
			Assert.fail("searchProduct: cart is not empty after login");
		}
		homeScreen.searchItem(searchText);
		SearchResultsScreen searchScreen = new SearchResultsScreen(driver, logger);
		expected = searchScreen.selectRandomItemFromList(searchText);
		logger.info("searchProduct: Expected product name is "+expected);
		if(expected == null) {
			logger.fail("searchProduct: product returned is empty");
			Assert.fail("searchProduct: product returned is empty");
		}			
		CheckoutProduct checkoutProduct = new CheckoutProduct(driver, logger);
		checkoutProduct.addItemToCart();
		checkoutProduct.clickOnBasket();
		actual = checkoutProduct.getProductNameFromCheckoutScreen();
		logger.info("searchProduct: Actual product name in checkout screen is "+actual);
		if(!expected.contains(actual)) {
			logger.fail("searchProduct: product name in checkout screen is not matching with product selected");
		}
		Assert.assertTrue(expected.contains(actual), "Expected product value does not match actual product value");
		

	}

}
