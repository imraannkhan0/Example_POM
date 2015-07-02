package org.example.test.appiumdriver;

/**
 * Created by Sabyasachi on 1/7/2015.
 */
public class CapabilityValues {

    //Android capabilities
    public final static String ANDROID_APP=System.getProperty("user.dir")+"/Data/configuration/flipkart.apk";
    public final static String ANDROID_PLATFORM="Android";
    public final static String APPIUM="Appium";
    public final static String SELENDROID="Selendroid";
    public final static String ANDROID_DEVICE="Android Emulator";
    public final static String ANDROID_APP_PACKAGE="";
    public final static String ANDROID_VERSION="4.4";
    public final static String PLATFORM_WINDOWS="windows";
    public final static String PLATFORM_MAC="mac";
    public final static String PLATFORM_ANY="any";
    public final static String PLATFORM_LINUX="linux";


    //IOS Capabilities
    public final static String IOS_APP=System.getProperty("user.dir")+"/Data/configuration/flipkart.app";
    public final static String IOS_PLATFORM="IOS";
    public final static String IOS_DEVICE="iPhone 5s (8.1 Simulator) [42FD0559-5276-4B7E-BE71-4283A8FFCFE4]";
    public final static String BOUNDLE_IOS="";
    public final static boolean AUTOLUNCH_ENABLED=true;

    public final static String MOBILE_DEVICE="Mobile";
    public final static boolean JAVASCRIPT_ENABLED=true;
    public final static boolean TOUCHSCREEN_ENABLED=true;
    public final static boolean SSL_CERTIFICATE_ENABLED=true;

}
