package org.example.test.common;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * Created by Sabyasachi on 30/6/2015.
 */
public class MouseAction {

    private Actions actions;
    private WebDriver webDriver;
    private final String SPLITTER = "@#@";

    public void setActions(Actions actions)
    {
        this.actions = actions;
    }

    public MouseAction(WebDriver webDriver)
    {
        this.actions = new Actions(webDriver);
        this.webDriver = webDriver;
    }

    /**
     * Performs a modifier key press. Does not release the modifier key -
     * subsequent interactions may assume it's kept pressed. Note that the
     * modifier key is <b>never</b> released implicitly - either
     * <i>keyUp(theKey)</i> or <i>sendKeys(Keys.NULL)</i> must be called to
     * release the modifier.
     *
     * @param theKey
     *            Either {@link Keys#SHIFT}, {@link Keys#ALT} or
     *            {@link Keys#CONTROL}. If the provided key is none of those,
     *            {@link IllegalArgumentException} is thrown.
     * @return A self reference.
     */


    public MouseAction keyDown(Keys theKey)
    {
        setActions(actions.keyDown(null, theKey));
        return this;
    }


    /**
     * Performs a modifier key press after focusing on an element. Equivalent
     * to: <i>Actions.click(element).sendKeys(theKey);</i>
     *
     * @see #keyDown(org.openqa.selenium.Keys)
     *
     * @param theKey
     *            Either {@link Keys#SHIFT}, {@link Keys#ALT} or
     *            {@link Keys#CONTROL}. If the provided key is none of those,
     *            {@link IllegalArgumentException} is thrown.
     * @return A self reference.
     */

    public MouseAction keyDown(WebElement element, Keys theKey)
    {
        setActions(actions.keyDown(element, theKey));
        return this;
    }

    /**
     * Performs a modifier key release. Releasing a non-depressed modifier key
     * will yield undefined behaviour.
     *
     * @param theKey
     *            Either {@link Keys#SHIFT}, {@link Keys#ALT} or
     *            {@link Keys#CONTROL}.
     * @return A self reference.
     */

    public MouseAction keyUp(Keys theKey)
    {
        setActions(actions.keyUp(null, theKey));
        return this;
    }

    /**
     * Performs a modifier key release after focusing on an element. Equivalent
     * to: <i>Actions.click(element).sendKeys(theKey);</i>
     *
     * @see #keyUp(org.openqa.selenium.Keys) on behaviour regarding
     *      non-depressed modifier keys.
     *
     * @param theKey
     *            Either {@link Keys#SHIFT}, {@link Keys#ALT} or
     *            {@link Keys#CONTROL}.
     * @return A self reference.
     */

    public MouseAction keyUp(WebElement element, Keys theKey)
    {
        setActions(actions.keyUp(element, theKey));
        return this;
    }

    /**
     * Sends keys to the active element. This differs from calling
     * {@link WebElement#sendKeys(CharSequence...)} on the active element in two
     * ways:
     * <ul>
     * <li>The modifier keys included in this call are not released.</li>
     * <li>There is no attempt to re-focus the element - so sendKeys(Keys.TAB)
     * for switching elements should work.</li>
     * </ul>
     *
     * @see WebElement#sendKeys(CharSequence...)
     *
     * @param keysToSend
     *            The keys.
     * @return A self reference.
     */

    public MouseAction sendKeys(CharSequence... keysToSend)
    {
        setActions(actions.sendKeys(null, keysToSend));
        return this;
    }

    /**
     * Equivalent to calling:
     * <i>Actions.click(element).sendKeys(keysToSend).</i> This method is
     * different from
     * {@link org.openqa.selenium.WebElement#sendKeys(CharSequence...)} - see
     * {@link Actions#sendKeys(CharSequence...)} for details how.
     *
     * @see #sendKeys(java.lang.CharSequence[])
     *
     * @param element
     *            element to focus on.
     * @param keysToSend
     *            The keys.
     * @return A self reference.
     */
    public MouseAction sendKeys(WebElement element, CharSequence... keysToSend)
    {
        setActions(actions.sendKeys(element, keysToSend));
        return this;
    }

    /**
     * Clicks (without releasing) in the middle of the given element. This is
     * equivalent to: <i>Actions.moveToElement(onElement).clickAndHold()</i>
     *
     * @param onElement
     *            Element to move to and click.
     * @return A self reference.
     */
    public MouseAction clickAndHold(WebElement onElement)
    {
        setActions(actions.clickAndHold(onElement));
        return this;
    }

    /**
     * Clicks (without releasing) at the current mouse location.
     *
     * @return A self reference.
     */
    public MouseAction clickAndHold()
    {
        setActions(actions.clickAndHold());
        return this;
    }

    /**
     * Releases the depressed left mouse button, in the middle of the given
     * element. This is equivalent to:
     * <i>Actions.moveToElement(onElement).release()</i>
     *
     * Invoking this action without invoking {@link #clickAndHold()} first will
     * result in undefined behaviour.
     *
     * @param onElement
     *            Element to release the mouse button above.
     * @return A self reference.
     */
    public MouseAction release(WebElement onElement)
    {
        setActions(actions.release(onElement));
        return this;
    }

    /**
     * Releases the depressed left mouse button at the current mouse location.
     *
     * @see #release(org.openqa.selenium.WebElement)
     * @return A self reference.
     */
    public MouseAction release()
    {
        setActions(actions.release());
        return this;
    }

    /**
     * Clicks in the middle of the given element. Equivalent to:
     * <i>Actions.moveToElement(onElement).click()</i>
     *
     * @param onElement
     *            Element to click.
     * @return A self reference.
     */
    public MouseAction click(WebElement onElement)
    {
        setActions(actions.click(onElement));
        return this;
    }

    /**
     * Clicks at the current mouse location. Useful when combined with
     *  or
     * {@link #moveByOffset(int, int)}.
     *
     * @return A self reference.
     */
    public MouseAction click()
    {
        setActions(actions.click());
        return this;
    }

    /**
     * Performs a double-click at middle of the given element. Equivalent to:
     * <i>Actions.moveToElement(element).doubleClick()</i>
     *
     * @param onElement
     *            Element to move to.
     * @return A self reference.
     */
    public MouseAction doubleClick(WebElement onElement)
    {
        setActions(actions.doubleClick(onElement));
        return this;
    }

    /**
     * Performs a double-click at the current mouse location.
     *
     * @return A self reference.
     */
    public MouseAction doubleClick()
    {
        setActions(actions.doubleClick());
        return this;
    }

    /**
     * Moves the mouse to the middle of the element. The element is scrolled
     * into view and its location is calculated using getBoundingClientRect.
     *
     * @param toElement
     *            element to move to.
     * @return A self reference.
     */
    public MouseAction moveToElement(IElement toElement)
    {
        setActions(actions.moveToElement(toElement.getWebElement()));
        return this;
    }

    /**
     * Moves the mouse to an offset from the top-left corner of the element. The
     * element is scrolled into view and its location is calculated using
     * getBoundingClientRect.
     *
     * @param toElement
     *            element to move to.
     * @param xOffset
     *            Offset from the top-left corner. A negative value means
     *            coordinates right from the element.
     * @param yOffset
     *            Offset from the top-left corner. A negative value means
     *            coordinates above the element.
     * @return A self reference.
     */
    public MouseAction moveToElement(IElement toElement, int xOffset,
                                     int yOffset)
    {
        setActions(actions.moveToElement(toElement.getWebElement(), xOffset,
                yOffset));
        return this;
    }

    /**
     * Moves the mouse from its current position (or 0,0) by the given offset.
     * If the coordinates provided are outside the viewport (the mouse will end
     * up outside the browser window) then the viewport is scrolled to match.
     *
     * @param xOffset
     *            horizontal offset. A negative value means moving the mouse
     *            left.
     * @param yOffset
     *            vertical offset. A negative value means moving the mouse up.
     * @return A self reference.
     *
     *         if the provided offset is outside the document's boundaries.
     */
    public MouseAction moveByOffset(int xOffset, int yOffset)
    {
        setActions(actions.moveByOffset(xOffset, yOffset));
        return this;
    }

    /**
     * Performs a context-click at middle of the given element. First performs a
     * mouseMove to the location of the element.
     *
     * @param onElement
     *            Element to move to.
     * @return A self reference.
     */
    public MouseAction contextClick(WebElement onElement)
    {
        setActions(actions.contextClick(onElement));
        return this;
    }

    /**
     * Performs a context-click at the current mouse location.
     *
     * @return A self reference.
     */
    public MouseAction contextClick()
    {
        setActions(actions.contextClick());
        return this;
    }

    /**
     * A convenience method that performs click-and-hold at the location of the
     * source element, moves to the location of the target element, then
     * releases the mouse.
     *
     * @param source
     *            element to emulate button down at.
     * @param target
     *            element to move to and release the mouse at.
     * @return A self reference.
     */
    public MouseAction dragAndDrop(IElement source, IElement target)
    {
        setActions(actions.dragAndDrop(source.getWebElement(),
                target.getWebElement()));
        return this;
    }

    /**
     * A convenience method that performs click-and-hold at the location of the
     * source element, moves by a given offset, then releases the mouse.
     *
     * @param source
     *            element to emulate button down at.
     * @param xOffset
     *            horizontal move offset.
     * @param yOffset
     *            vertical move offset.
     * @return A self reference.
     */
    public MouseAction dragAndDropBy(IElement source, int xOffset, int yOffset)
    {
        setActions(actions.dragAndDropBy(source.getWebElement(), xOffset,
                yOffset));
        return this;
    }

    /**
     * Performs a pause.
     *
     * @param pause
     *            pause duration, in milliseconds.
     * @return A self reference.
     */
    public MouseAction pause(long pause)
    {
        setActions(actions.pause(pause));
        return this;
    }

    public MouseAction moveToChildElement(ElementIdentificationType type,
                                          String path)
    {
        switch (type)
        {
            case JS:
                // Apply hover class on element
                break;
            case WEBDRIVER:
                By by = getBy(path);
                setActions(actions.moveToElement(webDriver.findElement(by)));
                break;
        }
        return this;
    }

    private By getBy(String path)
    {
        By by = null;
        switch (path.split(SPLITTER)[0])
        {
            case "xpath":
                by = By.xpath(path.split(SPLITTER)[1]);
                break;
            case "class":
                by = By.className(path.split(SPLITTER)[1]);
                break;
            case "cssSelector":
                by = By.cssSelector(path.split(SPLITTER)[1]);
                break;
            case "id":
                by = By.id(path.split(SPLITTER)[1]);
                break;
            case "name":
                by = By.name(path.split(SPLITTER)[1]);
                break;
        }
        return by;
    }

    public MouseAction clickOnChildElement(ElementIdentificationType type,
                                           String path)
    {
        switch (type)
        {
            case JS:
                // Apply hover class on element
                break;
            case WEBDRIVER:
                By by = getBy(path);
                setActions(actions.click(webDriver.findElement(by)));
                break;
        }
        return this;
    }

    /**
     * A convenience method for performing the actions without calling build()
     * first.
     */
    public void perform()
    {
        actions.build().perform();
        // ScreenshotHelper.takeScreenShot(webDriver);
    }
}
