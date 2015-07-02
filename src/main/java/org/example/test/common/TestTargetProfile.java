package org.example.test.common;

import org.example.test.OS;
import org.example.test.PLATFORM;

public class TestTargetProfile {
    public PLATFORM Platform;
    public OS OperatingSystem = OS.ANDROID;
    public String Device;
    public RENDERINGDRIVER RenderingDriver;
    public String TargetMobileApp;
    public boolean ParallelRuns;

    public TestTargetProfile(TestTargetProfile testTargetProf) {
        setPlatform(testTargetProf.Platform);
        setDevice(testTargetProf.Device);
        setRenderingDriver(testTargetProf.RenderingDriver);
        setTargetMobileApp(testTargetProf.TargetMobileApp);
        setOperatingSystem(testTargetProf.OperatingSystem);
    }


    public TestTargetProfile() {
        Platform = PLATFORM.DESKTOP;
        Device = null;
        RenderingDriver = RENDERINGDRIVER.Chrome;
        TargetMobileApp = null;
        ParallelRuns = false;
        setPlatform(PLATFORM.DESKTOP);
        setDevice(null);
        setRenderingDriver(RENDERINGDRIVER.Chrome);
        setTargetMobileApp(null);
        setOperatingSystem(OS.ANDROID);

    }


    public TestTargetProfile(PLATFORM Platform, OS OperatingSystem, String device, RENDERINGDRIVER RenderingDriver, String TargetMobileApp) {
        setPlatform(Platform);
        setDevice(device);
        setRenderingDriver(RenderingDriver);
        setTargetMobileApp(TargetMobileApp);
        setOperatingSystem(OperatingSystem);
        setParallelRuns(ParallelRuns);
    }


    public String getDevice() {
        return Device;
    }

    public OS getOperatingSystem() {
        return OperatingSystem;
    }

    public PLATFORM getPlatform() {
        return Platform;
    }

    public RENDERINGDRIVER getRenderingDriver() {
        return RenderingDriver;
    }

    public String getTargetMobileApp() {
        return TargetMobileApp;
    }

    private void setDevice(String device) {
        Device = device;
    }

    private void setOperatingSystem(OS operatingSystem) {
        OperatingSystem = operatingSystem;
    }

    private void setPlatform(PLATFORM platform) {
        Platform = platform;
    }

    private void setRenderingDriver(RENDERINGDRIVER renderingDriver) {
        RenderingDriver = renderingDriver;
    }

    private void setTargetMobileApp(String targetMobileApp) {
        TargetMobileApp = targetMobileApp;
    }

    private void setParallelRuns(boolean parallelRuns) {
        ParallelRuns = parallelRuns;
    }

}
