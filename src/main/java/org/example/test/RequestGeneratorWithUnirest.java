package org.example.test;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sabyasachi on 6/6/2015.
 */
public class RequestGeneratorWithUnirest {

    public HttpResponse<?> response;
    public GetRequest req;
    public ResponseValidator respvalidate;
    public Requestvalidator request;

    private String typeofdatarequest;
    private String typeofdataresponse;
    private CreateServices serviceundertest;
    private DataType RequestDataType = DataType.JSON;
    private DataType ResponseDataType = DataType.JSON;

    private validator reqvalidations = null;
    public validationActions RequestValidations;
    private validator resvalidations;
    public validationActions ResponseValidations;
    String payload;
    public Map<String, String> headers;

    public RequestGeneratorWithUnirest(CreateServices service) throws Exception {
        serviceundertest = service;
        this.PostResponse(service);

    }

    public RequestGeneratorWithUnirest(CreateServices service, Map<String, String> headers) throws Exception {

        serviceundertest = service;
        //this.PostResponse(service, headers);
        this.PostResponse(service, headers);
    }

    private void PostResponse(CreateServices service, Map<String, String> headers) throws Exception {
        service.Payload = payload;
        if (service.PayloadRequired) {
            request = new Requestvalidator(service.Payload, service.payloaddataformat);
        }
        if (service.payloaddataformat == PayloadType.JSON) {
//            typeofdatarequest = MediaType.APPLICATION_JSON;
//            RequestDataType = DataType.JSON;
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json");

        } else if (service.payloaddataformat == PayloadType.URLENCODED) {
//            typeofdatarequest = MediaType.APPLICATION_FORM_URLENCODED;
//            RequestDataType = DataType.URLENCODED;
            headers.put("Accept", "application/x-www-form-urlencoded");
            headers.put("Content-Type", "application/x-www-form-urlencoded");
        } else {
//            typeofdatarequest = MediaType.APPLICATION_XML;
//            RequestDataType = DataType.XML;
            headers.put("Accept", "application/xml");
            headers.put("Content-Type", "application/xml");
        }

        if (service.responsedataformat == PayloadType.JSON) {
            typeofdataresponse = MediaType.APPLICATION_JSON;
            ResponseDataType = DataType.JSON;
        } else {
            typeofdataresponse = MediaType.APPLICATION_XML;
            ResponseDataType = DataType.XML;
        }
        CollectResponse(service, payload, headers);

    }


    private void PostResponse(CreateServices service) throws Exception {
        payload = service.Payload;
        if (service.PayloadRequired) {
            request = new Requestvalidator(service.Payload, service.payloaddataformat);

        }

        if (service.payloaddataformat == PayloadType.JSON) {
            typeofdatarequest = MediaType.APPLICATION_JSON;
            RequestDataType = DataType.JSON;
        } else if (service.payloaddataformat == PayloadType.URLENCODED) {
            typeofdatarequest = MediaType.APPLICATION_FORM_URLENCODED;
            RequestDataType = DataType.URLENCODED;
        } else {
            typeofdatarequest = MediaType.APPLICATION_XML;
            RequestDataType = DataType.XML;
        }

        if (service.responsedataformat == PayloadType.JSON) {
            typeofdataresponse = MediaType.APPLICATION_JSON;
            ResponseDataType = DataType.JSON;
        } else {
            typeofdataresponse = MediaType.APPLICATION_XML;
            ResponseDataType = DataType.XML;
        }
        CollectResponse(service, payload);


    }

    private void CollectResponse(CreateServices service, String payload) throws Exception {

        headers = new HashMap<String, String>();
        //   log.info("this is json path");
        if (service.payloaddataformat == PayloadType.JSON) {
            headers.put("Content-Type", "application/json");
            headers.put("Accept", "application/json");
            switch (service.RequestSendMethod) {

                case GET:
                    response = Unirest.get(service.URL).asString();
                    break;
                case PUT:
                    response = Unirest.put(service.URL).headers(headers).body(payload).asString();
                    break;
                case DELETE:
                    //To be verified
                    if (service.RequestSendMethod.name().toLowerCase().equals("delete") && service.PayloadRequired)
                        response = Unirest.delete(service.URL).headers(headers).body(payload).asString();
                    else
                        response = Unirest.delete(service.URL).headers(headers).asString();
                    break;
                case POST:
                    response = Unirest.post(service.URL).headers(headers).body(payload).asString();
                    break;
                default:
                    response = Unirest.get(service.URL).asString();

            }

        } else if (service.payloaddataformat == PayloadType.URLENCODED) {
            headers.put("Accept", "application/x-www-form-urlencoded");
            headers.put("Content-Type", "application/x-www-form-urlencoded");
            switch (service.RequestSendMethod) {
                case POST:
                    response = Unirest.post(service.URL).headers(headers).body(payload).asString();
                default:
                    response = Unirest.get(service.URL).asString();
            }
        } else if (service.payloaddataformat == PayloadType.XML) {
            headers.put("Accept", "application/xml");
            headers.put("Content-Type", "application/xml");

            switch (service.RequestSendMethod) {
                case POST:

                    response = Unirest.post(service.URL).headers(headers).body(payload).asString();
                    break;
                case GET:
                    response = Unirest.get(service.URL).asString();
                    break;
                case PUT:
                    response = Unirest.put(service.URL).headers(headers).body(payload).asString();
                    break;
                case DELETE:
                    response = Unirest.delete(service.URL).headers(headers).asString();
                    break;
                default:
                    response = Unirest.get(service.URL).asString();

            }
        }

    }

    private void CollectResponse(CreateServices service, String payload, Map<String, String> headers) throws Exception {

        if (service.payloaddataformat == PayloadType.JSON) {
            switch (service.RequestSendMethod) {

                case GET:
                    response = Unirest.get(service.URL).headers(headers).asString();

                    break;
                case PUT:
                    response = Unirest.put(service.URL).headers(headers).body(payload).asString();
                    break;
                case DELETE:
                    response = Unirest.delete(service.URL).headers(headers).asJson();
                    break;
                case POST:
                    response = Unirest.post(service.URL).headers(headers).body(payload).asString();
                    break;
                default:
                    response = Unirest.get(service.URL).headers(headers).asString();

            }

        }
    }

    public String returnresponseasstring() {
        String returntype = null;
        ObjectMapper mapper = null;
        returntype = response.getBody().toString();
        if (returntype.length() > 0) {
            try {
                if (serviceundertest.responsedataformat == PayloadType.JSON) {
                    //     log.info("this is considered to be a JSON response");
                    mapper = new ObjectMapper();
                    Object json = mapper.readValue(returntype, Object.class);
                    returntype = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return returntype;
    }
}
