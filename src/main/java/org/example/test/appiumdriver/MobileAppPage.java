package org.example.test.appiumdriver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.selendroid.SelendroidKeys;
import net.lightbody.bmp.core.har.HarLog;

import org.example.test.Initialize;
import org.example.test.common.PageFactory;
import org.example.test.common.RENDERINGDRIVER;
import org.example.test.webdriver.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.google.common.base.Function;
import com.google.common.base.Throwables;

public class MobileAppPage {

    private String os;
    protected static WebDriver webDriver;
    private DesiredCapabilities desiredCapabilities;
    protected static HarLog harLog;
    boolean isDesktop = false;

    private Initialize initialize;
    protected static RENDERINGDRIVER renderingdriver;
    protected static AndroidDriver androidDriver;
    protected static IOSDriver iosDriver;


    /**
     * This constructor should take parameters specific to the browser being
     * used and use it to invoke the required browser using
     * {@link org.example.test.appiumdriver.AppiumDriverFactory}
     */
    public MobileAppPage(Initialize initialize) {
        this.initialize = initialize;
        if (null == getAndriodDriver() && null == getIOSDriver()) {
            os = System.getProperty("os.name");
            System.out.println("OS : " + os);
            String platform = System.getenv("platform");
            System.out.println("Plaform : " + platform);
            String renderingDriver = System.getenv("renderingDriver");
            System.out.println("Renderer : " + renderingDriver);
            String device = System.getenv("device");
            System.out.println("Device : " + device);
            String targetMobileApp = System.getenv("aut");
            System.out.println("AUT : " + targetMobileApp);

            // If platform does not come from Jenkins, pick it up from
            // Initialize
            if (null == platform) {
                platform = this.initialize.TestEnvironment.TestTarget
                        .getPlatform().toString().toLowerCase();
            }
            switch (platform) {
                // Mobile Web Tests are done via a user agent
                case "app":

                    // If renderingDriver does not come from Jenkins, pick it from
                    // Initialize
                    if (null == renderingDriver) {
                        renderingDriver = this.initialize.TestEnvironment.TestTarget
                                .getRenderingDriver().toString();
                    }
                    renderingdriver = RENDERINGDRIVER
                            .valueOf(renderingDriver);
                    switch (renderingdriver) {
                        case AppAndroid:
                            startAppiumServer();
                            setupAndroidAppCapabilities();
                            try {
                                androidDriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), desiredCapabilities);
                                Thread.sleep(5000);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Throwables.propagateIfPossible(e);
                            }
                            break;
                        case AppSelendroid:
                            startAppiumServer();
                            setupAndroidAppCapabilities();
                            try {
                                androidDriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), desiredCapabilities);
                                Thread.sleep(5000);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Throwables.propagateIfPossible(e);
                            }
                            break;
                        case AppIOS:
                            startAppiumServer();
                            setupIosAppCapabilities();
                            try {
                                iosDriver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), desiredCapabilities);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Throwables.propagateIfPossible(e);
                            }
                            break;
                        case AppWindows:
                            break;
                        default:
                            Assert.fail("RenderingDriver not compatible on Platform. Platform : "
                                    + platform
                                    + " RenderingDriver : "
                                    + renderingDriver);
                    }

                    break;
                default:
                    Assert.fail("Only Mobile platform is authorized in this flow. Platform : "
                            + platform
                            + " RenderingDriver : "
                            + renderingDriver);

            }
            System.out.println("====@@====Initializing "
                    + this.getClass().getSimpleName() + "====@@====");


        }
        if (renderingdriver == RENDERINGDRIVER.AppAndroid || renderingdriver == RENDERINGDRIVER.AppSelendroid) {
            PageFactory.initElements(getAndriodDriver(), this, this.initialize);
        } else {
            PageFactory.initElements(getIOSDriver(), this, this.initialize);
        }
    }


    public static void endTest() {
        try {
            storeADBLogs();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (null != getAndriodDriver()) {
//			ScreenshotHelper.takeScreenShot(appiumDriver);
            getAndriodDriver().quit();
        } else {
            getIOSDriver().quit();
        }
    }


    public static void scrollDown() {
        if (renderingdriver == RENDERINGDRIVER.AppAndroid) {

            getAndriodDriver().swipe(400, 1100, 400, 300, 1200);
        } else if (renderingdriver == RENDERINGDRIVER.AppIOS) {
            getIOSDriver().swipe(10, 497, 320, 100, 1000);

        }
    }

    public static void pressBackButtonOfDevice() {
        getAndriodDriver().sendKeyEvent(4);

    }

    public static void scrollUp() {
        if (renderingdriver == RENDERINGDRIVER.AppAndroid) {
            getAndriodDriver().swipe(400, 300, 400, 1100, 1200);
//			WebElement ele14 = getAndriodDriver().findElement(By.xpath("//*[@id='tv_pdp_description']"));
//			TouchActions flick = new TouchActions(getAndriodDriver()).flick(ele14, 0, -100, 100);
//			flick.perform();
        } else if (renderingdriver == RENDERINGDRIVER.AppIOS) {
            getIOSDriver().swipe(75, 500, 75, 100, 1200);
        }
    }

    public WebElement fluentFinder(final By by) {
        return new FluentWait<AppiumDriver>(getAndriodDriver())
                .withTimeout(20, TimeUnit.SECONDS)
                .pollingEvery(500, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .withMessage("Retrying element again : " + by.toString())
                .until(new Function<AppiumDriver, WebElement>() {
                    public WebElement apply(AppiumDriver webDriver) {
                        return webDriver.findElement(by);
                    }
                });
    }

//	public WebElement fluentAndroidFinder(final String text) {
//		return new FluentWait<AppiumDriver>(appiumDriver)
//				.withTimeout(20, TimeUnit.SECONDS)
//				.pollingEvery(500, TimeUnit.MILLISECONDS)
//				.ignoring(NoSuchElementException.class)
//				.withMessage("Retrying element again : " + text)
//				.until(new Function<AppiumDriver, WebElement>() {
//					public WebElement apply(AppiumDriver webDriver) {
//						return (WebElement) webDriver.findElementsByAndroidUIAutomator(text);
//					}
//				});
//	}

    public List<WebElement> fluentListFinder(final By by) {
        return new FluentWait<AppiumDriver>(getAndriodDriver())
                .withTimeout(20, TimeUnit.SECONDS)
                .pollingEvery(500, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .withMessage("Retrying element again : " + by.toString())
                .until(new Function<AppiumDriver, List<WebElement>>() {
                    public List<WebElement> apply(AppiumDriver webDriver) {
                        return webDriver.findElements(by);
                    }
                });
    }

    public void elementPresent() {
        WebDriverWait wait = new WebDriverWait(webDriver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("someid")));
    }

    public static void webview() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Set<String> lst = new HashSet<String>();
        String contextid = null;
        String temp = null;
        if (renderingdriver == RENDERINGDRIVER.AppAndroid || renderingdriver == RENDERINGDRIVER.AppSelendroid) {
            lst = getAndriodDriver().getContextHandles();
            Iterator<String> it = lst.iterator();
            while (it.hasNext()) {
                contextid = it.next();
                if (contextid.contains("WEBVIEW")) {
                    temp = contextid;
                }
            }
            getAndriodDriver().context(temp);
        } else {
            lst = getIOSDriver().getContextHandles();
            Iterator<String> it = lst.iterator();
            while (it.hasNext()) {
                contextid = it.next();
                if (contextid.contains("WEBVIEW")) {
                    temp = contextid;
                }
            }
            getIOSDriver().context(temp);

        }

    }

    public static void nativeView() {
        if (renderingdriver == RENDERINGDRIVER.AppAndroid || renderingdriver == RENDERINGDRIVER.AppSelendroid) {
            getAndriodDriver().context("NATIVE_APP");
        } else {
            getIOSDriver().context("NATIVE_APP");
        }
    }


    public boolean isToastMessagePresent(String setText) {
        boolean isPresent = false;
        System.out.println(fluentFinder(By.linkText(setText)).getText());
        if (fluentFinder(By.name(setText)).getText().equals(setText)) {
            isPresent = true;
        }
        return isPresent;
    }


    public void pauseForSomeTime() {
        //new MyntActions(webDriver).pause(3000).perform();
    }


    public static String getCurrentUrl() {
        return webDriver.getCurrentUrl();
    }

    private void setupIosAppCapabilities() {
        String deviceid = System.getenv("deviceid");
        System.out.println(deviceid);
        if (null == desiredCapabilities) {
            desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, CapabilityValues.JAVASCRIPT_ENABLED);
            desiredCapabilities.setCapability(CapabilityType.HAS_TOUCHSCREEN, CapabilityValues.TOUCHSCREEN_ENABLED);
            desiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, CapabilityValues.SSL_CERTIFICATE_ENABLED);
            desiredCapabilities.setCapability("automationName", CapabilityValues.APPIUM);
            desiredCapabilities.setCapability("browserName", "");
            desiredCapabilities.setCapability("autoLaunch", CapabilityValues.AUTOLUNCH_ENABLED);
//		 	desiredCapabilities.setCapability("appium-version", "1.0");
            desiredCapabilities.setCapability("platformName", CapabilityValues.IOS_PLATFORM);
            desiredCapabilities.setCapability("platformVersion", "8.1");
//			desiredCapabilities.setCapability("deviceName", "iPhone 5s (8.1 Simulator) [2C0E77E7-4BF7-4B25-860E-FFB17CD79B3B]");//For Local testing
            if (null == deviceid) {
                desiredCapabilities.setCapability("deviceName", CapabilityValues.IOS_DEVICE);
            } else {
                desiredCapabilities.setCapability("deviceName", deviceid);
            }
//			desiredCapabilities.setCapability("app", "/Users/c.sivasubramanian/Library/Developer/Xcode/DerivedData/Myntra-chdkpohusyvxplcsagsomxhzwvnp/Build/Products/Debug-iphonesimulator/Myntra.app");
            desiredCapabilities.setCapability("app", CapabilityValues.IOS_APP);
            desiredCapabilities.setCapability("bundleId", "com.myntra.Myntra");
            desiredCapabilities.setCapability("showIOSLog", true);

        }

    }

    private void setupAndroidAppCapabilities() {
        String deviceid = System.getenv("deviceid");
        System.out.println(deviceid);
        if (null == desiredCapabilities) {
            desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability("browserName", "");
            desiredCapabilities.setCapability("newCommandTimeout", "500");
            if (renderingdriver == RENDERINGDRIVER.AppSelendroid) {
                desiredCapabilities.setCapability("automationName", CapabilityValues.SELENDROID);
            } else {
                desiredCapabilities.setCapability("automationName", CapabilityValues.APPIUM);
            }
            desiredCapabilities.setCapability("platformName", CapabilityValues.ANDROID_PLATFORM);
            desiredCapabilities.setCapability("deviceType", CapabilityValues.MOBILE_DEVICE);
            desiredCapabilities.setCapability("deviceName", CapabilityValues.ANDROID_DEVICE);
            desiredCapabilities.setCapability(CapabilityType.VERSION, CapabilityValues.ANDROID_VERSION);
            if (os.contains("Windows")) {
                desiredCapabilities.setCapability(CapabilityType.PLATFORM, CapabilityValues.PLATFORM_WINDOWS);

            } else {

                desiredCapabilities.setCapability(CapabilityType.PLATFORM, CapabilityValues.PLATFORM_MAC);

            }
            desiredCapabilities.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, CapabilityValues.JAVASCRIPT_ENABLED);
            desiredCapabilities.setCapability(CapabilityType.HAS_TOUCHSCREEN, CapabilityValues.TOUCHSCREEN_ENABLED);
            desiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, CapabilityValues.SSL_CERTIFICATE_ENABLED);
            if (deviceid != null) {
                desiredCapabilities.setCapability("udid", deviceid);
            }
            String appname = System.getenv("APPNAME");
            if (null == appname) {
                desiredCapabilities.setCapability(MobileCapabilityType.APP, CapabilityValues.ANDROID_APP);
            } else {
                desiredCapabilities.setCapability(MobileCapabilityType.APP, System.getProperty("user.dir") + "/Data/configuration/flipkart.apk");
            }
           // desiredCapabilities.setCapability("appPackage", "com.myntra.android"); // This is package name of your app (you can get it from apk info app)
            //desiredCapabilities.setCapability("appActivity", "com.myntra.android.activities.LoginRegisterActivity");
            //desiredCapabilities.setCapability("appWaitActivity", "com.myntra.android.activities.LoginRegisterActivity");
        }
    }

    public static void startAppiumServer() {
        String sessionText;
        webDriver = new FirefoxDriver();
        webDriver.get("http://localhost:4723/wd/hub/status");
//		webDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        try {
            sessionText = webDriver.findElement(By.cssSelector("body>pre")).getText();
        } catch (NoSuchElementException e) {
            try {
                Runtime r = Runtime.getRuntime();
                System.out.print("**********************Appium is not started....Starting appium server");
                Process p = r.exec("cmd.exe /c " + System.getProperty("user.dir") + "/Data/configuration/appiumstarter.bat");
                p.waitFor();
                webDriver.navigate().refresh();
                webDriver.navigate().refresh();
                sessionText = webDriver.findElement(By.cssSelector("body>pre")).getText();
                if (sessionText != null) {
                    webDriver.quit();
                }

            } catch (Exception exp) {
                // TODO Auto-generated catch block
                exp.printStackTrace();
                System.out.print(exp.getMessage());
            }
        }
        System.out.println("Appium server started already");
        webDriver.quit();
        webDriver = null;
    }

    public void closeQwertyKeyboard() {
        new Actions(webDriver).sendKeys(SelendroidKeys.BACK).perform();
    }

    public void closeKeyBoard() {
        if (renderingdriver == RENDERINGDRIVER.AppAndroid) {

            //getAndriodDriver().sendKeyEvent(AndroidKeyCode.BACK);
            getAndriodDriver().hideKeyboard();
        } else {
            //TODO
        }
    }

    public static void navigateBacktoSearchScreenFrmCartPage() {
        nativeView();
        getAndriodDriver().navigate().back();
        scrollUp();
        scrollUp();
        getAndriodDriver().navigate().back();

    }


    public static void navigatetoBackScreen() {
        getAndriodDriver().navigate().back();
    }

    public static void storeADBLogs() throws IOException {
        nativeView();
        if (renderingdriver == RENDERINGDRIVER.AppAndroid || renderingdriver == RENDERINGDRIVER.AppSelendroid) {
            List<LogEntry> logEntries = getAndriodDriver().manage().logs().get("logcat").filter(Level.ALL);
            File file = new File("./target/adblogs.txt");
            if (file.exists()) {
                file.delete();
            }

            FileWriter out = new FileWriter(new File("./target/adblogs.txt"));
            BufferedWriter bw = new BufferedWriter(out);

            for (int i = 0; i < logEntries.size(); i++) {
                if (logEntries.get(i).getMessage().contains("com.myntra.android")) {
                    bw.write(logEntries.get(i).toString());
                    bw.write("\n");
                }
            }
            bw.close();
            out.close();
        }
    }

    public static AndroidDriver getAndriodDriver() {
        return androidDriver;
    }

    public static IOSDriver getIOSDriver() {
        return iosDriver;
    }
}


