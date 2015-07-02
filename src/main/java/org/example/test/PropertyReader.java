package org.example.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

    String propertiesfilepath = "testsettings.properties";
    String propertiesfilename = "testsettings.properties";
    Properties props = new Properties();

    FileInputStream fis;
    InputStream is;
    private boolean isexternalfolder = true;
    private String FilepathFull = "";

    public PropertyReader() {
        try {
            FilepathFull = propertiesfilename;
            isexternalfolder = false;
            is = this.getClass().getResourceAsStream(propertiesfilename);
            props.load(is);

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public PropertyReader(String PathofBaseConfigurationFolder) {
        try {
            isexternalfolder = true;
            FilepathFull = PathofBaseConfigurationFolder + "/" + propertiesfilename;
            //log.info("properties file used for framework initialize is :"+ FilepathFull);
            fis = new FileInputStream(FilepathFull);
            props.load(fis);
        } catch (IOException e) {
            //log.error("exception occurred while reading properties file from path "+ PathofBaseConfigurationFolder + "/" + propertiesfilename);
            e.printStackTrace();
        }
    }

    public PropertyReader(String[] Pathofpropertiesfile) {
        try {
            for (String pathoffile : Pathofpropertiesfile) {
                fis = new FileInputStream(pathoffile);
                props.load(fis);
            }

        } catch (IOException e) {
            //	log.error("exception occurred while reading properties file from path ");
            e.printStackTrace();
        }
    }

    private void printvalues() {
        for (Object keys : props.keySet()) {
            System.out.println("Key = '" + keys.toString() + "' has value = '");
        }
    }

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


    private String readproperty(String propertyname) {
        return props.getProperty(propertyname);
    }

    public void SetProperty(String Key, String Value) {
        try {
            FileInputStream in = new FileInputStream(FilepathFull);
            Properties props = new Properties();
            props.load(in);
            in.close();

            FileOutputStream out = new FileOutputStream(FilepathFull);
            props.setProperty(Key, Value);
            props.store(out, null);
            out.close();

        } catch (Exception e) {
            //	log.info("Exception Occurred while setting property "+Key);
        }
    }

    public String getPropertyValue(String propertyname) {
        return readproperty(propertyname);
    }
}
