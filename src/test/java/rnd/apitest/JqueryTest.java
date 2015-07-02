package rnd.apitest;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

/**
 * Created by Sabyasachi on 21/6/2015.
 */
public class JqueryTest {

    public static void main(String[] args){

        WebDriver driver=new FirefoxDriver();
        driver.get("http://google.com");
        WebElement searchbox = driver.findElement(By.xpath("//input[@name='q']"));
        JavascriptExecutor myExecutor = ((JavascriptExecutor) driver);
        myExecutor.executeScript("arguments[0].value='selenium';", searchbox);
        SoftAssert softAssert=new SoftAssert();



    }
}
