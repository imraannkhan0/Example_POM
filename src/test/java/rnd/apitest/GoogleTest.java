package rnd.apitest;

import org.example.test.*;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by Sabyasachi on 6/6/2015.
 */
public class GoogleTest {

    static Initialize initialize ;
    APIUtilities apiUtil ;

    @BeforeClass
    public void setUpEnv(){
        initialize= new Initialize("./Data/configuration");
        apiUtil= new APIUtilities();
    }

    @Test
    public void GoogleMapTest_Json() throws Exception {
        CreateServices service=new CreateServices(ServiceType.portal_geolocation, APINAME.mapapi, initialize.Configurations,PayloadType.XML,PayloadType.XML);
        service.URL=apiUtil.prepareparameterizedURL(service.URL, new String[]{"false","bangalore"});
        System.out.println(service.URL);
         RequestGenerator req=new RequestGenerator(service);
       // RequestGeneratorWithUnirest req=new RequestGeneratorWithUnirest(service);
        String jsonResponse = req.returnresponseasstring();
          System.out.println(jsonResponse);
        Assert.assertEquals(req.response.getStatus(),200);
    }

    @Test
    public void signinTest() throws Exception {
        CreateServices service=new CreateServices(ServiceType.portal_devapi,APINAME.signin,initialize.Configurations,new String[]{"devapitesting100@myntra.com","123456"},PayloadType.JSON,PayloadType.JSON);
        System.out.println(service.Payload);
        System.out.println(service.URL);
       // RequestGenerator req=new RequestGenerator(service);
        RequestGeneratorWithUnirest req=new RequestGeneratorWithUnirest(service);
        System.out.println(req.response.getStatus());
        System.out.println(req.returnresponseasstring());


    }
    @AfterClass
    public void endTest(){


    }
}
