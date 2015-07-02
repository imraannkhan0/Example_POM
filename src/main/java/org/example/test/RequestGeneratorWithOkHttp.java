package org.example.test;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.squareup.okhttp.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sabyasachi on 6/6/2015.
 */
public class RequestGeneratorWithOkHttp {

    public static void main(String[] args) throws IOException, UnirestException {
        OkHttpClient client = new OkHttpClient();
        Map<String, String> map = new HashMap<>();
        map.put("content-type", "application/json");
        map.put("accept", "application/json");

        Headers headers = Headers.of(map);

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,
                "{\n  \"userId\":\"devapitesting100@myntra.com\",\n  \"password\":\"123456\",\n  \"action\":\"signin\"\n  \n}");
        Request request = new Request.Builder()
                .url("http://developer.myntra.com/idp")
                .post(body)
                .headers(headers)
                .build();
        Response response = client.newCall(request).execute();

        //  System.out.println(response.body().string());

        //   new RequestGeneratorWithOkHttp().unirestTest();

    }

    public void unirestTest() throws UnirestException {
        Map<String, Object> data = new HashMap<>();
        data.put("parameter", "value");
        data.put("foo", "bar");

        HttpResponse<JsonNode> jsonResponse = Unirest.post("http://httpbin.org/post")
                .header("accept", "application/json")
                .queryString("apiKey", "123")
                .fields(data)
                .asJson();

        System.out.println(jsonResponse.getBody().toString());

    }
}

