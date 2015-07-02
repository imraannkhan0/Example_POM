package org.example.test;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Requestvalidator {

    private JsonElement request;
    private String payload;
    private APIUtilities utilities = new APIUtilities();

    public Requestvalidator(String Payload, PayloadType type) {
        payload = Payload;
        if (type == PayloadType.XML) {
            //payload = Payload;
        } else if (type == PayloadType.URLENCODED) {

        } else {
            request = new JsonParser().parse(Payload);

        }
    }

    public void Requestvalidator(String Payload) {
        request = new JsonParser().parse(Payload);
        payload = Payload;
    }


    public String ReadData(String NodePathWithDots) {
        return utilities.ReadJsonData(NodePathWithDots, payload);
    }


    public boolean DoesNodeExists(String Nodename) {
        return utilities.Exists(Nodename, payload);
    }

}
