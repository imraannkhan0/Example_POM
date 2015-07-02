package org.example.test.common;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public interface IElement {

    void clear(int... timeouts);

    void click(int... timeouts);

    WebElement findElement(By by);

    List<WebElement> findElements(By by);

    String getAttribute(String name, int... timeouts);

    String getCssValue(String propertyName, int... timeouts);

    Point getLocation(int... timeouts);

    Dimension getSize(int... timeouts);

    String getTagName(int... timeouts);

    String getText(int... timeouts);

    ElementIdentificationType getType();

    boolean isDisplayed(int... timeouts);

    boolean isEnabled(int... timeouts);

    boolean isSelected(int... timeouts);

    void sendKeys(CharSequence keysToSend, int... timeouts);

    void submit(int... timeouts);

    String[] getSimilarTagsContentList();

    WebElement getWebElement();

    void hover(int... timeouts);

    Select getSelect(int... timeouts);


}
