package org.example.test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.codehaus.jackson.map.ObjectMapper;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map.Entry;

public class RequestGenerator {

    private WebTarget target;
    private ClientConfig config;
    private Client client;
    public Response response;
    public ResponseValidator respvalidate;
    public Requestvalidator request;
    @SuppressWarnings("unused")
    private String typeofdatarequest;
    private String typeofdataresponse;
    private CreateServices serviceundertest;
    private DataType RequestDataType = DataType.JSON;
    private DataType ResponseDataType = DataType.JSON;

    private validator reqvalidations = null;
    public validationActions RequestValidations;
    private validator resvalidations;
    public validationActions ResponseValidations;

    public RequestGenerator(CreateServices service) {
        serviceundertest = service;
        this.PostResponse(service, service.Payload);
    }

    public RequestGenerator(CreateServices service, HashMap<String, String> headers) {
        serviceundertest = service;
        this.PostResponse(service, service.Payload, headers);
    }

    public RequestGenerator(CreateServices service, String payload) {
        serviceundertest = service;
        this.PostResponse(service, payload);
    }


    private void PostResponse(CreateServices service, String payload) {

        service.Payload = payload;
        if (service.PayloadRequired) {
            request = new Requestvalidator(service.Payload, service.payloaddataformat);
        }

        config = new ClientConfig();
        client = ClientBuilder.newClient(config);
        target = client.target(service.URL);
        String typeofdatarequest = MediaType.APPLICATION_JSON; // defaults the
        // value to JSON
        // response
        String typeofdataresponse = MediaType.APPLICATION_JSON;

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

        Invocation.Builder invocationBuilder = target.request(typeofdatarequest);
        CollectResponse(service, invocationBuilder, payload);
        //respvalidate = new ResponseValidator(response, service.responsedataformat);
        reqvalidations = new validator(service.Payload, RequestDataType);
        RequestValidations = reqvalidations.getValidator();
        resvalidations = new validator(returnresponseasstring(), ResponseDataType);
        ResponseValidations = resvalidations.getValidator();
        response.bufferEntity();
        respvalidate = new ResponseValidator(response, service.responsedataformat);
        //logserviceinfo(service);

    }

    @SuppressWarnings("unused")
    private void PostResponse(CreateServices service, String payload, HashMap<String, String> headers) {

        if (service.PayloadRequired) {
            request = new Requestvalidator(service.Payload, service.payloaddataformat);
        }

        config = new ClientConfig();
        client = ClientBuilder.newClient(config);
        target = client.target(service.URL);
        //String typeofdatarequest = MediaType.APPLICATION_JSON; // defaults the
        // value to JSON
        // response
        //String
        typeofdataresponse = MediaType.APPLICATION_JSON;

        if (service.payloaddataformat == PayloadType.JSON) {
            typeofdatarequest = MediaType.APPLICATION_JSON;
            RequestDataType = DataType.JSON;
        } else if (service.payloaddataformat == PayloadType.URLENCODED) {
            typeofdatarequest = MediaType.APPLICATION_FORM_URLENCODED;
            RequestDataType = DataType.URLENCODED;

        } else if (service.payloaddataformat == PayloadType.FORMDATA) {
            typeofdatarequest = MediaType.MULTIPART_FORM_DATA;
            RequestDataType = DataType.REQUEST_FORMDATA;
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

        //logserviceinfo(service);

        // Invocation invocation = null;
        //log.info("invocation builder initialization="+typeofdatarequest);
        Invocation.Builder invocationBuilder = target.request(typeofdataresponse);


        for (Entry<String, String> entry : headers.entrySet()) {
            Object value = entry.getValue();
            invocationBuilder.header(entry.getKey(), entry.getValue());
        }


        CollectResponse(service, invocationBuilder, payload);
        reqvalidations = new validator(service.Payload, RequestDataType);
        RequestValidations = reqvalidations.getValidator();
        resvalidations = new validator(returnresponseasstring(), ResponseDataType);
        ResponseValidations = resvalidations.getValidator();
        //log.info("debug: responsedataformat = "+service.responsedataformat);
        response.bufferEntity();
        respvalidate = new ResponseValidator(response, service.responsedataformat);
        //logserviceinfo(service);

    }

    public void CollectResponse(CreateServices service, Invocation.Builder invocationBuilder, String payload) {

        //String typeofdataresponse = MediaType.APPLICATION_JSON;
        if (service.responsedataformat == PayloadType.XML) {
            typeofdataresponse = MediaType.APPLICATION_XML;
        } else {
            typeofdataresponse = MediaType.APPLICATION_JSON;
        }

//        log.info("debug:typeofdataresponse="+typeofdataresponse);
//        log.info("debug:typeofdatarequest="+typeofdatarequest);
        //log.info("debug:typeofdataresponse="+typeofdataresponse);
        //log.info("debug:typeofdataresponse="+typeofdataresponse);

        if (service.payloaddataformat == PayloadType.JSON) {
            //   log.info("this is json path");
            switch (service.RequestSendMethod) {
                case POST:
                    //   log.info("posting the json request payload");
                    response = invocationBuilder.accept(typeofdataresponse).post(Entity.json(payload));
                    break;
                case GET:
                    response = invocationBuilder.get();
                    break;
                case PUT:
                    response = invocationBuilder.accept(typeofdataresponse).put(Entity.json(payload));
                    break;
                case DELETE:
                    response = invocationBuilder.accept(typeofdataresponse).delete();
                    break;
                default:
                    response = invocationBuilder.get();
                    break;
            }

        } else if (service.payloaddataformat == PayloadType.URLENCODED) {
            switch (service.RequestSendMethod) {
                case POST:
                    //  log.info("posting the urlencoded request payload");
                    response = invocationBuilder.accept(typeofdataresponse).post(Entity.text(payload));
                    break;

                default:
                    response = invocationBuilder.get();
                    break;
            }
        } else if (service.payloaddataformat == PayloadType.FORMDATA) {
            switch (service.RequestSendMethod) {
                case POST:
                    //	response=invocationBuilder.accept(MediaType.MULTIPART_FORM_DATA).post(Entity.form(formData));
                default:
                    break;
            }
        } else {
            //   log.info("this is xml path");
            switch (service.RequestSendMethod) {
                case POST:
                    //  log.info("posting the xml request payload");
                    response = invocationBuilder.accept(typeofdataresponse).post(Entity.xml(payload));
                    break;
                case GET:
                    response = invocationBuilder.get();
                    break;
                case PUT:
                    //System.out.println("payload = " + payload);
                    response = invocationBuilder.accept(typeofdataresponse).put(Entity.xml(payload));
                    break;
                case DELETE:
                    response = invocationBuilder.accept(typeofdataresponse).delete();
                    break;

                default:
                    response = invocationBuilder.get();
                    break;
            }
        }
    }

    public String returnresponseasstring() {
        String returntype = null;
        ObjectMapper mapper = null;
        response.bufferEntity();
        returntype = response.readEntity(String.class);

        if (returntype.length() > 0) {
            try {
                if (serviceundertest.responsedataformat == PayloadType.JSON) {
                    //     log.info("this is considered to be a JSON response");
                    mapper = new ObjectMapper();
                    Object json = mapper.readValue(returntype, Object.class);
                    returntype = mapper.writerWithDefaultPrettyPrinter
                            ().writeValueAsString(json);
                } else if (serviceundertest.responsedataformat == PayloadType.XML) {


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //    log.info("Printing response from service :"+returntype);
        //return gson.toJson(jO);
        return returntype;
    }


}
