/**
 * Implementation of {@link IElement} which overrides {@link WebElement}
 * functionality and switches it between the {@link WebDriver} or
 * {@link JsElement}. Any new method must be first added to the {@link IElement}
 * interface since all page elemets are {@link IElement}s
 */

package org.example.test.common;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.example.test.javascript.JsElement;
import org.example.test.javascript.JsFactory;
import org.example.test.javascript.JsFunction;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

public class CreateElement implements IElement {

    private WebElement webElement;
    private WebDriver webDriver;
    private JsElement jsElement;

    private ElementIdentificationType elementIdentificationType;


    public CreateElement(WebDriver webDriver, WebElement webElement,
                         ElementIdentificationType elementIdentificationType) {
        this.setWebDriver(webDriver);
        this.setWebElement(webElement);
        this.setElementIdentificationType(elementIdentificationType);
    }

    public CreateElement(WebDriver webDriver, JsElement jsElement,
                         ElementIdentificationType myntElementIdentificationType) {
        this.setWebDriver(webDriver);
        this.setJsElement(jsElement);
        this.setElementIdentificationType(myntElementIdentificationType);
    }

    public void setElementIdentificationType(
            ElementIdentificationType elementIdentificationType) {
        this.elementIdentificationType = elementIdentificationType;
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void setWebElement(WebElement webElement) {
        this.webElement = webElement;
    }

    public ElementIdentificationType getType() {
        return elementIdentificationType;
    }

    public boolean waitForElementPresent(int timeout) {
        boolean isFound = false;
        WebDriverWait wait = new WebDriverWait(webDriver, timeout);
        wait.until(ExpectedConditions.visibilityOf(getWebElement()));
        return isFound;
    }

    public boolean waitForElementPresent() {
        int timeout = 5000;
        return waitForElementPresent(timeout);
    }

    public void hover(int... timeouts) {
        switch (elementIdentificationType) {
            case JS:
                // TODO
                break;
            case WEBDRIVER:
                new Actions(webDriver).moveToElement(webElement).pause(500).perform();
                break;
            case APPIUMDRIVER:
                //	new Actions(webDriver).moveToElement(webElement).pause(500).perform();

                break;
        }
    }

    public Select getSelect(int... timeouts) {
        return new Select(getWebElement());
    }

    public void click(int... timeouts) {
        switch (elementIdentificationType) {
            case JS:
                JsFactory.executeJavaScript(webDriver, jsElement, JsFunction.CLICK);
                break;
            case WEBDRIVER:
            case APPIUMDRIVER:
                new FluentWait<WebElement>(webElement)
                        .pollingEvery(500, TimeUnit.MILLISECONDS)
                        .withTimeout(15, TimeUnit.SECONDS)
                        .withMessage(
                                "Not able to click. Finder - "
                                        + webElement.toString())
                        .until(new Function<WebElement, Boolean>() {
                            public Boolean apply(WebElement webElement) {
                                boolean isClickable = false;
                                System.out.println("Trying to click on - "
                                        + webElement.toString());
                                try {
                                    webElement.click();
                                    isClickable = true;
                                } catch (WebDriverException e) {
                                }
                                return isClickable;
                            }
                        });
                break;
        }
    }

    public void submit(int... timeouts) {
        switch (elementIdentificationType) {
            case JS:
                JsFactory.executeJavaScript(webDriver, jsElement, JsFunction.SUBMIT);
                break;
            case WEBDRIVER:
            case APPIUMDRIVER:
                webElement.submit();
                break;
        }
    }

    public void sendKeys(final CharSequence keysToSend, int... timeouts) {
        switch (elementIdentificationType) {
            case JS:
                // TODO
                break;
            case WEBDRIVER:
            case APPIUMDRIVER:
                new FluentWait<WebElement>(webElement)
                        .pollingEvery(500, TimeUnit.MILLISECONDS)
                        .withTimeout(15, TimeUnit.SECONDS)
                        .withMessage(
                                "Not able to send Keys. Finder - "
                                        + webElement.toString())
                        .until(new Function<WebElement, Boolean>() {
                            public Boolean apply(WebElement webElement) {
                                boolean isKeySendPossible = false;
                                System.out.println("Trying to sendKeys to - "
                                        + webElement.toString());
                                try {
                                    webElement.sendKeys(keysToSend);
                                    isKeySendPossible = true;
                                } catch (WebDriverException e) {
                                }
                                return isKeySendPossible;
                            }
                        });
                break;
        }
    }

    public void clear(int... timeouts) {
        switch (elementIdentificationType) {
            case JS:
                // TODO - Clear textarea or textbox to be added
//			JsFactory.executeJavaScript(webDriver, jsElement,
//					JsFunction.CLEAR_CHECKBOX);
                break;
            case WEBDRIVER:
            case APPIUMDRIVER:
                webElement.clear();
                break;
        }
    }

    public String getTagName(int... timeouts) {
        String tagName = "";
        switch (elementIdentificationType) {
		case JS:
			tagName = (String) JsFactory.executeJavaScript(webDriver,
					jsElement, JsFunction.GET_TAG_NAME);
			break;
            case WEBDRIVER:
            case APPIUMDRIVER:
                tagName = webElement.getTagName();
        }
        return tagName;
    }

    public String getAttribute(String name, int... timeouts) {
        String attribute = "";
        switch (elementIdentificationType) {
		case JS:
			attribute = (String) JsFactory.executeJavaScript(webDriver,
					jsElement, JsFunction.GET_ATTRIBUTE);
			break;
            case WEBDRIVER:
            case APPIUMDRIVER:
                attribute = webElement.getAttribute(name);
                break;
        }
        return attribute;
    }

    public boolean isSelected(int... timeouts) {
        boolean isSelected = false;
        switch (elementIdentificationType) {
		case JS:
			isSelected = (Boolean) JsFactory.executeJavaScript(webDriver,
					jsElement, JsFunction.IS_SELECTED);
			break;
            case WEBDRIVER:
            case APPIUMDRIVER:
                isSelected = webElement.isSelected();
                break;
        }
        return isSelected;
    }

    public boolean isEnabled(int... timeouts) {
        boolean isEnabled = false;
        switch (elementIdentificationType) {
		case JS:
			isEnabled = (Boolean) JsFactory.executeJavaScript(webDriver,
					jsElement, JsFunction.IS_ENABLED);
			break;
            case WEBDRIVER:
            case APPIUMDRIVER:
                isEnabled = webElement.isEnabled();
                break;
        }
        return isEnabled;
    }

    public String getText(int... timeouts) {
        String text = "";
        switch (elementIdentificationType) {
		case JS:
			text = (String) JsFactory.executeJavaScript(webDriver, jsElement,
					JsFunction.GET_TEXT);
			break;
            case WEBDRIVER:
            case APPIUMDRIVER:
                text = webElement.getText();
                break;
        }
        return text;
    }

    public List<WebElement> findElements(By by) {
        List<WebElement> elements = null;
        switch (elementIdentificationType) {
            case JS:
                // TODO
                break;
            case WEBDRIVER:
            case APPIUMDRIVER:
                elements = webDriver.findElements(by);
                break;
        }
        return elements;
    }

    public WebElement findElement(By by) {
        WebElement element = null;
        switch (elementIdentificationType) {
            case JS:
                // TODO
                break;
            case WEBDRIVER:
            case APPIUMDRIVER:
                element = webDriver.findElement(by);
                break;
        }
        return element;
    }

    public boolean isDisplayed(int... timeouts) {
        boolean isDisplayed = false;
        switch (elementIdentificationType) {
            case JS:
			isDisplayed = (Boolean) JsFactory.executeJavaScript(webDriver,
					jsElement, JsFunction.IS_DISPLAYED);
                break;
            case WEBDRIVER:
            case APPIUMDRIVER:
                isDisplayed = webElement.isDisplayed();
                break;
        }
        return isDisplayed;
    }

    public Point getLocation(int... timeouts) {
        Point location = null;
        switch (elementIdentificationType) {
            case JS:
                // TODO
                break;
            case WEBDRIVER:
            case APPIUMDRIVER:
                location = webElement.getLocation();
                break;
        }
        return location;
    }

    public Dimension getSize(int... timeouts) {
        Dimension size = null;
        switch (elementIdentificationType) {
            case JS:
                // TODO
                break;
            case WEBDRIVER:
            case APPIUMDRIVER:
                size = webElement.getSize();
                break;
        }
        return size;
    }

    public String getCssValue(String propertyName, int... timeouts) {
        String cssValue = "";
        switch (elementIdentificationType) {
            case JS:

                cssValue = (String) JsFactory.executeJavaScript(webDriver,
                        jsElement, JsFunction.GET_CSS_VALUE);
                break;
            case WEBDRIVER:
            case APPIUMDRIVER:
                cssValue = webElement.getCssValue(propertyName);
                break;
        }
        return cssValue;
    }

    public WebElement getWebElement() {
        return webElement;
    }

    @Override
    public String[] getSimilarTagsContentList() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setJsElement(JsElement jsElement) {
        this.jsElement = jsElement;
    }


}
