package org.example.test.common;

import java.io.File;
import java.io.IOException;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

public class ScreenshotHelper {

    public static void takeScreenShot(WebDriver webDriver, IElement element) {
        String path = "./target/screeners/" + element.toString()
                + System.currentTimeMillis();
        takeScreenShot(webDriver, path);
    }

    public static void takeScreenShot(WebDriver webDriver, String path) {
        File screenshot = null;
        try {
            screenshot = ((TakesScreenshot) webDriver)
                    .getScreenshotAs(OutputType.FILE);
        } catch (WebDriverException e) {
            System.out.println("Unable to capture Screenshot. Moving on...\n");
            e.printStackTrace();
        }
        try {
            if (null != screenshot) {
                FileUtils.copyFile(screenshot, new File(path + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void takeScreenShot(WebDriver webDriver) {
        int parser = 0;
        StackTraceElement caller = null;
        StackTraceElement[] list = Thread.currentThread().getStackTrace();
        for (StackTraceElement singleElement : list) {
            if (singleElement.toString().contains("NativeMethodAccessorImpl")
                    || singleElement.toString().contains(
                    "GeneratedMethodAccessor")) {
                caller = list[parser - 1];
                break;
            } else {
                parser++;
            }
        }
        takeScreenShot(
                webDriver,
                "./target/screeners/" + caller.getClassName() + "/"
                        + caller.getMethodName() + "/"
                        + System.currentTimeMillis());
    }

}
