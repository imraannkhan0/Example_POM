package org.example.test.appiumdriver;


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by Sabyasachi on 30/6/2015.
 */
public class MobileGestures {

TouchAction touchAction;
 AppiumDriver appiumDriver;
    WebDriver webDriver;

    public void setTouchAction(TouchAction touchAction) {
        this.touchAction = touchAction;
    }

    public MobileGestures(AppiumDriver appiumDriver){
        this.touchAction=new TouchAction(appiumDriver);
        this.appiumDriver=appiumDriver;

    }



    public MobileGestures press(int x,int y){

        setTouchAction(touchAction.press(x,y));
        return this;
    }

    public MobileGestures press(WebElement webElement){
        setTouchAction(touchAction.press(webElement));
        return this;
    }

    public MobileGestures press(WebElement webElement,int x,int y){
        setTouchAction(touchAction.press(webElement,x,y));
        return this;
    }

    public MobileGestures longPress(int x, int y){
        setTouchAction(touchAction.longPress(x,y));
        return this;
    }

    public MobileGestures longPress(int x,int y,int duration){
        setTouchAction(touchAction.longPress(x,y,duration));
        return this;
    }

    public MobileGestures longPress(WebElement webElement){
        setTouchAction(touchAction.longPress(webElement));
        return this;
    }

    public MobileGestures longPress(WebElement webElement,int x,int y){
        setTouchAction(touchAction.longPress(webElement,x,y));
        return this;
    }

    public MobileGestures longPress(WebElement webElement,int x,int y,int duration){
        setTouchAction(touchAction.longPress(webElement,x,y,duration));
        return this;
    }

    public MobileGestures moveTo(int x,int y){
        setTouchAction(touchAction.moveTo(x,y));
        return this;
    }

    public MobileGestures moveTo(WebElement webElement){
        setTouchAction(touchAction.moveTo(webElement));
        return this;
    }

    public MobileGestures moveTo(WebElement webElement,int x,int y){
        setTouchAction(touchAction.moveTo(webElement,x,y));
        return this;
    }

    public MobileGestures tap(int x,int y){
        setTouchAction(touchAction.tap(x,y));
        return this;
    }



    public MobileGestures tap(WebElement webElement){
        setTouchAction(touchAction.tap(webElement));
        return this;
    }

    public MobileGestures tap(WebElement webElement,int x,int y){
        setTouchAction(touchAction.tap(webElement,x,y));
        return this;
    }


    public MobileGestures perform(){
        setTouchAction(touchAction.perform());
        return this;
    }

    public MobileGestures release(){
        setTouchAction(touchAction.release());
        return this;
    }

    public MobileGestures waitAction(){
        setTouchAction(touchAction.waitAction());
        return this;
    }

    public MobileGestures waitAction(int ms){
        setTouchAction(touchAction.waitAction(ms));
        return this;
    }

    public static void swipeUp(MobileElement mobileElement,int duration){

        mobileElement.swipe(SwipeElementDirection.UP,duration);


    }

    public static void swipeDown(MobileElement mobileElement,int duration){

        mobileElement.swipe(SwipeElementDirection.DOWN,duration);


    }

    public static void swipeRight(MobileElement mobileElement,int duration){

        mobileElement.swipe(SwipeElementDirection.RIGHT,duration);


    }

    public static void swipeLeft(MobileElement mobileElement,int duration){

        mobileElement.swipe(SwipeElementDirection.LEFT,duration);


    }
}
