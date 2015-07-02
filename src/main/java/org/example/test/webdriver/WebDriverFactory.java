package org.example.test.webdriver;

import io.appium.java_client.AppiumDriver;

import java.net.URL;

import org.example.test.common.ProfileConfiguration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WebDriverFactory {

    private static WebDriver driverInstance = null;
    private static AppiumDriver appiumInstance = null;

    /**
     * Factory method for getting the firefox driver instance
     *
     * @param resetDriver boolean flag to reset the driver
     * @return WebDriver Instance of firefox driver
     */
    public static WebDriver getFirefoxDriver(boolean resetDriver,
                                             DesiredCapabilities dc) {
        if (driverInstance == null || resetDriver) {
            driverInstance = ProfileConfiguration.FIREFOX.getDriver(dc);
        }
        return driverInstance;
    }

    /**
     * Factory method for getting the chrome driver instance
     *
     * @param resetDriver boolean flag to reset the driver
     * @return WebDriver Instance of chrome driver
     */
    public static WebDriver getChromeDriver(boolean resetDriver,
                                            DesiredCapabilities dc) {
        if (driverInstance == null || resetDriver) {
            driverInstance = ProfileConfiguration.CHROME.getDriver(dc);
        }
        return driverInstance;
    }

    /**
     * Factory method for getting the Safari driver instance
     *
     * @param resetDriver boolean flag to reset the driver
     * @return WebDriver Instance of Safari driver
     */
    public static WebDriver getSafariDriver(boolean resetDriver,
                                            DesiredCapabilities dc) {
        if (driverInstance == null || resetDriver) {
            driverInstance = ProfileConfiguration.SAFARI.getDriver(dc);
        }
        return driverInstance;
    }

    /**
     * Factory method for getting the IE driver instance
     *
     * @param resetDriver boolean flag to reset the driver
     * @return WebDriver Instance of IE driver
     */
    public static WebDriver getInternetExplorerDriver(boolean resetDriver,
                                                      DesiredCapabilities dc) {
        if (driverInstance == null || resetDriver) {
            driverInstance = ProfileConfiguration.INTERNET_EXPLORER
                    .getDriver(dc);
        }
        return driverInstance;
    }

    /**
     * Factory method for getting the Android driver instance
     *
     * @param resetDriver boolean flag to reset the driver
     * @return WebDriver Instance of IE driver
     */
    public static WebDriver getSelendroidDriver(boolean resetDriver,
                                                DesiredCapabilities dc) {
        if (driverInstance == null || resetDriver) {
            driverInstance = ProfileConfiguration.ANDROID.getDriver(dc);
        }
        return driverInstance;
    }

    //Not implemented
    public static WebDriver getPhantomJsDriver(boolean resetDriver,
                                               DesiredCapabilities dc) {
        {
            if (driverInstance == null || resetDriver) {
                driverInstance = ProfileConfiguration.PHANTOMJS.getDriver(dc);
            }
            return driverInstance;
        }
    }

    public static AppiumDriver getAppiumDriver(boolean resetDriver, URL remoteUrl,
                                               DesiredCapabilities dc) {
        {
            if (appiumInstance == null || resetDriver) {
                appiumInstance = ProfileConfiguration.APPIUM.getAppiumDriver(remoteUrl, dc);
            }
            return appiumInstance;
        }
    }

    /**
     * Reset the driver instance
     */
    public static void resetDriver() {
        driverInstance = null;
    }
}
