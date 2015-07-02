package org.example.test.pages;

import org.example.test.Initialize;
import org.example.test.common.IElement;
import org.example.test.webdriver.WebPage;

/**
 * Created by Sabyasachi on 7/6/2015.
 */
public class BasePage extends WebPage {

    /**
     * This constructor should take parameters specific to the browser being
     * used and use it to invoke the required browser using
     * {@link org.example.test.webdriver.WebDriverFactory}
     *
     * @param initialize
     */

    public BasePage(Initialize initialize) {
        super(initialize);
    }


}
