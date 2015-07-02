package org.example.test.appiumdriver;

import io.appium.java_client.MobileElement;
import io.appium.java_client.NetworkConnectionSetting;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.PushesFiles;
import io.appium.java_client.android.StartsActivity;
import io.appium.java_client.ios.IOSDriver;

import java.net.URL;
import java.util.List;

import org.example.test.common.RENDERINGDRIVER;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

public class AppiumDriverFactory {
    static AndroidDriver androidDriver;
    static IOSDriver iosDriver;

    public AppiumDriverFactory(URL remoteUrl,
                               DesiredCapabilities desiredCapabilities, RENDERINGDRIVER renderingdriver) {
        if (renderingdriver == RENDERINGDRIVER.AppAndroid && androidDriver == null) {
            try {
                androidDriver = new AndroidDriver(remoteUrl, desiredCapabilities);
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else if (renderingdriver == RENDERINGDRIVER.AppIOS) {
            iosDriver = new IOSDriver(remoteUrl, desiredCapabilities);
        }
    }

    public AppiumDriverFactory() {
        // TODO Auto-generated constructor stub
    }

    public AndroidDriver getAndriodDriver() {
        return androidDriver;
    }

    public IOSDriver getIOSDriver() {
        return iosDriver;
    }


    public MobileElement Android_scrollTo(String text) {

        return androidDriver.scrollTo(text);
    }

    public MobileElement Android_scrollToExact(String text) {
        return androidDriver.scrollToExact(text);
    }

    public void Android_sendKeyEvent(int key, Integer metastate) {
        androidDriver.sendKeyEvent(key);
    }

    public NetworkConnectionSetting Android_getNetworkConnection() {
        return androidDriver.getNetworkConnection();
    }

    public void Android_setNetworkConnection(NetworkConnectionSetting connection) {
        androidDriver.setNetworkConnection(connection);
    }

    /**
     * @param remotePath Path to file to write data to on remote device
     * @param base64Data Base64 encoded byte array of data to write to remote device
     * @see io.appium.java_client.android.PushesFiles#pushFile(String, byte[])
     */
    public void Android_pushFile(String remotePath, byte[] base64Data) {
        androidDriver.pushFile(remotePath, base64Data);
    }

    /**
     * @param appPackage      The package containing the activity. [Required]
     * @param appActivity     The activity to start. [Required]
     * @param appWaitPackage  Automation will begin after this package starts. [Optional]
     * @param appWaitActivity Automation will begin after this activity starts. [Optional]
     * @example driver.startActivity("com.foo.bar", ".MyActivity", null, null);
     * @see io.appium.java_client.android.StartsActivity#startActivity(String, String, String, String)
     */
    public void Android_startActivity(String appPackage, String appActivity,
                                      String appWaitPackage, String appWaitActivity)
            throws IllegalArgumentException {

        androidDriver.startActivity(appPackage, appActivity, appWaitPackage, appWaitActivity);
    }

    /**
     * @param appPackage  The package containing the activity. [Required]
     * @param appActivity The activity to start. [Required]
     * @example *.startActivity("com.foo.bar", ".MyActivity");
     * @see io.appium.java_client.android.StartsActivity#startActivity(String, String)
     */
    public void Android_startActivity(String appPackage, String appActivity)
            throws IllegalArgumentException {
        androidDriver.startActivity(appPackage, appActivity);
    }

    /**
     * Get test-coverage data
     *
     * @param intent intent to broadcast
     * @param path   path to .ec file
     */
    public void Android_endTestCoverage(String intent, String path) {
        androidDriver.endTestCoverage(intent, path);
    }

    /**
     * Get the current activity being run on the mobile device
     */
    public String Android_currentActivity() {
        return androidDriver.currentActivity();
    }

    /**
     * Open the notification shade, on Android devices.
     */
    public void Android_openNotifications() {
        androidDriver.openNotifications();
    }

    /**
     * Check if the device is locked.
     *
     * @return true if device is locked. False otherwise
     */
    public boolean Android_isLocked() {
        return androidDriver.isLocked();
    }

    public void Android_toggleLocationServices() {
        androidDriver.toggleLocationServices();
    }

    /**
     * Set the `ignoreUnimportantViews` setting. *Android-only method*
     * <p/>
     * Sets whether Android devices should use `setCompressedLayoutHeirarchy()`
     * which ignores all views which are marked IMPORTANT_FOR_ACCESSIBILITY_NO
     * or IMPORTANT_FOR_ACCESSIBILITY_AUTO (and have been deemed not important
     * by the system), in an attempt to make things less confusing or faster.
     *
     * @param compress ignores unimportant views if true, doesn't ignore otherwise.
     */
    // Should be moved to the subclass
    public void Android_ignoreUnimportantViews(Boolean compress) {
        androidDriver.ignoreUnimportantViews(compress);
    }

    public WebElement Android_findElementByAndroidUIAutomator(String using) {
        return androidDriver.findElementByAndroidUIAutomator(using);
    }

    public List<WebElement> Android_findElementsByAndroidUIAutomator(String using) {
        return androidDriver.findElementsByAndroidUIAutomator(using);

    }

    public WebElement Android_findElementByAccessibility(String using){
        return androidDriver.findElementByAccessibilityId(using);
    }


}
