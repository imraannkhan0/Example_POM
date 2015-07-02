package org.example.test.common;

import io.appium.java_client.AppiumDriver;


import io.appium.java_client.ios.IOSDriver;
import io.selendroid.SelendroidDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

public enum DriverMapping {

    CHROME_DRIVER(ChromeDriver.class),
    FIREFOX_DRIVER(FirefoxDriver.class),
    INTERNET_EXPLORER_DRIVER(InternetExplorerDriver.class),
    SAFARI_DRIVER(SafariDriver.class),
    PHANTOMJS_DRIVER(PhantomJSDriver.class),
    ANDROID_DRIVER(SelendroidDriver.class),
    IOS_DRIVER(IOSDriver.class),

    //REMOTE_WEBDRIVER(RemoteWebDriver.class),

    APPIUM_DRIVER(AppiumDriver.class);
    private Class<? extends WebDriver> driver;

    private DriverMapping(Class<? extends WebDriver> driver) {
        this.driver = driver;
    }

    public Class<? extends WebDriver> getDriver() {
        return driver;
    }

}
