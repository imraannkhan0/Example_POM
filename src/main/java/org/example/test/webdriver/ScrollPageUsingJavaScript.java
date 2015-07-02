package org.example.test.webdriver;

import org.example.test.common.IElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * Created by Sabyasachi on 1/7/2015.
 */
public class ScrollPageUsingJavaScript {

    WebDriver webDriver;
    JavascriptExecutor javascriptExecutor;

    public  ScrollPageUsingJavaScript(WebDriver webDriver){
        this.webDriver=webDriver;
        this.javascriptExecutor=(JavascriptExecutor)webDriver;
    }

    public void scrollToEnd(){
        javascriptExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");

    }

    public void scrollTo(IElement iElement){
        javascriptExecutor.executeScript( "arguments[0].scrollIntoView();",iElement.getWebElement());
    }

    public void scrollTo(int x,int y){
        javascriptExecutor.executeScript("window.scrollBy("+x+","+y+")");
    }
}
