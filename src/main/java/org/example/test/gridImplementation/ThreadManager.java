package org.example.test.gridImplementation;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Created by Sabyasachi on 22/6/2015.
 */
public class ThreadManager {

    private static ThreadLocal<AndroidDriver> thread = new ThreadLocal<>();
    private static ThreadLocal<RemoteWebDriver> threadWebdriver = new ThreadLocal<>();

    public AndroidDriver getAndroidDriver() {

        return thread.get();
    }

    public void setAndroidDriver(AndroidDriver androidDriver) {
        thread.set(androidDriver);
    }

    public WebDriver getWebdriver() {
        return threadWebdriver.get();
    }

    public void setWebDriver(RemoteWebDriver webDriver) {
        threadWebdriver.set(webDriver);
    }
}
