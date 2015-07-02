package org.example.test.common;

import io.appium.java_client.AppiumDriver;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.google.common.base.Throwables;

public enum ProfileConfiguration {
    /**
     * Profile for firefox
     */
    FIREFOX(DriverMapping.FIREFOX_DRIVER, 10, 60, TimeUnit.SECONDS),
    /**
     * profile for Internet Explorer
     */
    INTERNET_EXPLORER(
            DriverMapping.INTERNET_EXPLORER_DRIVER,
            30,
            30,
            TimeUnit.SECONDS),
    /**
     * profile for chrome
     */
    CHROME(DriverMapping.CHROME_DRIVER, 10, 300, TimeUnit.SECONDS),
    /**
     * profile for Safari
     */
    SAFARI(DriverMapping.SAFARI_DRIVER, 30, 30, TimeUnit.SECONDS),
    /**
     * Profile for android
     */
    ANDROID(DriverMapping.ANDROID_DRIVER, 10, 300, TimeUnit.SECONDS),
    /**
     * Attribute for Driver Configuration
     */
    PHANTOMJS(DriverMapping.PHANTOMJS_DRIVER, 10, 300, TimeUnit.SECONDS),
    /**
     * Attribute for AppiumDriver Configuration
     */
    APPIUM(DriverMapping.APPIUM_DRIVER, 80, 300, TimeUnit.SECONDS);
    /**
     * Attribute for Driver Configuration
     */
    private DriverMapping driverMapping;
    /**
     * Time to wait when an element is not visible immediately
     */
    private long implicitWait;
    /**
     * Time to wait for a page to load
     */
    private long pageLoadTimeout;
    /**
     * TimeUnit for the number specified @implicitWait and @pageLoadTimeout
     */
    private TimeUnit timeUnitForTimeout;

    private ProfileConfiguration(DriverMapping driverMapping,
                                 long implicitWait, long pageLoadTimeout, TimeUnit timeUnitForTimeout) {
        this.driverMapping = driverMapping;
        this.implicitWait = implicitWait;
        this.pageLoadTimeout = pageLoadTimeout;
        this.timeUnitForTimeout = timeUnitForTimeout;
    }

    /**
     * Clients need to invoke this method to get an instance of the driver That
     * instance needs to be propagated across the test case. Every call to this
     * method will create a new browser window Use it carefully
     *
     * @return WebDriver Instance of any of the supported Browser Driver class
     */
    public WebDriver getDriver(DesiredCapabilities desiredCapabilities) {
        int count = 0;
        WebDriver driver = null;
        do {
            try {
                if (null != desiredCapabilities) {
                    driver = (WebDriver) Class
                            .forName(this.driverMapping.getDriver().getName())
                            .getConstructor(Capabilities.class)
                            .newInstance(desiredCapabilities);
                } else {
                    driver = (WebDriver) Class.forName(
                            this.driverMapping.getDriver().getName())
                            .newInstance();
                }
            } catch (ClassNotFoundException | IllegalAccessException
                    | InstantiationException | NoSuchMethodException
                    | InvocationTargetException e) {
                Throwables.propagateIfPossible(e);
                e.printStackTrace();
                System.out.println(e);
            }
            count++;
        } while (null == driver && count < 5);
        driver.manage().timeouts()
                .pageLoadTimeout(this.pageLoadTimeout, this.timeUnitForTimeout);
        driver.manage().timeouts()
                .implicitlyWait(this.implicitWait, this.timeUnitForTimeout);
        return driver;
    }

    /**
     * Clients need to invoke this method to get an instance of the Appiumdriver
     * That instance needs to be propagated across the test case. Every call to
     * this method will create a new browser window Use it carefully
     *
     * @return AppiumDriver Instance of any of the supported MobileApps(Android
     * and IOS)
     * @author c.sivasubramanian
     */

    public AppiumDriver getAppiumDriver(URL remoteUrl,
                                        DesiredCapabilities desiredCapabilities) {
        AppiumDriver driver = null;
        try {
            if (null != desiredCapabilities) {
                driver = (AppiumDriver) Class
                        .forName(this.driverMapping.getDriver().getName())
                        .getConstructor(URL.class, Capabilities.class)
                        .newInstance(remoteUrl, desiredCapabilities);
            } else {
                driver = (AppiumDriver) Class.forName(
                        this.driverMapping.getDriver().getName()).newInstance();
            }
        } catch (ClassNotFoundException | IllegalAccessException
                | InstantiationException | NoSuchMethodException
                | InvocationTargetException e) {
            Throwables.propagateIfPossible(e);
        }
        driver.manage().timeouts()
                .implicitlyWait(this.implicitWait, this.timeUnitForTimeout);
        return driver;
    }
}