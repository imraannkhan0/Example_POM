package org.example.test.common;

import io.appium.java_client.AppiumDriver;

import org.example.test.Initialize;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Field;

public class ElementLocatorFactory {
    public WebDriver webDriver;
    public AppiumDriver appiumDriver;
    private Initialize initialize;

    public ElementLocatorFactory(WebDriver driverRef, Initialize initialize) {
        this.webDriver = driverRef;
        this.initialize = initialize;
    }

    public ElementLocatorFactory(AppiumDriver appiumDriver,
                                 Initialize initialize) {
        this.appiumDriver = appiumDriver;
        this.initialize = initialize;
    }

    public ElementLocator createLocator(Field field) {
        return new ElementLocator(webDriver, appiumDriver, field, initialize);
    }

}
