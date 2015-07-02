package org.example.test.common;

import java.lang.reflect.Field;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

import org.example.test.Initialize;
import org.example.test.PLATFORM;
import org.openqa.selenium.WebDriver;

public class PageFactory {

    public static void initElements(AppiumDriver appiumDriver, Object page,
                                    Initialize initialize) {
        Class<?> proxyIn;
        FieldDecorator decorator = new FieldDecorator(
                new ElementLocatorFactory(appiumDriver, initialize));
        if (initialize.TestEnvironment.TestTarget.Platform == PLATFORM.APP) {
            proxyIn = page.getClass().getSuperclass();
        } else {
            proxyIn = page.getClass();
        }
        while (proxyIn != Object.class) {
            proxyFields(decorator, page, proxyIn);
            proxyIn = proxyIn.getSuperclass();
        }
    }

    public static void initElements(WebDriver driverRef, Object page,
                                    Initialize initialize) {
        FieldDecorator decorator = new FieldDecorator(
                new ElementLocatorFactory(driverRef, initialize));
        Class<?> proxyIn = page.getClass();
        while (proxyIn != Object.class) {
            proxyFields(decorator, page, proxyIn);
            proxyIn = proxyIn.getSuperclass();
        }

    }

    private static void proxyFields(FieldDecorator decorator, Object page,
                                    Class<?> proxyIn) {
        Field[] fields = proxyIn.getDeclaredFields();
        for (Field field : fields) {
            Object value = decorator.decorate(page.getClass().getClassLoader(),
                    field);
            if (value != null) {
                try {
                    field.setAccessible(true);
                    field.set(page, value);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
