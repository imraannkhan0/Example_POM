package rnd;

import initializeFramework.InitializeFramework;
import org.example.test.Initialize;
import org.example.test.PLATFORM;
import org.example.test.appiumdriver.MobileGestures;
import org.example.test.common.RENDERINGDRIVER;
import org.example.test.pages.HomePage;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by Sabyasachi on 7/6/2015.
 */
public class GooglePortalClass {

    HomePage homePage;

    private Initialize initialize = InitializeFramework.init;

    @BeforeClass
    public void setUpEnv(){
     //   initialize.TestEnvironment.TestTarget.Platform = PLATFORM.MOBILE;
     //   initialize.TestEnvironment.TestTarget.RenderingDriver = RENDERINGDRIVER.MobileFirefox;
    }


  @Test
    public void OpenGmail(){

      homePage=new HomePage(initialize);
      homePage.open();
      homePage.clickOnGmailLink();

  }

    @AfterClass
    public void endTest(){
        homePage.endTest();
    }
}
