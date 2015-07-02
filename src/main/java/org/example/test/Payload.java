package org.example.test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Payload {

    Configuration config;
    APIUtilities utilities = new APIUtilities();
    public APINAME apifortest;
    public String filecontent = "";
    //	static Logger log = Logger.getLogger(Payload.class);
    public PayloadType payloaddataformat = PayloadType.JSON;


    public Payload(APINAME APINAME, Configuration configuration, PayloadType PayloadDataFormat) {
        String filepath;
        try {
            config = configuration;
            apifortest = APINAME;
            payloaddataformat = PayloadDataFormat;
            filepath = GetPayloadFileFullPath();
            File file = new File(filepath);
            if (file.exists()) {
                filecontent = utilities.readFileAsString(filepath);
            } else {
                flippayloadtype(filepath);
                filepath = GetPayloadFileFullPath();
                filecontent = utilities.readFileAsString(filepath);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Payload(APINAME APINAME, String[] parameterstopayload, Configuration configuration) {
        try {
            config = configuration;
            apifortest = APINAME;
            filecontent = utilities.preparepayload(utilities.readFileAsString(GetPayloadFileFullPath()), parameterstopayload);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Payload(APINAME APINAME, String[] parameterstopayload, PayloadType type, Configuration configuration) {

        try {
            config = configuration;
            payloaddataformat = type;
            apifortest = APINAME;
            filecontent = utilities.preparepayload(utilities.readFileAsString(GetPayloadFileFullPath()), parameterstopayload);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String PayloadfromFile() {
        try {

            return utilities.readFileAsString(GetPayloadFileFullPath());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String ParamatereizedPayload(String[] Paramateres) {
        String payload = PayloadfromFile();
        return prepareparameterizedpayload(payload, Paramateres);

    }

    private String prepareparameterizedpayload(String Payload, String[] parameterstoreplace) {
        return utilities.preparepayload(Payload, parameterstoreplace);
    }

    private void flippayloadtype(String filepath) {

        switch (payloaddataformat) {

            case JSON:
                payloaddataformat = PayloadType.XML;
                break;
            case XML:
                payloaddataformat = PayloadType.JSON;
                break;
            case FORMDATA:
                payloaddataformat = PayloadType.FORMDATA;
            default:
                payloaddataformat = PayloadType.JSON;
        }
    }

    private String GetPayloadFileFullPath() {
        String[] payloadFile = new File(config.PayloadPath(payloaddataformat)).list();
        String fileName = "";
        for (String fileNameFromDir : payloadFile) {
            if (fileNameFromDir.equalsIgnoreCase(apifortest.toString()))
                fileName = fileNameFromDir;
        }
        String path = config.PayloadPath(payloaddataformat) + fileName;
        //log.info(path);
        return path;
    }
}
