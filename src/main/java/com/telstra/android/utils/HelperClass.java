package com.telstra.android.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

/*
 * Static helper functions 
 */
public class HelperClass {

	public static void androidKeyPress(String key, AppiumDriver driver) {
		
		if(key.contains("ENTER"))
		{
			((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.ENTER));
		}
	}

}
