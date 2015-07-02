package org.example.test;


import org.apache.commons.configuration.HierarchicalConfiguration;

import java.util.HashMap;
import java.util.List;

import javax.ws.rs.core.MultivaluedHashMap;


public class CreateServices {
    public String URL;
    public String Payload;
    public Method RequestSendMethod;
    public String APIName;
    public String Core;
    public String ServiceName;
    public String BaseURL;
    public String AuthenticationRequired;
    public String UserID;
    public String Password;
    public String SecurityToken;
    public boolean QueryParamRequired = false;
    public boolean PayloadRequired = false;
    public HashMap<String, String> payloadAsFormdata;

    public PayloadType payloaddataformat = PayloadType.JSON;
    public PayloadType responsedataformat = PayloadType.JSON;


    private Configuration testenvconfiguration;
    private APIDetails apidetails;

    APIUtilities utilities = new APIUtilities();

    public CreateServices() {
//		URL = "http://www.myntra.com/searchws/search/styleids2";
//		RequestSendMethod = Method.POST;
//		APIName = "styleids2";

    }


    public CreateServices(ServiceType Servicename, APINAME apiundertest, Configuration config) {
        testenvconfiguration = config;
        apidetails = new APIDetails(Servicename, apiundertest.toString(), config);
        loadapidetails(apidetails);
        loadobjectdetails(Servicename, apiundertest);

        if (PayloadRequired) {
            Payload ld = new Payload(apiundertest, config, payloaddataformat);
            Payload = ld.filecontent;
        }
    }

    public CreateServices(ServiceType Servicename, APINAME apiundertest, Configuration config, PayloadType payloaddataformat, HashMap<String, String> formdata, PayloadType responseType) {
        testenvconfiguration = config;
        apidetails = new APIDetails(Servicename, apiundertest.toString(), config);
        loadapidetails(apidetails);
        loadobjectdetails(Servicename, apiundertest);

        if (PayloadRequired) {
            //Payload ld = new Payload(apiundertest,config,payloaddataformat);
            //Payload = ld.filecontent;
            payloadAsFormdata = formdata;
        }


    }


    public CreateServices(ServiceType Servicename, APINAME apiundertest, Configuration config, PayloadType PayloaddataFormat, PayloadType ResponseDataFormat) {
        testenvconfiguration = config;
        apidetails = new APIDetails(Servicename, apiundertest.toString(), config);
        loadapidetails(apidetails);
        loadobjectdetails(Servicename, apiundertest);
        payloaddataformat = PayloaddataFormat;
        responsedataformat = ResponseDataFormat;
        if (PayloadRequired) {
            Payload ld = new Payload(apiundertest, config, payloaddataformat);
            Payload = ld.filecontent;
        }


    }
    
    /*
     * Added for LMS
     */
   /* public CreateServices(String apiName,Method requestSendMethod,boolean payloadReq,boolean queryParamReq)
    {
		apidetails=new APIDetails();
		apidetails.APIname = apiName;
		apidetails.RequestMethod = requestSendMethod;
		apidetails.PayloadRequired = false;
		apidetails.QueryParamsRequired = false;
		
		loadapidetails(apidetails);
		apidetails.APIPath="/courier/getTrackingNumber/ML/2/true/110011";
	}*/

    public CreateServices(ServiceType Servicename, APINAME apiundertest, Configuration config, String CustomPayload) {
        apidetails = new APIDetails(Servicename, apiundertest.toString(), config);
        testenvconfiguration = config;
        loadapidetails(apidetails);
        loadobjectdetails(Servicename, apiundertest);
        Payload = CustomPayload;
    }


    public CreateServices(ServiceType Servicename, APINAME apiundertest, Configuration config, String[] PayloadParams) {
        apidetails = new APIDetails(Servicename, apiundertest.toString(), config);
        testenvconfiguration = config;
        loadapidetails(apidetails);
        loadobjectdetails(Servicename, apiundertest);
        if (PayloadRequired) {
            Payload ld = new Payload(apiundertest, PayloadParams, config);
            Payload = ld.filecontent;
        }


    }


    public CreateServices(ServiceType Servicename, APINAME apiundertest, Configuration config, String[] PayloadParams, String[] urlparams) {
        apidetails = new APIDetails(Servicename, apiundertest.toString(), config);
        testenvconfiguration = config;
        loadapidetails(apidetails);
        loadobjectdetails(Servicename, apiundertest);
        if (QueryParamRequired) {
            URL = utilities.prepareparameterizedURL(URL, urlparams);
        }

        if (PayloadRequired) {
            Payload ld = new Payload(apiundertest, PayloadParams, config);
            Payload = ld.filecontent;
        }


    }


    public CreateServices(ServiceType Servicename, APINAME apiundertest, Configuration config, String[] PayloadParams, String[] urlparams, PayloadType PayloadDataFormat, PayloadType ReponseAcceptDataFormat) {
        apidetails = new APIDetails(Servicename, apiundertest.toString(), config);
        testenvconfiguration = config;
        loadapidetails(apidetails);
        loadobjectdetails(Servicename, apiundertest);
        payloaddataformat = PayloadDataFormat;
        responsedataformat = ReponseAcceptDataFormat;

        if (QueryParamRequired) {
            URL = utilities.prepareparameterizedURL(URL, urlparams);
        }

        if (PayloadRequired) {
            Payload ld = new Payload(apiundertest, PayloadParams, payloaddataformat, config);
            Payload = ld.filecontent;
        }
    }


    public CreateServices(ServiceType Servicename, APINAME apiundertest, Configuration config, String[] PayloadParams, PayloadType PayloadDataFormat, PayloadType ReponseAcceptDataFormat) {

        apidetails = new APIDetails(Servicename, apiundertest.toString(), config);
        testenvconfiguration = config;
        loadapidetails(apidetails);
        loadobjectdetails(Servicename, apiundertest);
        payloaddataformat = PayloadDataFormat;
        responsedataformat = ReponseAcceptDataFormat;


        if (PayloadRequired) {
            Payload ld = new Payload(apiundertest, PayloadParams, PayloadDataFormat, config);

            Payload = ld.filecontent;
        }


    }


    public CreateServices(ServiceType Servicename, APINAME apiundertest, Configuration config, PayloadType PayloadDataFormat, String[] urlparams, PayloadType ReponseAcceptDataFormat) {

        apidetails = new APIDetails(Servicename, apiundertest.toString(), config);
        testenvconfiguration = config;
        loadapidetails(apidetails);
        loadobjectdetails(Servicename, apiundertest);
        payloaddataformat = PayloadDataFormat;
        responsedataformat = ReponseAcceptDataFormat;

        if (QueryParamRequired) {
            URL = utilities.prepareparameterizedURL(URL, urlparams);
        }

    }

    public CreateServices(ServiceType Servicename, APINAME apiundertest, Configuration config, String[] urlparams, PayloadType ReponseAcceptDataFormat) {

        apidetails = new APIDetails(Servicename, apiundertest.toString(), config);
        testenvconfiguration = config;
        loadapidetails(apidetails);
        loadobjectdetails(Servicename, apiundertest);
        //payloaddataformat = PayloadDataFormat;
        responsedataformat = ReponseAcceptDataFormat;

        if (QueryParamRequired) {
            URL = utilities.prepareparameterizedURL(URL, urlparams);
        }

    }


    public void loadobjectdetails(ServiceType Servicename, APINAME apiundertest) {
        String[] inforarray = Servicename.toString().split("_");
        String core = inforarray[0].toLowerCase();
        String servicename = inforarray[1].toLowerCase();

        loadendpointdetails(Getvaluefromxmlconfig(testenvconfiguration.GetAllEndPointsDetails(), servicename));
    }

    private void loadendpointdetails(EndpointDetails endpointdetailstoload) {
        AuthenticationRequired = endpointdetailstoload.AuthenticationRequired;
        BaseURL = endpointdetailstoload.BaseURL;
        Core = endpointdetailstoload.Core;
        Password = endpointdetailstoload.Password;
        SecurityToken = endpointdetailstoload.SecurityToken;
        ServiceName = endpointdetailstoload.ServiceName;
        UserID = endpointdetailstoload.UserID;
        URL = PrepareFullURL();
    }


    private void loadapidetails(APIDetails apidetails) {

        APIName = apidetails.APIname;
        System.out.println("Apiname=" + APIName);
        RequestSendMethod = apidetails.RequestMethod;
        System.out.println("RequestMethod=" + RequestSendMethod);
        PayloadRequired = apidetails.PayloadRequired;
        QueryParamRequired = apidetails.QueryParamsRequired;

    }


    private EndpointDetails Getvaluefromxmlconfig(List<HierarchicalConfiguration> AllEndpointElements, String servicename) {
        EndpointDetails element = new EndpointDetails();
        String ServiceName = "";
        for (HierarchicalConfiguration listitem : AllEndpointElements) {
            ServiceName = GetAttributevalue(listitem, "servicename");
            if (ServiceName.toLowerCase().equalsIgnoreCase(servicename.toLowerCase())) {
                element.Core = GetAttributevalue(listitem, "core");
                element.ServiceName = GetAttributevalue(listitem, "servicename");
                element.BaseURL = GetAttributevalue(listitem, "baseurl");
                element.AuthenticationRequired = GetAttributevalue(listitem, "authenticationrequired");
                element.UserID = GetAttributevalue(listitem, "userid");
                element.Password = GetAttributevalue(listitem, "password");
                element.Password = GetAttributevalue(listitem, "securitytoken");
            }

        }

        return element;
    }

    private String PrepareFullURL() {
        String fullurl = BaseURL + "/" + apidetails.APIPath;
        if (fullurl.contains("null/null")) {
            throw new RuntimeException("url contains null.Please check Baseurl and apipath in environment.xml");
        }
        //System.out.println(fullurl);
        return fullurl;
    }

    public String GetAttributevalue(HierarchicalConfiguration configuration, String Attributename) {
        String value = configuration.getString("[@" + Attributename + "]");
        return value;
    }

    class EndpointDetails {
        public String ServiceName;
        public String Core;
        public String BaseURL;
        public String AuthenticationRequired;
        public String UserID;
        public String Password;
        public String SecurityToken;
    }

    class APIDetails {
        public String APIname;
        public String Name;
        public Method RequestMethod;
        public boolean PayloadRequired;
        public boolean QueryParamsRequired;
        public String PayloadType;
        public String APIPath;

        public APIDetails() {

        }

        public APIDetails(ServiceType Servicename, String APINAME, Configuration config) {
            String[] inforarray = Servicename.toString().split("_");
            String core = inforarray[0].toLowerCase();
            String servicename = inforarray[1].toLowerCase();
            String targetapiname;
            String xmlnodepath = "services.apis" + "." + servicename.toLowerCase().trim() + ".api";

            for (HierarchicalConfiguration listitem : config.GetAllAPIElements(xmlnodepath)) {

                targetapiname = GetAttributevalue(listitem, "apiname");

                if (targetapiname.toLowerCase().trim().equalsIgnoreCase(APINAME.toLowerCase().trim())) {
                    APIname = GetAttributevalue(listitem, "apiname");

                    Name = GetAttributevalue(listitem, "name");
                    RequestMethod = Method.valueOf(GetAttributevalue(listitem, "requestmethod").toUpperCase());
                    //System.out.println("Request Method:"+RequestMethod);
                    PayloadRequired = Boolean.parseBoolean(GetAttributevalue(listitem, "payloadrequired"));
                    QueryParamsRequired = Boolean.parseBoolean(GetAttributevalue(listitem, "queryparamsrequired"));
                    PayloadType = GetAttributevalue(listitem, "payloadtype");
                    APIPath = GetAttributevalue(listitem, "path");
                }

            }
        }

        public String GetAttributevalue(HierarchicalConfiguration configuration, String Attributename) {
            String value = configuration.getString("[@" + Attributename + "]");
            return value;
        }
    }

}