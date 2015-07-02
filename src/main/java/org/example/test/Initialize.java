package org.example.test;

import org.example.test.common.UIElements;

public class Initialize {
    public Configuration Configurations;
    public TestEnvironmentDetails TestEnvironment;
    public boolean requireProxy = false;

    //	public MyntraDatabases MyntraDatabases;
    public UIElements UIObjects;
    //	public TestEnvironmentDetails TestEnvironment;
//	static Logger log = Logger.getLogger(Initialize.class.getName());
//	public AlertLevel NotificationLevel = AlertLevel.LOW;
    Throwable t = new Throwable();


    public Initialize() {
        Configurations = new Configuration();
//		log.info("Configuration object prepared");
//		MyntraDatabases = new MyntraDatabases(Configurations);
//		log.info("MyntraDatabase initialized");
        UIObjects = new UIElements(Configurations);
//		log.info("UI Objects are successfully initialized");
        TestEnvironment = new TestEnvironmentDetails(Configurations);
//		log.info("Test Environment successfully initialized");
    }

    public Initialize(String ConfigurationFolderPath) {

        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        String callerClassName = elements[1].getClassName();
        Configurations = new Configuration(ConfigurationFolderPath);
        //System.out.println(callerClassName.toLowerCase());
        if (!callerClassName.toLowerCase().contains("apitest")) {
            UIObjects = new UIElements(Configurations);
        }
//		MyntraDatabases = new MyntraDatabases(Configurations);

        TestEnvironment = new TestEnvironmentDetails(Configurations);
    }
}
