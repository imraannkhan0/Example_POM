package org.example.test.webdriver;

import net.lightbody.bmp.core.har.HarLog;
import net.lightbody.bmp.proxy.ProxyServer;
import org.example.test.Initialize;
import org.example.test.common.RENDERINGDRIVER;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Sabyasachi on 24/6/2015.
 */
public class RemoteWebDriverWebPage {
    private static ThreadLocal<RemoteWebDriver> remoteWebDriver = null;
    public static WebDriver webDriver;

    private DesiredCapabilities desiredCapabilities;
    protected static ProxyServer proxyServer;
    protected static HarLog harLog;
    boolean isDesktop = false;
    String isNonJS = "false";
    private String os;

    private Initialize initialize;

    public static WebDriver getWebDriver() {
        return webDriver;
    }

    public RemoteWebDriverWebPage(Initialize initialize) throws MalformedURLException {
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

            if (null == platform) {
                platform = this.initialize.TestEnvironment.TestTarget
                        .getPlatform().toString().toLowerCase();
                System.out.println("Platform: " + platform);
            }

            switch (platform) {

                case "desktop":
                    isDesktop = true;
                default:
                    if (null == renderingDriver) {
                        renderingDriver = this.initialize.TestEnvironment.TestTarget
                                .getRenderingDriver().toString();
                    }
                    switch (RENDERINGDRIVER.valueOf(renderingDriver)) {

                        case Firefox:
                            setUpRemoteWebDriverFirefoxCapabilities();
                            remoteWebDriver = new ThreadLocal<RemoteWebDriver>();
                            remoteWebDriver.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), desiredCapabilities));


                    }


            }

        }
    }

    private void setUpRemoteWebDriverFirefoxCapabilities() {

        if (null == desiredCapabilities) {
            desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setPlatform(Platform.ANY);
            desiredCapabilities.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
        }

    }

    public WebDriver getDriver() {
        return remoteWebDriver.get();
    }

}


