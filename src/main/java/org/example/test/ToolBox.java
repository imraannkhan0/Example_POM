package org.example.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;


@SuppressWarnings("unused")
public class ToolBox {

    private String operatingsystem = System.getProperty("os.name").toLowerCase();
    //private static Initialize initialize = new Initialize("./Data/configuration");

    public ToolBox() {

    }

    public void printcurrentdirectory(Object classname) {
        URL location = Object.class.getProtectionDomain()
                .getCodeSource().getLocation();
        System.out.println(location.getFile());
        System.out.println(System.getProperty("user.dir"));
    }

    public boolean CheckIfFileexists(String Pathoffile) {
        boolean result = false;
        File f = new File(Pathoffile);
        if (f.exists()) {
            result = true;
        } else {
            result = false;
        }

        return result;
    }

    public String readFileAsString(String filePath) throws IOException {
        //log.info("Begining reading file '" + filePath + "'");
        // StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        //	String fileAsString=FileUtils.readFileToString(new File(filePath));
        // log.info("File content is : "+IOUtils.toString(reader));

        return IOUtils.toString(reader);
    }
    /*public String commaparser()
	{
		Map<String, String> valuesMap = new HashMap<String, String>();
		valuesMap.put("0", ",");
		String templateString = "//div[contains(@class${0}'header')]/div/a/div[@class='badge bagcount']";
		StrSubstitutor sub = new StrSubstitutor(valuesMap);
		String resolvedString = sub.replace(templateString);
		return resolvedString;
	}*/

    public void PrintList(List<?> Listtoprint) {
        String str = "";
        for (Object object : Listtoprint) {
            str = str + object.toString() + ",";
        }
        //log.info(str);
    }

    public void getbasefolderpath() {
        OS osname = getosname();
        switch (osname) {
            case LINUX:

                break;

            default:
                break;
        }
    }

    public OS getosname() {
        if (isWindows()) {
            return OS.WINDOWS;
        } else if (isMac()) {
            return OS.MACOS;
        } else if (isUnix()) {
            return OS.LINUX;
        } else if (isSolaris()) {
            return OS.SOLARIS;
        } else {
            return null;
        }
    }

    private boolean isWindows() {

        return (operatingsystem.indexOf("win") >= 0);

    }

    private boolean isMac() {

        return (operatingsystem.indexOf("mac") >= 0);

    }

    private boolean isUnix() {

        return (operatingsystem.indexOf("nix") >= 0
                || operatingsystem.indexOf("nux") >= 0 || operatingsystem
                .indexOf("aix") > 0);

    }

    private boolean isSolaris() {

        return (operatingsystem.indexOf("sunos") >= 0);

    }

    public static String getWebPageDOM(String URL) {
        String response = null;
        try {
            InputStreamReader is = curler(URL);
            response = IOUtils.toString(is);
        } catch (IOException e) {
//			log.error("Exception occurred whil reading the webpage as DOM"
//					+ e.getMessage());

        }
        return response;
    }

    private static InputStreamReader curler(String URL) throws IOException {
        String cmd = "curl -s " + URL;
        String[] list = cmd.split(" ");
        ProcessBuilder pb = new ProcessBuilder(list);
        Process p = pb.start();
        InputStreamReader is = new InputStreamReader(p.getInputStream());
        return is;
    }

    public static BufferedReader getWebPageDOMByLine(String URL) {
        InputStreamReader is = null;
        try {
            is = curler(URL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new BufferedReader(is);
    }

    public static Object[][] returnReducedDataSet(Object[][] dataSet,
                                                  String[] includedGroups, int miniRegressionDpCount,
                                                  int regressionDpCount) {
        if (ArrayUtils.contains(includedGroups, "ExhaustiveRegression")) {
            // Don't remove any of the data
        } else if (ArrayUtils.contains(includedGroups, "Regression")) {
            dataSet = removeNItemsFromArray(dataSet, regressionDpCount);
        } else
            dataSet = removeNItemsFromArray(dataSet, miniRegressionDpCount);
        return dataSet;
    }

    public static Object[][] returnReducedDataSet(String Env,
                                                  Object[][] dataSet, String[] includedGroups,
                                                  int miniRegressionDpCount, int regressionDpCount) {
        if (Env != null)
            dataSet = returnEnvSpecificDataSet(Env, dataSet);
        else
            dataSet = returnDataSetIfEnvNull(dataSet);
        if (ArrayUtils.contains(includedGroups, "ExhaustiveRegression")) {
            // Don't remove any of the data
        } else if (ArrayUtils.contains(includedGroups, "Regression")) {
            dataSet = removeNItemsFromArray(dataSet, regressionDpCount);
        } else
            dataSet = removeNItemsFromArray(dataSet, miniRegressionDpCount);
        return dataSet;
    }

    public static Object[][] removeNItemsFromArray(Object[][] dataSet,
                                                   int finalArrayCount) {
        Random random = new Random();
        for (int i = dataSet.length - finalArrayCount; i > 0; i--) {
            int indexToBeRemoved = random.nextInt(dataSet.length);
            dataSet = ArrayUtils.remove(dataSet, indexToBeRemoved);
        }
        return dataSet;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static Object[][] returnEnvSpecificDataSet(String Env,
                                                       Object[][] dataSet) {
        List dataSetAsList = Arrays.asList(dataSet);
        ArrayList dataSetOfEnv = new ArrayList();
        int index = 0;
        for (int i = 0; i < dataSetAsList.size(); i++) {
            String[] test = (String[]) dataSetAsList.get(i);
            // System.out.println(test[0]);
            if (test[0].toUpperCase().contains(Env.toUpperCase())) {
                dataSetOfEnv.add(index,
                        ArrayUtils.subarray(test, 1, test.length));
                index++;
            }
        }
        Object[][] newDataSet = new Object[dataSetOfEnv.size()][];
        for (int i = 0; i < dataSetOfEnv.size(); i++) {
            newDataSet[i] = (Object[]) dataSetOfEnv.get(i);

        }

        return newDataSet;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static Object[][] returnDataSetIfEnvNull(Object[][] dataSet) {
        List dataSetAsList = Arrays.asList(dataSet);
        ArrayList dataSetOfEnv = new ArrayList();
        int index = 0;
        for (int i = 0; i < dataSetAsList.size(); i++) {
            String[] test = (String[]) dataSetAsList.get(i);
            dataSetOfEnv.add(index, ArrayUtils.subarray(test, 1, test.length));
            index++;
        }
        Object[][] newDataSet = new Object[dataSetOfEnv.size()][];
        for (int i = 0; i < dataSetOfEnv.size(); i++) {
            newDataSet[i] = (Object[]) dataSetOfEnv.get(i);
        }

        return newDataSet;
    }

    public static String getFullyQualifiedUrl(String url) {
        String fullyQualifiedUrl = "";
        if (url.contains("http://") && url.contains(".myntra.com")) {
            fullyQualifiedUrl = url;
        } else if (url.contains(".myntra.com")) {
            fullyQualifiedUrl = "http://" + url;
        } else {
            fullyQualifiedUrl = "http://" + url + ".myntra.com";
        }
        return fullyQualifiedUrl;
    }

    public void runAdbCommands(String command){
        try {


            ProcessBuilder ps = new ProcessBuilder(command);
            Process p = ps.start();

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}







