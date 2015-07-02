package org.example.test;

import java.io.File;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

public class Configuration {

    public PropertyReader properties;
    public String configurationfolderpath;
    public String preprodfoldername;
    public String productionfoldername;
    public String functionalfoldername, performancefoldername;
    public String environmentfilename = "environment.xml";
    public String uielementxmlname = "UIelements.xml";
    //private String commondatafilename = "./commondata.xml";
    // private String ConfigurationFilepath;
    public XMLConfiguration config, uielementxmlconfig, commondataconfig, mobileappuielementxmlconfig;
    public XMLConfiguration environmentfile;
    //static Logger log = Logger.getLogger(Configuration.class);
    Environment testenvironment;
    public String mobileappuielementxmlname = "mobileAppUIElements.xml";
    public TestRunData frameworkData = new TestRunData();


    public Configuration() {
        properties = new PropertyReader();
        testenvironment = GetTestEnvironemnt();
        configurationfolderpath = properties
                .getPropertyValue("configurationbasefolder");
        preprodfoldername = properties.getPropertyValue("preprodfoldername");
        productionfoldername = properties
                .getPropertyValue("productionfoldername");
        functionalfoldername = properties
                .getPropertyValue("functionalfoldername");
        performancefoldername = properties
                .getPropertyValue("performancefoldername");
        try {
            config = new XMLConfiguration(
                    GetConfigurationFilepath(testenvironment));
            environmentfile = config;
            uielementxmlconfig = new XMLConfiguration();
            uielementxmlconfig.setDelimiterParsingDisabled(true); // To ignore
            // commas
            // mentioned
            // as values
            // for
            // attributes.
            //uielementxmlconfig.load(GetUIElementsxmlpath());

            mobileappuielementxmlconfig = new XMLConfiguration();
            mobileappuielementxmlconfig.setDelimiterParsingDisabled(true);
            //mobileappuielementxmlconfig.load(GetMobileAppUIElementsxmlpath()); //Loading the Mobileapp UI elements.

            // printallfilesincurrentdirectory();
            // System.out.println(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
            // commondataconfig.load(commondatafilename);
            // System.out.println(commondataconfig.getString("email.smtpservername"));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    public Configuration(String Propertiesfilepath) {
        properties = new PropertyReader(Propertiesfilepath);
        configurationfolderpath = Propertiesfilepath;
        testenvironment = GetTestEnvironemnt();
        preprodfoldername = properties.getPropertyValue("preprodfoldername");
        productionfoldername = properties
                .getPropertyValue("productionfoldername");
        functionalfoldername = properties
                .getPropertyValue("functionalfoldername");
//        performancefoldername = properties
//                .getPropertyValue("performancefoldername");
        try {
            config = new XMLConfiguration(GetConfigurationFilepath(
                    testenvironment, configurationfolderpath));
            environmentfile = config;
            uielementxmlconfig = new XMLConfiguration();
            uielementxmlconfig.setDelimiterParsingDisabled(true); // To ignore
            // commas
            uielementxmlconfig.load(GetUIElementsxmlpath(configurationfolderpath));

            mobileappuielementxmlconfig = new XMLConfiguration();
            mobileappuielementxmlconfig.setDelimiterParsingDisabled(true);
            if (new File(GetMobileAppUIElementsxmlpath(configurationfolderpath)).exists())
                mobileappuielementxmlconfig.load(GetMobileAppUIElementsxmlpath(configurationfolderpath));//Loading the Mobileapp UI elements.

        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    private String GetUIElementsxmlpath() {
        String configurationfilepath = "";

        configurationfilepath = configurationfolderpath + "/"
                + uielementxmlname;

        return configurationfilepath;
    }

    private String GetMobileAppUIElementsxmlpath() {
        String configurationfilepath = "";

        configurationfilepath = configurationfolderpath + "/"
                + mobileappuielementxmlname;

        return configurationfilepath;
    }


    private String GetMobileAppUIElementsxmlpath(String Basefolderpath) {
        String configurationfilepath = "";
        configurationfolderpath = Basefolderpath;
        configurationfilepath = configurationfolderpath + "/"
                + mobileappuielementxmlname;
        return configurationfilepath;
    }

    private String GetUIElementsxmlpath(String Basefolderpath) {
        String configurationfilepath = "";
        configurationfolderpath = Basefolderpath;
        configurationfilepath = configurationfolderpath + "/"
                + uielementxmlname;
        return configurationfilepath;
    }

    public Environment GetTestEnvironemnt() {
        String environment = System.getProperty("environment");
        if (environment == null)
            environment = System.getenv("environment");
        if (null == environment) {
            environment = Gettestenvironment();
        }
        System.out.println("testenv = " + environment);
        Environment testenv = Environment.valueOf(environment.toUpperCase());
        return testenv;
    }

    private String Gettestenvironment() {
        String returnval = properties.getPropertyValue("environment");
        return returnval;
    }

    private String GetConfigurationFilepath(Environment testenvironment) {

        String configurationfilepath = "";
        switch (testenvironment) {
            case PREPROD:
                configurationfilepath = configurationfolderpath + "/"
                        + preprodfoldername + "/" + environmentfilename;
                break;
            case PRODUCTION:
                configurationfilepath = configurationfolderpath + "/"
                        + productionfoldername + "/" + environmentfilename;
                break;
            case QA:
                configurationfilepath = configurationfolderpath + "/"
                        + functionalfoldername + "/" + environmentfilename;
                break;

            default:
                System.out
                        .println("Not a valid enum value for testenvironment, proceeding with the default selection preprod environment.");
                configurationfilepath = configurationfolderpath + "/"
                        + preprodfoldername + "/" + environmentfilename;
                break;
        }
        return configurationfilepath;
    }

    public String GetConfigurationFilepath(Environment testenvironment,
                                           String BasefolderPath) {

        String configurationfilepath = "";
        configurationfolderpath = BasefolderPath;
        switch (testenvironment) {
            case QA:
                configurationfilepath = configurationfolderpath + "/"
                        + functionalfoldername + "/" + environmentfilename;
                break;
            case PRODUCTION:
                configurationfilepath = configurationfolderpath + "/"
                        + productionfoldername + "/" + environmentfilename;
                break;
            case PREPROD:
                configurationfilepath = configurationfolderpath + "/"
                        + preprodfoldername + "/" + environmentfilename;
                break;

            default:
                System.out
                        .println("Not a valid enum value for testenvironment, proceeding with the default selection preprod environment.");
                configurationfilepath = configurationfolderpath + "/"
                        + preprodfoldername + "/" + environmentfilename;
                break;
        }
        return configurationfilepath;
    }

    public List<HierarchicalConfiguration> GetAllEndPointsDetails() {
        @SuppressWarnings("unchecked")
        List<HierarchicalConfiguration> returnval = config
                .configurationsAt("services.endpoints.endpoint");
        return returnval;
    }

    @SuppressWarnings("unused")
    private String Getvalue(String Nodename) {
        String value = config.getString(Nodename);
        return value;
    }

    public PLATFORM GetPlatform() {
        return PLATFORM.valueOf(ReadProperty("platform").toUpperCase());
    }

    public void SetProperty(String key, String value) {
        properties.SetProperty(key, value);
    }

    public String ReadProperty(String propertykey) {
        return properties.getPropertyValue(propertykey);

    }

    @SuppressWarnings("unused")
    private void printallfilesincurrentdirectory() {
        String path = ".";

        String files;
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            files = listOfFiles[i].getName();
            System.out.println(files);
        }
    }

    public String ReadConfiguration(String configurationname) {
        return config.getString(configurationname);
    }

    @SuppressWarnings("unchecked")
    public List<HierarchicalConfiguration> ReadAttributeValues(String field,
                                                               String attributename) {
        List<HierarchicalConfiguration> returnval = config
                .configurationsAt(field);
        // printobject(returnval, attributename);
        return returnval;
    }

    public String ReadAttributevalue(HierarchicalConfiguration configuration,
                                     String Attributename) {
        String value = configuration.getString("[@" + Attributename + "]");
        return value;
    }

    public String PayloadPath(PayloadType PayloadtypeinRequest) {
        String datafolder = "";
        switch (PayloadtypeinRequest) {
            case JSON:
                datafolder = config.getString("framework.paths.jsonpayloads");
                break;
            case XML:
                datafolder = config.getString("framework.paths.xmlpayloads");
                break;
            case URLENCODED:
                datafolder = config.getString("framework.paths.urlencodedpayloads");
                break;
        }
        return datafolder;
    }

    @SuppressWarnings("unchecked")
    public List<HierarchicalConfiguration> GetAllAPIElements(String NodePath) {
        List<HierarchicalConfiguration> returnval = config.configurationsAt(NodePath);

        return returnval;
    }

    public String GetPortalHomeUrl() {
        String portalHome = Getvalue("urls.portal_home");
        if (testenvironment.equals(Environment.QA)) {
            String qa = System.getenv("qa");
            if (null != qa) {
                portalHome = ToolBox.getFullyQualifiedUrl(qa);
            }
        }
        //log.info("Portal Home : " + portalHome);
        return portalHome;
    }

    public String getPortalUrl() {
        String portalHome;
        Environment env = GetTestEnvironemnt();
        switch (env) {
            case PRODUCTION:
                portalHome = properties.getPropertyValue("productionurl");
                break;
            case PREPROD:
                portalHome = properties.getPropertyValue("preprodurl");
                break;
            case QA:
                portalHome = properties.getPropertyValue("qaurl");
                break;
            default:
                portalHome = properties.getPropertyValue("preprodurl");
        }
        return portalHome;
    }

    @SuppressWarnings("unchecked")
    public List<HierarchicalConfiguration> GetAllUIElements() {
        List<HierarchicalConfiguration> returnval = uielementxmlconfig
                .configurationsAt("Element");
        List<HierarchicalConfiguration> mobileappval = mobileappuielementxmlconfig // to get elements from the MobileAppUIelements.xml
                .configurationsAt("Element");
        returnval.addAll(mobileappval); // Putting all the elements from MobileAppUIelements to the UIelements.

        return returnval;
    }

    String GetJenkinsurl() {
        String returnval = properties.getPropertyValue("jenkinurl");
        return returnval;
    }


}
