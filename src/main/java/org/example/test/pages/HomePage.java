package org.example.test.pages;

import org.example.test.Environment;
import org.example.test.Initialize;
import org.example.test.common.IElement;

/**
 * Created by Sabyasachi on 7/6/2015.
 */
public class HomePage extends BasePage {
    /**
     * This constructor should take parameters specific to the browser being
     * used and use it to invoke the required browser using
     * {@link org.example.test.webdriver.WebDriverFactory}
     *
     * @param initialize
     */
    Initialize initialize;
    IElement gmailLink;

    public HomePage(Initialize initialize) {
        super(initialize);
        this.initialize = initialize;
    }

    public void open() {
        webDriver.get(initialize.TestEnvironment.URL_PORTALHOME);
//webDriver.get("http://www.taxiforsure.com/");
    }

    public void clickOnGmailLink() {
        gmailLink.click();
    }

}


