package com.telstra.android.utils;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.telstra.reporting.Logger;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;

/*
 * Constructor to initialize the AndroidActions class with driver object
 */
public class AndroidActions {
	private AppiumDriver driver;
	private TouchAction action;
	private WebDriverWait wait;
	private Logger logger;

	public AndroidActions(AppiumDriver driver, Logger logger) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 30);
		this.action = new TouchAction(driver);
		this.logger = logger;

	}

	/*
	 * Returns the centre point co-ordinates of the screen irrespective of screen
	 * size
	 */
	private PointOption getCentrePoint() {
		Dimension dimensions = driver.manage().window().getSize();
		int StartX = (int) (dimensions.getWidth() * 0.5);
		int startY = (int) (dimensions.getHeight() * 0.5);
		PointOption centre = new PointOption().withCoordinates(StartX, startY);
		return centre;

	}

	/*
	 * Returns the top point co-ordinates of the screen irrespective of screen size
	 */
	private PointOption getTopPoint() {
		Dimension dimensions = driver.manage().window().getSize();
		int StartX = (int) (dimensions.getWidth() * 0.5);
		PointOption top = new PointOption().withCoordinates(StartX, 100);
		return top;
	}

	/*
	 * Returns the bottom point co-ordinates of the screen irrespective of screen
	 * size
	 */
	private PointOption getBottomPoint() {
		Dimension dimensions = driver.manage().window().getSize();
		int StartX = (int) (dimensions.getWidth() * 0.5);
		int StartY = (int) (dimensions.getWidth() * 0.9);
		PointOption bottom = new PointOption().withCoordinates(StartX, StartY);
		return bottom;
	}

	/*
	 * utility function to scroll up the screen
	 */
	public void scrollUp() {
		PointOption centre = getCentrePoint();
		PointOption top = getTopPoint();
		action.longPress(centre).moveTo(top).release().perform();
	}

	/*
	 * utility function to scroll down the screen
	 */
	public void scrollDown() {
		PointOption centre = getCentrePoint();
		PointOption bottom = getBottomPoint();
		action.longPress(centre).moveTo(bottom).release().perform();
	}

	/*
	 * utility function to scroll to the element passed as param
	 * 
	 * @param - Element to be scrolled to
	 */
	public void scrollToElement(MobileElement el) {
		logger.info("scrollToElement: Scroll to element"+el);
		boolean visible = false;
		int attempts = 0;
		while (!visible && attempts < 5) {
			try {
				wait.until(ExpectedConditions.visibilityOf(el));
				logger.info("scrollToElement: element is visible");
				visible = true;
			} catch (Exception e) {
				logger.info("scrollToElement: Exception thrown"+ e.getMessage());
				scrollUp();
			}
			attempts++;
		}
		if(!visible) {
			logger.fail("scrollToElement: Element not found");
		}

	}

}
