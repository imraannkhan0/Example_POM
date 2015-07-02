
/**
 * Base class for all web pages. This class initializes the protected
 * {@link WebDriver} instance that moves across page objects during the course
 * of the test flow
 */

package org.example.test.webdriver;


import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;


import io.appium.java_client.MobileDriver;
import io.selendroid.SelendroidCapabilities;
import io.selendroid.device.DeviceTargetPlatform;
import net.lightbody.bmp.core.har.HarLog;
import net.lightbody.bmp.proxy.ProxyServer;

import org.example.test.Initialize;
import org.example.test.common.PageFactory;
import org.example.test.common.RENDERINGDRIVER;
import org.example.test.common.ScreenshotHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;

import com.google.common.base.Function;
import com.google.common.base.Throwables;
import org.testng.ITestResult;

public class WebPage {


    private String os;
    protected static WebDriver webDriver;
    private DesiredCapabilities desiredCapabilities;
    protected static ProxyServer proxyServer;
    protected static HarLog harLog;
    boolean isDesktop = false;
    String isNonJS = "false";

    private Initialize initialize;

    /**
     * This method can be used to run new functionalities of WebDriver which
     * might not be present in the framework.
     *
     * @return returns the internal WebDriver instance
     */
    public static WebDriver getWebDriver() {
        return webDriver;
    }

    /**
     * This constructor should take parameters specific to the browser being
     * used and use it to invoke the required browser using
     * {@link org.example.test.webdriver.WebDriverFactory}
     */
    public WebPage(Initialize initialize) {
        this.initialize = initialize;
        if (null == webDriver) {
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
            isNonJS = System.getProperty("isNonJS");
            System.out.println("isNonJS : " + isNonJS);
            String agent;
            int height = 640;
            int width = 360;

            // If platform does not come from Jenkins, pick it up from
            // Initialize
            if (null == platform) {
                platform = this.initialize.TestEnvironment.TestTarget
                        .getPlatform().toString().toLowerCase();
                System.out.println("Platform: " + platform);
            }
            switch (platform) {
                // Mobile Web Tests are done via a user agent
                case "mobile":
                    if (this.initialize.requireProxy) {
                        setupProxyCapabilities();
                    }
                    // If renderingDriver does not come from Jenkins, pick it from
                    // Initialize
                    if (null == renderingDriver) {
                        renderingDriver = this.initialize.TestEnvironment.TestTarget
                                .getRenderingDriver().toString();
                    }
                    RENDERINGDRIVER renderingdriver = RENDERINGDRIVER
                            .valueOf(renderingDriver);
                    switch (renderingdriver) {
                        case Stock:
                            desiredCapabilities = SelendroidCapabilities.android();
                            desiredCapabilities.setCapability(
                                    SelendroidCapabilities.EMULATOR, true);
                            try {
                                webDriver = WebDriverFactory.getSelendroidDriver(true,
                                        desiredCapabilities);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Throwables.propagateIfPossible(e);
                            }
                            break;
                        // RenderingDrivers not compatible with platform
                        case AppAndroid:

                        case AppIOS:
                        case AppWindows:
                        case Chrome:
                        case Firefox:
                        case IE:

                            Assert.fail("RenderingDriver not compatible on Platform. Platform : "
                                    + platform
                                    + " RenderingDriver : "
                                    + renderingDriver);
                            break;
                        case OperaMini:
                        case OperaMobile:
                        case MobileChrome:
                            //agent=renderingdriver.userAgent;
                            //startUpFirefoxDriverWithUserAgent(agent);
                            //	startUpChromeDriverWithUserAgent(agent);
                            //break;
                        case MobileAppWebView:
                            agent = renderingdriver.userAgent;
                            startUpFirefoxDriverWithUserAgent(agent);
                            break;
                        case MobileSafari:
                            //case MobileWebAndroid:
                            //case MobileWebIOS:
                            //case MobileWebWindows:
                            agent = renderingdriver.userAgent;
                            startUpFirefoxDriverWithUserAgent(agent);
                            break;
                        case MobileFirefox:
                            agent = renderingdriver.userAgent;
                            startUpFirefoxDriverWithUserAgent(agent);
                            break;
                        default:
                            if (null != isNonJS) {
                                renderingdriver = RENDERINGDRIVER.OperaMini;
                            } else {
                                renderingdriver = RENDERINGDRIVER.MobileSafari;
                            }

                            agent = renderingdriver.userAgent;
                            startUpFirefoxDriverWithUserAgent(agent);
                    }
                    webDriver.manage().window()
                            .setSize(new Dimension(width, height));
                    break;
                case "app":
                    if (null == renderingDriver) {
                        renderingDriver = this.initialize.TestEnvironment.TestTarget
                                .getRenderingDriver().toString();
                    }
                    switch (RENDERINGDRIVER.valueOf(renderingDriver)) {
                        case AppAndroid:
                            if (null == targetMobileApp) {
                                // Looking for something like this -
                                // io.selendroid.testapp:0.8.0
                                targetMobileApp = this.initialize.TestEnvironment.TestTarget
                                        .getTargetMobileApp();
                            }
                            desiredCapabilities = new SelendroidCapabilities(
                                    targetMobileApp);
                            // Device Logic should go in here
                            if (null == device) {
                                device = this.initialize.TestEnvironment.TestTarget
                                        .getDevice();
                            }
                            switch (device) {
                                case "android-std1":
                                    // Setting the name of an android emulator/device that
                                    // runs on API19
                                    ((SelendroidCapabilities) desiredCapabilities).
                                            setAndroidTarget(DeviceTargetPlatform.ANDROID19
                                                    .name());
                                    break;
                                case "android-std2":
                                    // Setting the name of an android emulator/device that
                                    // runs on API18
                                    ((SelendroidCapabilities) desiredCapabilities)
                                            .setAndroidTarget(DeviceTargetPlatform.ANDROID18
                                                    .name());
                                    break;
                                case "android-std3":
                                    // Setting the name of an android emulator/device that
                                    // runs on API17
                                    ((SelendroidCapabilities) desiredCapabilities)
                                            .setAndroidTarget(DeviceTargetPlatform.ANDROID17
                                                    .name());
                                    break;
                                default:
                                    // Default android device to be defined
                            }
                            try {
                                webDriver = WebDriverFactory.getSelendroidDriver(true,
                                        desiredCapabilities);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Throwables.propagateIfPossible(e);
                            }
                            break;
                        case AppIOS:
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
                case "desktop":
                    isDesktop = true;
                default:
                    if (this.initialize.requireProxy) {
                        setupProxyCapabilities();
                    }
                    if (null == renderingDriver) {
                        renderingDriver = this.initialize.TestEnvironment.TestTarget
                                .getRenderingDriver().toString();
                    }
                    switch (RENDERINGDRIVER.valueOf(renderingDriver)) {
                        case Firefox:
                            webDriver = WebDriverFactory.getFirefoxDriver(true,
                                    desiredCapabilities);
                            break;
                        case IE:
                            setupIECapabilities();
                            webDriver = WebDriverFactory.getInternetExplorerDriver(
                                    true, desiredCapabilities);
                            break;
					case PhantomJs:
						setupPhantomJsCapabilities();
						webDriver = WebDriverFactory.getPhantomJsDriver(true,
								desiredCapabilities);
						break;
                        case Safari:
                            Assert.fail("Safari Driver not implemented");
                            break;
                        case Opera:
                            Assert.fail("Opera Driver not implemented");
                            break;
                        // RenderingDrivers not compatible with platform
                        case AppAndroid:
                        case AppIOS:
                        case AppWindows:
                        case MobileChrome:
                        case MobileSafari:
                        case OperaMini:
                        case OperaMobile:
                            //case MobileWebAndroid:
                            //case MobileWebIOS:
                            //case MobileWebWindows:
                            Assert.fail("RenderingDriver not compatible on Platform. Platform : "
                                    + platform
                                    + " RenderingDriver : "
                                    + renderingDriver);
                            break;
                        case Chrome:
                        default:
                            setupChromeCapabilities();
                            webDriver = WebDriverFactory.getChromeDriver(true,
                                    desiredCapabilities);
                            break;
                    }
            }
            if (isDesktop) {
                webDriver.manage().window().maximize();
            }
        }
        System.out.println("====@@====Initializing "
                + this.getClass().getSimpleName() + "====@@====");
        PageFactory.initElements(webDriver, this, this.initialize);
    }

    private void setupIECapabilities() {
        if (null == desiredCapabilities) {
            desiredCapabilities = new DesiredCapabilities();
        }
        System.setProperty("webdriver.ie.driver",
                "Data/configuration/IEDriverServer.exe");
        desiredCapabilities.setCapability("webdriver.ie.driver",
                "Data/configuration/IEDriverServer.exe");
    }

    /**
     * Method which sets up a proxy server as {@link org.openqa.selenium.remote.DesiredCapabilities}
     */
    private void setupProxyCapabilities() {
        Proxy proxy = null;
        String PROXY = "localhost:1201";
        proxyServer = new ProxyServer(1201);
        try {
            proxyServer.start();
            proxyServer.setCaptureHeaders(true);
            proxyServer.setCaptureContent(true);
        } catch (Exception e) {
            Throwables.propagateIfPossible(e);
        }
        try {
            proxy = proxyServer.seleniumProxy();
        } catch (UnknownHostException e) {
            Throwables.propagateIfPossible(e);
        }
        proxy.setHttpProxy(PROXY).setFtpProxy(PROXY).setSslProxy(PROXY);
        if (null == desiredCapabilities) {
            desiredCapabilities = new DesiredCapabilities();
        }
        desiredCapabilities.setCapability(CapabilityType.PROXY, proxy);
        desiredCapabilities
                .setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        desiredCapabilities.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT,
                true);
    }

    private void setupChromeCapabilities() {
        if (null == desiredCapabilities) {
            desiredCapabilities = new DesiredCapabilities();
        }
        if (os.contains("Win")) {
            System.setProperty("webdriver.chrome.driver",
                    "Data/configuration/chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("test-type");
            // System.out.println("[DEBUG] : disabling test-type");
            desiredCapabilities
                    .setCapability(ChromeOptions.CAPABILITY, options);
            desiredCapabilities.setCapability("webdriver.chrome.driver",
                    "Data/configuration/chromedriver.exe");
        } else if (os.contains("Mac")) {
            System.setProperty("webdriver.chrome.driver",
                    "Data/configuration/chromedriver-mac");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("test-type");
            desiredCapabilities
                    .setCapability(ChromeOptions.CAPABILITY, options);
            desiredCapabilities.setCapability("webdriver.chrome.driver",
                    "Data/configuration/chromedriver-mac");
        } else {
            System.setProperty("webdriver.chrome.driver",
                    "Data/configuration/chromedriver-linux");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("test-type");
            desiredCapabilities
                    .setCapability(ChromeOptions.CAPABILITY, options);
            desiredCapabilities.setCapability("webdriver.chrome.driver",
                    "Data/configuration/chromedriver-linux");
        }

    }

    private void setupPhantomJsCapabilities() {
        if (null == desiredCapabilities) {
            desiredCapabilities = new DesiredCapabilities();
        }
        desiredCapabilities.setJavascriptEnabled(true);
        if (os.contains("Win")) {
            desiredCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "./Data/configuration/phantomjs.exe");
        } else if (os.contains("Mac")) {
            desiredCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "./Data/configuration/phantomjs-mac");
        } else {
            desiredCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "./Data/configuration/phantomjs-linux");
        }
    }

    private void startUpFirefoxDriverWithUserAgent(String agentString) {
        System.out.println("Starting firefox with useragent: " + agentString);
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        firefoxProfile.setPreference("general.useragent.override", agentString);
        if (null != isNonJS) {
            firefoxProfile.setPreference("javascript.enabled", false);
        }
        if (null == desiredCapabilities) {
            desiredCapabilities = new DesiredCapabilities();
        }
        desiredCapabilities
                .setCapability(FirefoxDriver.PROFILE, firefoxProfile);
        webDriver = WebDriverFactory
                .getFirefoxDriver(true, desiredCapabilities);
        // Use of the next line?
        webDriver.manage().deleteAllCookies();
    }

    private void startUpChromeDriverWithUserAgent(String agentString) {

        if (null == desiredCapabilities) {
            desiredCapabilities = new DesiredCapabilities();
        }
        if (os.contains("Win")) {
            System.setProperty("webdriver.chrome.driver",
                    "Data/configuration/chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("test-type");
            options.addArguments("--user-agent=" + agentString);
            // System.out.println("[DEBUG] : disabling test-type");
            desiredCapabilities
                    .setCapability(ChromeOptions.CAPABILITY, options);
            desiredCapabilities.setCapability("webdriver.chrome.driver",
                    "Data/configuration/chromedriver.exe");
            webDriver = WebDriverFactory
                    .getChromeDriver(true, desiredCapabilities);
            webDriver.manage().deleteAllCookies();
        } else if (os.contains("Mac")) {
            System.setProperty("webdriver.chrome.driver",
                    "Data/configuration/chromedriver-mac");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("test-type");
            options.addArguments("--user-agent=" + agentString);
            desiredCapabilities
                    .setCapability(ChromeOptions.CAPABILITY, options);
            desiredCapabilities.setCapability("webdriver.chrome.driver",
                    "Data/configuration/chromedriver-mac");
            webDriver = WebDriverFactory
                    .getChromeDriver(true, desiredCapabilities);
            webDriver.manage().deleteAllCookies();
        } else if (os.toLowerCase().contains("linux")) {
            System.setProperty("webdriver.chrome.driver",
                    "Data/configuration/chromedriver-linux");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("test-type");
            options.addArguments("--user-agent=" + agentString);
            desiredCapabilities
                    .setCapability(ChromeOptions.CAPABILITY, options);
            options.addArguments("--user-agent=" + agentString);
            desiredCapabilities.setCapability("webdriver.chrome.driver",
                    "Data/configuration/chromedriver-linux");
            webDriver = WebDriverFactory
                    .getChromeDriver(true, desiredCapabilities);
            webDriver.manage().deleteAllCookies();
        } else {
            Assert.fail("Failed to start chrome driver or driver not present");
        }


    }

    public Object executeJavascript(String ScripttoExecute) {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        return js.executeScript(ScripttoExecute);
    }

    public static void endTest() {
        if (null != proxyServer) {
            try {
                proxyServer.stop();
            } catch (Exception e) {
                Throwables.propagateIfPossible(e);
            }
        }
        if (null != webDriver) {
            //ScreenshotHelper.takeScreenShot(webDriver);
            webDriver.close();
            webDriver = null;
        }

    }

    public void closeWebEngageIframeWindow() {
        webDriver.switchTo().frame(
                "webklipper-publisher-widget-container-notification-frame");
        webDriver.findElement(By.cssSelector(".close")).click();
        webDriver.switchTo().defaultContent();
    }

    public static void switchToIframeWindow(String css) {
        webDriver.switchTo().frame(css);
    }

    public static void switchToParentFrame() {
        webDriver.switchTo().defaultContent();
    }

    public void waitForThrobber(String css) {
        new FluentWait<String>(css).withTimeout(30, TimeUnit.SECONDS)
                .pollingEvery(500, TimeUnit.MILLISECONDS)
                .withMessage("Throbber taking too long to vanish!")
                .until(new Function<String, Boolean>() {
                    public Boolean apply(String css) {
                        boolean isPresentOrVisible = true;
                        System.out
                                .println("Checking if throbber has vanished - "
                                        + css);
                        try {
                            isPresentOrVisible = webDriver.findElement(
                                    By.cssSelector(css)).isDisplayed();
                        } catch (WebDriverException e) {
                            isPresentOrVisible = false;
                            System.out.println(e.getMessage());
                        }
                        return !isPresentOrVisible;
                    }
                });
    }

    public void waitForThrobberForXpath(String xpath) {
        new FluentWait<String>(xpath).withTimeout(30, TimeUnit.SECONDS)
                .pollingEvery(500, TimeUnit.MILLISECONDS)
                .withMessage("Throbber taking too long to vanish!")
                .until(new Function<String, Boolean>() {
                    public Boolean apply(String xpath) {
                        boolean isPresentOrVisible = true;
                        System.out
                                .println("Checking if throbber has vanished - "
                                        + xpath);
                        try {
                            isPresentOrVisible = webDriver.findElement(
                                    By.xpath(xpath)).isDisplayed();
                        } catch (WebDriverException e) {
                            isPresentOrVisible = false;
                            System.out.println(e.getMessage());
                        }
                        return !isPresentOrVisible;
                    }
                });
    }

    public void waitForThrobberOnMobile(String css) {
        new FluentWait<String>(css).withTimeout(30, TimeUnit.SECONDS)
                .pollingEvery(500, TimeUnit.MILLISECONDS)
                .withMessage("Throbber taking too long to vanish!")
                .until(new Function<String, Boolean>() {
                    @Override
                    public Boolean apply(String css) {
                        boolean isPresentOrVisible = true;
                        System.out
                                .println("Checking if throbber has vanished - "
                                        + css);
                        try {
                            isPresentOrVisible = webDriver.findElement(
                                    By.cssSelector(css)).isDisplayed();
                        } catch (WebDriverException e) {
                            isPresentOrVisible = false;
                            System.out.println(e.getMessage());
                        }
                        return !isPresentOrVisible;
                    }
                });
    }

    public void switchToPopUp() {
        String mainWindowHandle = webDriver.getWindowHandle();
        Set<String> allWindowHandles = webDriver.getWindowHandles();
        Iterator<String> ite = allWindowHandles.iterator();
        String singleHandle;
        while (ite.hasNext()) {
            singleHandle = ite.next().toString();
            if (!singleHandle.equals(mainWindowHandle)) {
                webDriver.switchTo().window(singleHandle);
            }
        }
    }

    public static void scroll(int x, int y) {
        TouchActions scroll = new TouchActions(webDriver).scroll(x, y);
        scroll.perform();
    }

    public static void hitUrl(String url) {
        webDriver.get(url);
    }

    public static String getHtmlTagFromApi(String uniqueHtmlTag) {
        return webDriver.getPageSource().split(uniqueHtmlTag)[1];
    }

    /**
     * Use this method to setup a new reference while extracting har files. This
     * can be used as a filter if the HAR files is huge
     *
     * @param pageRef any string which can be used later for filtering
     */
    public void setNewPageRef(String pageRef) {
        if (null != proxyServer) {
            proxyServer.newHar(pageRef);
        }
    }

    /**
     * Use this method to feed {@link //HttpArchiveHelper} with the log which can
     * then be filtered
     *
     * @return the HAR file which can be filtered
     */
    public HarLog getHarLog() {
        if (null != proxyServer) {
            harLog = null;
            harLog = proxyServer.getHar().getLog();
        }
        return harLog;
    }

    public void pauseForSomeTime() {
        //new MyntActions(webDriver).pause(3000).perform();
    }

    public void webPageRefresh() {
        webDriver.navigate().refresh();
    }

    public void alertAccept() {
        new FluentWait<WebDriver>(webDriver).
                ignoring(NoAlertPresentException.class).
                withTimeout(10, TimeUnit.SECONDS).
                pollingEvery(2, TimeUnit.SECONDS).
                withMessage("Alert not present").
                until(ExpectedConditions.alertIsPresent());
        webDriver.switchTo().alert().accept();

    }

    public void alertDismiss(){
         new FluentWait<WebDriver>(webDriver).
        ignoring(NoAlertPresentException.class).
        withTimeout(10, TimeUnit.SECONDS).
        pollingEvery(2, TimeUnit.SECONDS).
        withMessage("Alert not present").
        until(ExpectedConditions.alertIsPresent());

        webDriver.switchTo().alert().dismiss();

    }

    public static String getCurrentUrl() {
        return webDriver.getCurrentUrl();
    }


}


