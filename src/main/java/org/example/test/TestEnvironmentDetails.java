package org.example.test;

import org.example.test.common.TestTargetProfile;

public class TestEnvironmentDetails {
    public String URL_PORTALHOME;
    public TestTargetProfile TestTarget;

    public TestEnvironmentDetails(Configuration config) {
        //URL_PORTALHOME = config.GetPortalHomeUrl();
        URL_PORTALHOME = config.getPortalUrl();
        TestTarget = new TestTargetProfile();
        //	log.info("This is the info log from TestEnvironmentDetails");
    }

    public TestEnvironmentDetails(Configuration config, TestTargetProfile TestTargetProf) {
        URL_PORTALHOME = config.GetPortalHomeUrl();
        TestTarget = new TestTargetProfile(TestTargetProf);
        //log.info("This is the info log from TestEnvironmentDetails");
    }

}
