package org.example.test.javascript;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * Created by Sabyasachi on 10/6/2015.
 */
public class JsFactory {

    /**
     * Generates in-built JavaScript when parent-finder-element and function
     * needed are input
     *
     * @param jsElement the base element tag finder
     * @param jsFuntion JavaScript functions enum
     * @return generated JavaScript string
     */
    public static String generateJavaScript(JsElement jsElement,
                                            JsFunction jsFuntion) {
        return jsFuntion.getBaseQuery().replace("%s",
                jsElement.getJsBaseQuery());
    }

    /**
     * Executes the JavaScript string using a {@link JavascriptExecutor} casted
     * WebDriver. Cast the return object into the expected type
     *
     * @param webDriver
     * @param javaScript
     * @return same as
     * {@link JavascriptExecutor#executeScript(String, Object...)}
     */
    public static Object executeJavaScript(WebDriver webDriver,
                                           String javaScript) {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        return js.executeScript(javaScript);
    }

    public static Object executeJavaScript(WebDriver webDriver,
                                           JsElement jsElement, JsFunction jsFuntion) {
        return executeJavaScript(webDriver, jsElement, jsFuntion);
    }
}
