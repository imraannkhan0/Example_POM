package org.example.test.common;

import io.appium.java_client.AppiumDriver;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.example.test.Initialize;
import org.example.test.javascript.JsBy;
import org.example.test.javascript.JsElement;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.Annotations;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;

import com.google.common.base.Function;


public class ElementLocator {


    private final WebDriver webDriver;
    private final AppiumDriver appiumDriver;
    private final boolean shouldCache;
    private By by;
    private JsBy jsBy;
    private final ElementIdentificationType elementIdentificationType;
    private IElement cachedElement;
    private List<IElement> cachedElementList;
    private final String SPLITTER = "@#@";

    public ElementLocator(WebDriver webDriver, AppiumDriver appiumDriver,
                          Field field, Initialize initialize) {
        ElementIdentificationType type = null;
        String data = "";
        this.webDriver = webDriver;
        this.appiumDriver = appiumDriver;
        Annotations annotations = new Annotations(field);
        shouldCache = annotations.isLookupCached();

//		log.info(field.getDeclaringClass().getSimpleName() + "."
//				+ field.getName());

        // Setting type
        try {
            // System.out.println("initialize = " +
            // initialize.UIObjects.GetUIElementDetails(field.getDeclaringClass().getName()));
            // System.out.println("initialize data = "
            // + field.getDeclaringClass().getSimpleName());
            type = initialize.UIObjects.GetUIElementDetails(field
                    .getDeclaringClass().getSimpleName()
                    + "."
                    + field.getName()).type;
        } catch (NullPointerException npe) {
            Assert.fail("Issue with "
                    + field.getDeclaringClass().getSimpleName() + "."
                    + field.getName());
        }
        switch (type) {
            case JS:
                // Reading node from the XML
                data = initialize.UIObjects.GetUIElementDetails(field
                        .getDeclaringClass().getSimpleName()
                        + "."
                        + field.getName()).value;
                elementIdentificationType = ElementIdentificationType.JS;
                switch (data.split(SPLITTER)[0]) {
                    case "className":
                        jsBy = JsBy.className(data.split(SPLITTER)[1]);
                        break;
                    case "id":
                        jsBy = JsBy.id(data.split(SPLITTER)[1]);
                        break;
                    case "name":
                        jsBy = JsBy.name(data.split(SPLITTER)[1]);
                        break;
                    case "tagName":
                        jsBy = JsBy.tagName(data.split(SPLITTER)[1]);
                        break;
                    case "tagNameNs":
                        jsBy = JsBy.tagNameNs(data.split(SPLITTER)[1]);
                        break;
                    default:
                        jsBy = JsBy.attributeValue(data);
                }
                break;
            case WEBDRIVER:
                // Reading node from the XML
                if (initialize.TestEnvironment.TestTarget.RenderingDriver
                        .toString().equalsIgnoreCase("appAndroid")) {
                    data = initialize.UIObjects.GetUIElementDetails(field
                            .getDeclaringClass().getSimpleName()
                            + "."
                            + field.getName()).value;
                } else if (initialize.TestEnvironment.TestTarget.RenderingDriver
                        .toString().equalsIgnoreCase("appIOS")) {
                    data = initialize.UIObjects.GetUIElementDetails(field
                            .getDeclaringClass().getSimpleName()
                            + "."
                            + field.getName()).jvalue;
                } else {
                    data = initialize.UIObjects.GetUIElementDetails(field
                            .getDeclaringClass().getSimpleName()
                            + "."
                            + field.getName()).value;
                }
                elementIdentificationType = ElementIdentificationType.WEBDRIVER;
                // Adding just two identifiers now since this might have to be
                // modified
                switch (data.split(SPLITTER)[0]) {
                    case "xpath":
                        by = By.xpath(data.split(SPLITTER)[1]);
                        break;
                    case "id":
                        by = By.id(data.split(SPLITTER)[1]);
                        break;
                    case "className":
                        by = By.className(data.split(SPLITTER)[1]);
                        break;
                    case "name":
                        by = By.name(data.split(SPLITTER)[1]);
                        break;
                    case "linkText":
                        by = By.linkText(data.split(SPLITTER)[1]);
                        break;
                    case "partialLinkText":
                        by = By.partialLinkText(data.split(SPLITTER)[1]);
                        break;
                    case "cssSelector":
                        by = By.cssSelector(data.split(SPLITTER)[1]);
                        break;
                    case "tagName":
                        by = By.tagName(data.split(SPLITTER)[1]);
                        break;

                    default:
                        // Unknown for now

                }
                break;

            case APPIUMDRIVER:
                if (initialize.TestEnvironment.TestTarget.RenderingDriver
                        .toString().equalsIgnoreCase("appAndroid")) {
                    data = initialize.UIObjects.GetUIElementDetails(field
                            .getDeclaringClass().getSimpleName()
                            + "."
                            + field.getName()).value;

                } else if (initialize.TestEnvironment.TestTarget.RenderingDriver
                        .toString().equalsIgnoreCase("appSelendroid")) {
                    data = initialize.UIObjects.GetUIElementDetails(field
                            .getDeclaringClass().getSimpleName()
                            + "."
                            + field.getName()).svalue;
                } else if (initialize.TestEnvironment.TestTarget.RenderingDriver
                        .toString().equalsIgnoreCase("appIOS")) {
                    data = initialize.UIObjects.GetUIElementDetails(field
                            .getDeclaringClass().getSimpleName()
                            + "."
                            + field.getName()).jvalue;
                } else {
                    data = initialize.UIObjects.GetUIElementDetails(field
                            .getDeclaringClass().getSimpleName()
                            + "."
                            + field.getName()).value;
                }
                elementIdentificationType = ElementIdentificationType.APPIUMDRIVER;
                // Adding just two identifiers now since this might have to be
                // modified
                switch (data.split(SPLITTER)[0]) {
                    case "xpath":
                        by = By.xpath(data.split(SPLITTER)[1]);
                        break;
                    case "id":
                        by = By.id(data.split(SPLITTER)[1]);
                        break;
                    case "className":
                        by = By.className(data.split(SPLITTER)[1]);
                        break;
                    case "name":
                        by = By.name(data.split(SPLITTER)[1]);
                        break;
                    case "linkText":
                        by = By.linkText(data.split(SPLITTER)[1]);
                        break;
                    case "cssSelector":
                        by = By.cssSelector(data.split(SPLITTER)[1]);
                        break;
                    default:
                        // Unknown for now

                }
                break;

            default:
                elementIdentificationType = null;
        }
    }

    public IElement findElement(int findTimeout, int visbilityTimeout) {
        CreateElement element = null;
        // System.out.println(by.toString()));
        switch (elementIdentificationType) {
            case WEBDRIVER:
                if (cachedElement != null && shouldCache) {
                    return cachedElement;
                }
                System.out.println("***New Element " + by.toString());
                WebElement webElement = new FluentWait<WebDriver>(webDriver)
                        .withTimeout(findTimeout, TimeUnit.SECONDS)
                        .pollingEvery(500, TimeUnit.MILLISECONDS)
                        .ignoring(NoSuchElementException.class)
                        .ignoring(ElementNotVisibleException.class)
                        .withMessage(
                                "Element could not be found. Finder - "
                                        + by.toString())
                        .until(new Function<WebDriver, WebElement>() {
                            public WebElement apply(WebDriver driver) {
                                System.out.println("Searching for element - "
                                        + by.toString());
                                driver = webDriver;
                                return driver.findElement(by);
                            }
                        });
                new FluentWait<WebElement>(webElement)
                        .withTimeout(visbilityTimeout, TimeUnit.SECONDS)
                        .pollingEvery(500, TimeUnit.MILLISECONDS)
                        .ignoring(WebDriverException.class)
                        .withMessage(
                                "Element was not visible. Finder - "
                                        + by.toString())
                        .until(new Function<WebElement, Boolean>() {
                            public Boolean apply(WebElement webElement) {
                                System.out
                                        .println("Checking if element is displayed - "
                                                + by.toString());
                                return webElement.isDisplayed();
                            }
                        });
                element = new CreateElement(webDriver, webElement,
                        elementIdentificationType);
                if (shouldCache) {
                    cachedElement = element;
                }
                //	ScreenshotHelper.takeScreenShot(webDriver);
                break;
            case JS:
                element = new CreateElement(webDriver, new JsElement(jsBy),
                        elementIdentificationType);

                //ScreenshotHelper.takeScreenShot(webDriver);
                break;

            case APPIUMDRIVER:
                if (cachedElement != null && shouldCache) {
                    return cachedElement;
                }
                System.out.println("***New Element " + by.toString());
                WebElement webElement1 = new FluentWait<AppiumDriver>(appiumDriver)
                        .withTimeout(25, TimeUnit.SECONDS)
                        .pollingEvery(500, TimeUnit.MILLISECONDS)
                        .ignoring(NoSuchElementException.class)
                        .ignoring(ElementNotVisibleException.class)
                        .withMessage(
                                "Element could not be found. Finder - "
                                        + by.toString())
                        .until(new Function<AppiumDriver, WebElement>() {
                            public WebElement apply(AppiumDriver driver) {
                                System.out.println("Searching for element - "
                                        + by.toString());
                                driver = appiumDriver;
                                return driver.findElement(by);
                            }
                        });
                new FluentWait<WebElement>(webElement1)
                        .withTimeout(12, TimeUnit.SECONDS)
                        .pollingEvery(500, TimeUnit.MILLISECONDS)
                        .ignoring(WebDriverException.class)
                        .withMessage(
                                "Element was not visible. Finder - "
                                        + by.toString())
                        .until(new Function<WebElement, Boolean>() {
                            public Boolean apply(WebElement webElement) {
                                System.out
                                        .println("Checking if element is displayed - "
                                                + by.toString());
                                return webElement.isDisplayed();
                            }
                        });
                element = new CreateElement(webDriver, webElement1,
                        elementIdentificationType);
                if (shouldCache) {
                    cachedElement = element;
                }
//			 ScreenshotHelper.takeScreenShot(webDriver);
                break;
            default:
                element = null;
        }
        return element;
    }

    public List<IElement> findElements(int findTimeout) {
        List<IElement> elements = new ArrayList<IElement>();
        List<WebElement> webElementList;
        switch (elementIdentificationType) {
            case APPIUMDRIVER:
                System.out.println(by);
                if (cachedElementList != null && shouldCache) {
                    return cachedElementList;
                }
                webElementList = new FluentWait<AppiumDriver>(appiumDriver)
                        .withTimeout(15, TimeUnit.SECONDS)
                        .pollingEvery(500, TimeUnit.MILLISECONDS)
                        .ignoring(NoSuchElementException.class)
                        .withMessage(
                                "Element could not be found - " + by.toString())
                        .until(new Function<AppiumDriver, List<WebElement>>() {
                            public List<WebElement> apply(AppiumDriver driver) {
                                System.out.println("Searching for list - "
                                        + by.toString());
                                driver = appiumDriver;
                                return driver.findElements(by);
                            }
                        });
                for (WebElement singleWebElement : webElementList) {
                    elements.add(new CreateElement(appiumDriver, singleWebElement,
                            elementIdentificationType));
                }
                if (shouldCache) {
                    cachedElementList = elements;
                }
                //ScreenshotHelper.takeScreenShot(webDriver);
                break;
            case JS:
                // TBD
                break;

            case WEBDRIVER:
                System.out.println(by);
                if (cachedElementList != null && shouldCache) {
                    return cachedElementList;
                }
                webElementList = new FluentWait<WebDriver>(webDriver)
                        .withTimeout(findTimeout, TimeUnit.SECONDS)
                        .pollingEvery(500, TimeUnit.MILLISECONDS)
                        .ignoring(NoSuchElementException.class)
                        .withMessage(
                                "Element could not be found - " + by.toString())
                        .until(new Function<WebDriver, List<WebElement>>() {
                            public List<WebElement> apply(WebDriver driver) {
                                System.out.println("Searching for list - "
                                        + by.toString());
                                driver = webDriver;
                                return driver.findElements(by);
                            }
                        });
                for (WebElement singleWebElement : webElementList) {
                    elements.add(new CreateElement(webDriver, singleWebElement,
                            elementIdentificationType));
                }
                if (shouldCache) {
                    cachedElementList = elements;
                }
                ScreenshotHelper.takeScreenShot(webDriver);
                break;
            default:
                elements = null;
        }
        return elements;
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public AppiumDriver getAppiumDriver() {
        return appiumDriver;
    }


}
