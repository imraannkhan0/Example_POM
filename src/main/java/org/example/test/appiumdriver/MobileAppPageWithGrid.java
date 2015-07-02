package org.example.test.appiumdriver;

import com.google.common.base.Function;
import com.google.common.base.Throwables;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import net.lightbody.bmp.core.har.HarLog;
import org.example.test.Initialize;
import org.example.test.common.PageFactory;
import org.example.test.common.RENDERINGDRIVER;
import org.example.test.gridImplementation.ThreadManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ThreadGuard;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * Created by Sabyasachi on 22/6/2015.
 */
public class MobileAppPageWithGrid extends ThreadManager {
    private static String os;
    protected static WebDriver webDriver;
    private static DesiredCapabilities desiredCapabilities;
    protected static HarLog harLog;
    boolean isDesktop = false;

    private Initialize initialize;
    protected static RENDERINGDRIVER renderingdriver;
    protected static AndroidDriver androidDriver;

    protected static IOSDriver iosDriver;

    private static final String TESTDROID_USERNAME = "sreehari.mohan@myntra.com";
    private static final String TESTDROID_PASSWORD = "qwertyui";
    private static final String TESTDROID_SERVER = "http://appium.testdroid.com";

    protected static Map<String, AndroidDriver> driverlist = new HashMap<String, AndroidDriver>();
    public static ThreadManager threadManager = new ThreadManager();
    protected static String tmp;

    public MobileAppPageWithGrid(Initialize initialize) {
        this.initialize = initialize;
        if (initialize.TestEnvironment.TestTarget.ParallelRuns) {
            tmp = initialize.TestEnvironment.TestTarget.TargetMobileApp.toString();
            System.out.println(tmp + " " + driverlist.containsKey(tmp));
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
            if (null == renderingDriver) {
                renderingDriver = this.initialize.TestEnvironment.TestTarget
                        .getRenderingDriver().toString();
            }
            renderingdriver = RENDERINGDRIVER
                    .valueOf(renderingDriver);
            setupAndroidAppCapabilities();
            try {
                androidDriver = new AndroidDriver(new URL("http://127.0.0.1:6666/wd/hub"), desiredCapabilities);
                ThreadGuard.protect(androidDriver);
                threadManager.setAndroidDriver(androidDriver);
            } catch (Exception e) {
                e.printStackTrace();
                Throwables.propagateIfPossible(e);
            }
            initialize.TestEnvironment.TestTarget.ParallelRuns = false;
            initialize.TestEnvironment.TestTarget.TargetMobileApp = "";
        } else {
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
                        String AppiumPort = System.getenv("AppiumPort");
                        System.out.println("AppiumPort : " + AppiumPort);
                        switch (renderingdriver) {
                            case AppAndroid:
                                startAppiumServer();
                                setupAndroidAppCapabilities();
                                try {
                                    if (AppiumPort == null) {
                                        androidDriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), desiredCapabilities);

                                    } else {
                                        androidDriver = new AndroidDriver(new URL("http://127.0.0.1:" + AppiumPort + "/wd/hub"), desiredCapabilities);
                                    }
                                    Thread.sleep(5000);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Throwables.propagateIfPossible(e);
                                }
                                break;
                            case AppSelendroid:
                                startAppiumServer();
                                String jenkinJobName = System.getenv("jenkinsJobName");
                                System.out.println("JenkinsJobName : " + jenkinJobName);
                                if (jenkinJobName == null) {
                                    jenkinJobName = "android";
                                }
                                if (jenkinJobName.toLowerCase().contains("testdroid")) {
                                    setupTestdroidAppCapabilities();
                                    try {
                                        androidDriver = new AndroidDriver(new URL(TESTDROID_SERVER + "/wd/hub"), desiredCapabilities);
                                        Thread.sleep(5000);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Throwables.propagateIfPossible(e);
                                    }
                                } else {
                                    setupAndroidAppCapabilities();
                                    System.out.println("did not happen");
                                    try {
                                        if (AppiumPort == null) {
                                            androidDriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), desiredCapabilities);
                                        } else {
                                            androidDriver = new AndroidDriver(new URL("http://127.0.0.1:" + AppiumPort + "/wd/hub"), desiredCapabilities);
                                        }
                                        Thread.sleep(5000);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Throwables.propagateIfPossible(e);
                                    }
                                }
                                break;
                            case AppIOS:
                                startAppiumServer();
                                setupIosAppCapabilities();
                                try {
                                    if (AppiumPort == null) {
                                        iosDriver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), desiredCapabilities);
                                    } else {
                                        iosDriver = new IOSDriver(new URL("http://127.0.0.1:" + AppiumPort + "/wd/hub"), desiredCapabilities);
                                    }

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
        }
        if (renderingdriver == RENDERINGDRIVER.AppAndroid || renderingdriver == RENDERINGDRIVER.AppSelendroid) {
            PageFactory.initElements(getAndriodDriver(), this, this.initialize);
        } else {
            PageFactory.initElements(getIOSDriver(), this, this.initialize);
        }
    }

    public static void endTest(String devicename) {
        try {
            storeADBLogs(devicename);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (null != threadManager.getAndroidDriver()) {
//			ScreenshotHelper.takeScreenShot(appiumDriver);
//			driverlist.get(simname).quit();
            threadManager.getAndroidDriver().quit();
        } else {
            getIOSDriver().quit();
        }
    }

    public static void closeApp() {
        if (null != getAndriodDriver()) {
            getAndriodDriver().quit();
            androidDriver = null;
        }

    }


    public static void scrollDown() {
        if (renderingdriver == RENDERINGDRIVER.AppAndroid) {

//		   getAndriodDriver().swipe(400, 1100, 400, 300, 1200);
        } else if (renderingdriver == RENDERINGDRIVER.AppIOS) {
            getIOSDriver().swipe(10, 497, 320, 100, 1000);

        }

    }

    public static void pressBackButtonOfDevice() {
//		getAndriodDriver().sendKeyEvent(4);

    }

    public static void scrollUp() {
        if (renderingdriver == RENDERINGDRIVER.AppAndroid) {
//			getAndriodDriver().swipe(400, 300, 400, 1100, 1200);
//			WebElement ele14 = getAndriodDriver().findElement(By.xpath("//*[@id='tv_pdp_description']"));
//			TouchActions flick = new TouchActions(getAndriodDriver()).flick(ele14, 0, -100, 100);
//			flick.perform();
        } else if (renderingdriver == RENDERINGDRIVER.AppIOS) {
            getIOSDriver().swipe(75, 500, 75, 100, 1200);
        }
    }

    public WebElement fluentFinder(final By by) {
        return new FluentWait<AppiumDriver>(getAndriodDriver(null))
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
        return new FluentWait<AppiumDriver>(getAndriodDriver(null))
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
//		try
//		{
//			Thread.sleep(5000);
//		} catch (InterruptedException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        Set<String> lst = new HashSet<>();
        String contextid = null;
        String temp = null;
        if (renderingdriver == RENDERINGDRIVER.AppAndroid || renderingdriver == RENDERINGDRIVER.AppSelendroid) {
//		    lst =driverlist.get().getContextHandles();
            lst = threadManager.getAndroidDriver().getContextHandles();
            Iterator<String> it = lst.iterator();
            while (it.hasNext()) {
                contextid = it.next();
                if (contextid.contains("WEBVIEW")) {
                    temp = contextid;
                }
            }
//			   driverlist.get(simname).context(temp);
            threadManager.getAndroidDriver().context(temp);

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
//			driverlist.get(simname).context("NATIVE_APP");
            threadManager.getAndroidDriver().context("NATIVE_APP");
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
        // new MyntActions(webDriver).pause(3000).perform();
    }


    public static String getCurrentUrl() {
        return webDriver.getCurrentUrl();
    }

    private void setupIosAppCapabilities() {
        String deviceid = System.getenv("deviceid");
        System.out.println(deviceid);
        if (null == desiredCapabilities) {
            desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
            desiredCapabilities.setCapability(CapabilityType.HAS_TOUCHSCREEN, true);
            desiredCapabilities.setCapability("newCommandTimeout", "500");
            desiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            desiredCapabilities.setCapability("automationName", "Appium");
            desiredCapabilities.setCapability("browserName", "");
            desiredCapabilities.setCapability("autoLaunch", true);
//		 	desiredCapabilities.setCapability("appium-version", "1.0");
            desiredCapabilities.setCapability("platformName", "iOS");
            desiredCapabilities.setCapability("platformVersion", "8.1");
//			desiredCapabilities.setCapability("deviceName", "iPhone 5s (8.1 Simulator) [2C0E77E7-4BF7-4B25-860E-FFB17CD79B3B]");//For Local testing
            if (null == deviceid) {
                desiredCapabilities.setCapability("deviceName", "iPhone 5s (8.1 Simulator) [42FD0559-5276-4B7E-BE71-4283A8FFCFE4]");
            } else {
                desiredCapabilities.setCapability("deviceName", deviceid);
            }
//			desiredCapabilities.setCapability("app", "/Users/c.sivasubramanian/Library/Developer/Xcode/DerivedData/Myntra-chdkpohusyvxplcsagsomxhzwvnp/Build/Products/Debug-iphonesimulator/Myntra.app");
            desiredCapabilities.setCapability("app", System.getProperty("user.dir") + "/Data/configuration/Myntra.app");
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
                desiredCapabilities.setCapability("automationName", "selendroid");
            } else {
                desiredCapabilities.setCapability("automationName", "Appium");
            }
            desiredCapabilities.setCapability("platformName", "Android");
            desiredCapabilities.setCapability("deviceType", "phone");
            desiredCapabilities.setCapability("deviceName", "Android Emulator");
            desiredCapabilities.setCapability(CapabilityType.VERSION, "4.3");
            if (os.contains("Windows")) {
                desiredCapabilities.setCapability(CapabilityType.PLATFORM, "WINDOWS");

            } else {

                desiredCapabilities.setCapability(CapabilityType.PLATFORM, "MAC");

            }
            desiredCapabilities.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
            desiredCapabilities.setCapability(CapabilityType.HAS_TOUCHSCREEN, true);
            desiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            if (deviceid != null) {
                desiredCapabilities.setCapability("udid", deviceid);
            }
            String appname = System.getenv("APPNAME");
            if (null == appname) {
                desiredCapabilities.setCapability("app", System.getProperty("user.dir") + "/Data/configuration/Myntra-Android-release202.apk");
            } else {
                desiredCapabilities.setCapability("app", System.getProperty("user.dir") + "/Data/configuration/" + appname + ".apk");
            }
            desiredCapabilities.setCapability("appPackage", "com.myntra.android"); // This is package name of your app (you can get it from apk info app)
            desiredCapabilities.setCapability("appActivity", "com.myntra.android.activities.LoginRegisterActivity");
            desiredCapabilities.setCapability("appWaitActivity", "com.myntra.android.activities.LoginRegisterActivity");
        }
    }

    private void setupTestdroidAppCapabilities() {
        String TestdroidApp = System.getenv("TesdroidApp");
        System.out.println("TestdroidApp : " + TestdroidApp);
        String TestdroidDevice = System.getenv("deviceid");
        System.out.println("TestdroidDevice : " + TestdroidDevice);
        String Testrun = System.getenv("Build");
        System.out.println("Testrun : " + Testrun);
        if (null == desiredCapabilities) {
            desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability("platformName", "Android");
            desiredCapabilities.setCapability("newCommandTimeout", "500");
            desiredCapabilities.setCapability("platformVersion", "4.4");
            desiredCapabilities.setCapability("app-package", "com.myntra.android");
            desiredCapabilities.setCapability("app-activity", "com.myntra.android.activities.LoginRegisterActivity");
            desiredCapabilities.setCapability("device", "android");
            desiredCapabilities.setCapability("deviceName", "Android Phone");
            desiredCapabilities.setCapability("automationName", "selendroid");
            desiredCapabilities.setCapability("testdroid_username", TESTDROID_USERNAME);
            desiredCapabilities.setCapability("testdroid_password", TESTDROID_PASSWORD);
            desiredCapabilities.setCapability("testdroid_project", "LocalAppium");
            desiredCapabilities.setCapability("testdroid_description", "Appium project description");
            if (Testrun == null) {
                desiredCapabilities.setCapability("testdroid_testrun", "Android Run 7");
            } else {
                desiredCapabilities.setCapability("testdroid_testrun", Testrun);
            }
            if (TestdroidDevice == null) {
                desiredCapabilities.setCapability("testdroid_device", "Asus Fonepad ME371MG");
            } else {
                desiredCapabilities.setCapability("testdroid_device", TestdroidDevice);
            }
            if (TestdroidApp == null) {
                desiredCapabilities.setCapability("testdroid_app", "9c645538-fef5-4361-bde2-267862a9e192/Myntra-Android-release154.apk"); //to use existing app using "latest" as fileUUID
            } else {
                desiredCapabilities.setCapability("testdroid_app", TestdroidApp); //to use existing app using "latest" as fileUUID
            }

            desiredCapabilities.setCapability("testdroid_target", "Android");
            if (os.contains("Windows")) {
                desiredCapabilities.setCapability(CapabilityType.PLATFORM, "WINDOWS");
            } else {
                desiredCapabilities.setCapability(CapabilityType.PLATFORM, "Mac");
            }
            desiredCapabilities.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
            desiredCapabilities.setCapability(CapabilityType.HAS_TOUCHSCREEN, true);
            desiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            desiredCapabilities.setCapability("appWaitActivity", "com.myntra.android.activities.LoginRegisterActivity");
            System.out.println("Capabilities:" + desiredCapabilities.toString());
        }
    }

    public static void startAppiumServer() {
        String os = System.getProperty("os.name");
        String sessionText = null;
        webDriver = new FirefoxDriver();
        String AppiumPort = System.getenv("AppiumPort");
        System.out.println("AppiumPort : " + AppiumPort);
        if (AppiumPort == null) {
            webDriver.get("http://localhost:4723/wd/hub/status");
        } else {
            webDriver.get("http://localhost:" + AppiumPort + "/wd/hub/status");
        }

//		webDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        try {
            sessionText = webDriver.findElement(By.cssSelector("body>pre")).getText();
        } catch (NoSuchElementException e) {
            try {
                if (os.toLowerCase().contains("windows")) {
                    Runtime r = Runtime.getRuntime();
                    System.out.print("**********************Appium is not started....Starting appium server");
                    Process p = r.exec("cmd.exe /c " + System.getProperty("user.dir") + "/Data/configuration/appiumstarter.bat");
                    p.waitFor();
                    webDriver.navigate().refresh();
                    webDriver.navigate().refresh();
                    sessionText = webDriver.findElement(By.cssSelector("body>pre")).getText();
                } else if (os.toLowerCase().contains("nix") || os.toLowerCase().contains("nux")) {

                }
                if (sessionText != null) {
                    webDriver.quit();
                }

            } catch (IOException | InterruptedException exp) {
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
//		new Actions(webDriver).sendKeys(SelendroidKeys.BACK).perform();
    }

    public static void navigateBacktoSearchScreenFrmCartPage() {
//		nativeView();
//		getAndriodDriver().navigate().back();
//		scrollUp();
//		scrollUp();
//		getAndriodDriver().navigate().back();

    }


    public static void navigatetoBackScreen() {
//		getAndriodDriver().navigate().back();
    }

    public synchronized static void storeADBLogs(String devicename) throws IOException {
        nativeView();
        if (renderingdriver == RENDERINGDRIVER.AppAndroid || renderingdriver == RENDERINGDRIVER.AppSelendroid) {
            List<LogEntry> logEntries = getAndriodDriver().manage().logs().get("logcat").filter(Level.ALL);
            File file = new File("./target/adblogs_" + devicename + ".txt");


            FileWriter out = new FileWriter(new File("./target/adblogs_" + devicename + ".txt"));
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
        return threadManager.getAndroidDriver();
    }

    public static AndroidDriver getAndriodDriver(String simname) {
        return driverlist.get(simname);
//		return androidDriver;
    }

    public static IOSDriver getIOSDriver() {
        return iosDriver;
    }


    public static void upgrade(String appname) {
        // TODO Auto-generated method stub
//		getAndriodDriver().quit();
//		runShellCommand(System.getenv("ANDROID_HOME")+"/platform-tools/adb install -r "+System.getProperty("user.dir")+"/Data/configuration/"+appname+".apk");
    }

    public static void uninstallAndInstall(String appname) {
        //getAndriodDriver().quit();
        runShellCommand("/Users/manu.chadha/Library/Android/sdk/" + "/platform-tools/adb uninstall com.myntra.android");
        //runShellCommand("/Users/manu.chadha/Library/Android/sdk/"+"/platform-tools/adb install -r "+System.getProperty("user.dir")+"/Data/configuration/"+appname+".apk");
        //runShellCommand("/Users/manu.chadha/Library/Android/sdk/"+"/platform-tools/adb shell am start -n com.myntra.android/com.myntra.android.activities.LoginRegisterActivity");
    }


    public static void runShellCommand(String command) {

        String s = null;
        try {

            // run the Unix "ps -ef" command
            // using the Runtime exec method:
            Process p = Runtime.getRuntime().exec(command);

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));

            // read the output from the command
            System.out.println("Here is the standard output of the command:\n" + command + "\n");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            // read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }


        } catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static void clear() {
        desiredCapabilities = null;
        androidDriver = null;
        iosDriver = null;
    }

    public static boolean apsalarEvents(HashMap<String, ArrayList<Object>> results, String check) {
        System.out.println("apsalar events");
//		Iterator<HashMap.Entry<String, ArrayList<Object>>> it = results.entrySet().iterator();
//	    while (it.hasNext()) {
//	        HashMap.Entry<String, ArrayList<Object>> pair = it.next();
//	        System.out.println(pair.getKey() + " = " + pair.getValue());
//	        it.remove(); // avoids a ConcurrentModificationException
//	    }
        boolean event = false;
        for (Map.Entry<String, ArrayList<Object>> entry : results.entrySet()) {
            if (entry.getKey().toString().contains(check)) {
                event = true;
                break;
            }
            System.out.println(entry.getKey() + "/n");
        }
        return event;

    }

    public static void createAppiumSession(Initialize initialize) {
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

            String AppiumPort = System.getenv("AppiumPort");
            System.out.println("AppiumPort : " + AppiumPort);
            String deviceid = System.getenv("deviceid");
            System.out.println(deviceid);
            if (null == desiredCapabilities) {
                desiredCapabilities = new DesiredCapabilities();
                desiredCapabilities.setCapability("browserName", "");
                desiredCapabilities.setCapability("newCommandTimeout", "500");
                if (renderingdriver == RENDERINGDRIVER.AppSelendroid) {
                    desiredCapabilities.setCapability("automationName", "selendroid");
                } else {
                    desiredCapabilities.setCapability("automationName", "Appium");
                }
                desiredCapabilities.setCapability("platformName", "Android");
                desiredCapabilities.setCapability("deviceType", "phone");
                desiredCapabilities.setCapability("deviceName", "Android Emulator");
                desiredCapabilities.setCapability(CapabilityType.VERSION, "4.3");
                if (os.contains("Windows")) {
                    desiredCapabilities.setCapability(CapabilityType.PLATFORM, "WINDOWS");

                } else {

                    desiredCapabilities.setCapability(CapabilityType.PLATFORM, "Mac");

                }
                desiredCapabilities.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
                desiredCapabilities.setCapability(CapabilityType.HAS_TOUCHSCREEN, true);
                desiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                if (deviceid != null) {
                    desiredCapabilities.setCapability("udid", deviceid);
                }
                String appname = System.getenv("APPNAME");
                if (null == appname) {
                    desiredCapabilities.setCapability("app", System.getProperty("user.dir") + "/Data/configuration/Myntra-Android-release156.apk");
                } else {
                    desiredCapabilities.setCapability("app", System.getProperty("user.dir") + "/Data/configuration/" + appname + ".apk");
                }
                desiredCapabilities.setCapability("appPackage", "com.myntra.android"); // This is package name of your app (you can get it from apk info app)
                desiredCapabilities.setCapability("appActivity", "com.myntra.android.activities.MainActivity");
                desiredCapabilities.setCapability("appWaitActivity", "com.myntra.android.activities.MainActivity");
            }
            try {
                if (AppiumPort == null) {
                    androidDriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), desiredCapabilities);
                } else {
                    androidDriver = new AndroidDriver(new URL("http://127.0.0.1:" + AppiumPort + "/wd/hub"), desiredCapabilities);
                }
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
                Throwables.propagateIfPossible(e);
            }
        }

    }

    public static void endTest() {
        try {
            storeADBLogs();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (null != threadManager.getAndroidDriver()) {
//			ScreenshotHelper.takeScreenShot(appiumDriver);
//			driverlist.get(simname).quit();
            threadManager.getAndroidDriver().quit();
        } else {
            getIOSDriver().quit();
        }
    }

    public synchronized static void storeADBLogs() throws IOException {
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
}
