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
		logger.info("Running Test to search for "+searchText);
		LoginScreen loginScreen = new LoginScreen(driver, logger);
		loginScreen.skipLogin();
		String actual = "";
		String expected = "";
		HomeScreen homeScreen = new HomeScreen(driver, logger);
		boolean cartEmpty = homeScreen.verifyCartIsEmpty();
		if(!cartEmpty) {
			logger.fail("cart is not empty after login");
		}
		homeScreen.searchItem(searchText);
		SearchResultsScreen searchScreen = new SearchResultsScreen(driver, logger);
		expected = searchScreen.selectRandomItemFromList(searchText);
		CheckoutProduct checkoutProduct = new CheckoutProduct(driver, logger);
		checkoutProduct.addItemToCart();
		checkoutProduct.clickOnBasket();
		actual = checkoutProduct.getProductNameFromCheckoutScreen();
		Assert.assertTrue(expected.contains(actual), "Expected value does not match actual value");
		

	}

}
