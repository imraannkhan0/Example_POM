package org.example.test;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import argo.saj.InvalidSyntaxException;

public class ResponseValidator extends JSONUtilities {

    public Response ResponseFromService;

    private PayloadType responseType = PayloadType.JSON;
    APIUtilities utilities = new APIUtilities();

    public ResponseValidator(Response ResponsefromAPI, PayloadType ResponseType) {
        ResponseFromService = ResponsefromAPI;
        //System.out.println(ResponseFromService);
        responseType = ResponseType;

        if (ResponseType == PayloadType.JSON) {
            ResponseValidator(ResponsefromAPI);
        }
        //else

        // ResponseValidator(ResponsefromAPI);

    }

    private void ResponseValidator(Response ResponsefromAPI) {
        //log.info("first response instance " + ResponsefromAPI.gete);
        //ResponsefromAPI.bufferEntity();
        ResponsefromAPI.bufferEntity();
        ResponseFromService.bufferEntity();
        ResponseFromService = ResponsefromAPI;
        //ResponseFromService.bufferEntity();
        logresponse(ResponsefromAPI);
        returnresponseasstring();

    }

    private void logresponse(Response responsefromapi) {
        //log.debug(responsefromapi.readEntity(String.class));

        //responsefromapi.bufferEntity();
        //	log.info(returnresponseasstring());
    }

    public String returnresponseasstring() {
        String returntype = null;
        ObjectMapper mapper = null;
        //System.out.println(" ResponseFromService.hasEntity() " + ResponseFromService.getEntity());
        ResponseFromService.bufferEntity();
        returntype = ResponseFromService.readEntity(String.class);
        if (returntype.length() > 0) {
            try {
                if (responseType == PayloadType.JSON) {
                    //    log.info("this is considered to be a JSON response");
                    mapper = new ObjectMapper();
                    Object json = mapper.readValue(returntype, Object.class);
                    returntype = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
                } else {

                    //      log.info("this is considered to be a XML response");

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //	log.info("Printing response from service :"+returntype);
        //return gson.toJson(jO);
        return returntype;
    }

    public String formatXml(String responseFromApi) {
//TODO
        return null;

    }

    public void printresponse() throws IOException {
        //	log.info(returnresponseasstring());
    }

    public int GetTotalProductCount() {
        return Integer.parseInt(NodeValue("response1.totalProductsCount", true));
    }

    public String NodeValue(String NodePath, boolean Printresult) {
        String result = ReadNode1(NodePath);
        if (Printresult) {
            //	log.info(result);
        }
        return result;
    }

    public List<String> GetallvaluesfromarrayNode(String NodePath, boolean Printresult) {
        List<String> result = GetValueofAllNodesFromArray(NodePath);
        if (Printresult) {
            utilities.PrintList(result);
        }
        return result;
    }

    public int GetChildNodeCount(String NodePath) {
        int returnval = 0;
        String response = ResponseFromService.readEntity(String.class);
        try {
            returnval = utilities.GetChildElementCount(NodePath, response);
        } catch (InvalidSyntaxException e) {
            e.printStackTrace();
            returnval = -1;
        }
        return returnval;
    }


    public int GetChildNodeCountUsingIndex(String NodePath) {
        int returnval = 0;
        String response = ResponseFromService.readEntity(String.class);
        try {
            returnval = utilities.ReadChildNodeCountUsingIndex(NodePath, response);
        } catch (InvalidSyntaxException e) {
            e.printStackTrace();
            returnval = -1;
        }
        return returnval;
    }

    public String GetNodeTextUsingIndex(String NodePath) {
        String returnedresponse = null;
        String response = ResponseFromService.readEntity(String.class);
        try {
            returnedresponse = utilities.GetChildNodeValueUsingIndex(NodePath, response);
        } catch (InvalidSyntaxException e) {
            e.printStackTrace();
            //    log.info("Exception Occurred" + e.getStackTrace());

        }
        return returnedresponse;
    }

    private String ReadNode1(String Path) {
        //JsonParser parser = new JsonParser();
        String response = ResponseFromService.readEntity(String.class);
        return utilities.ReadJsonData(Path, response);
    }

    public void ParseJSON() {
        String response = ResponseFromService.readEntity(String.class);
        utilities.DynamicParsingofJSON(response);
    }

    private List<String> GetValueofAllNodesFromArray(String NodePath) {
        //JsonParser parser = new JsonParser();
        String response = ResponseFromService.readEntity(String.class);
        return utilities.GetallNodeValuesfromjsonarray(NodePath, response);
    }

    public int GetArraySize(String NodePath) {
        String response = ResponseFromService.readEntity(String.class);
        try {
            return utilities.GetChildElementCount(NodePath, response);
        } catch (InvalidSyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //	log.info("[EXCEPTION] - Failed to find Child element count, please check the Nodepath/response recived from service/api. Possibaly the path is wrong.");
            return -1;
        }
    }

    public boolean DoesNodeExists(String Nodename) {
        String response = ResponseFromService.readEntity(String.class);

        return utilities.Exists(Nodename, response);
    }


    public boolean DoesNodesExists(List<String> NodeList) {
        boolean result = true, temp = false;
        String response = ResponseFromService.readEntity(String.class);
        for (String Node : NodeList) {
            temp = utilities.Exists(Node, response);
            result = result && temp;
        }


        return result;
    }


    public Boolean DoesNodeExistsWithNonEmptyValue(String Path) {
        //JsonParser parser = new JsonParser();
        String response = ResponseFromService.readEntity(String.class);
        String nodevalue = utilities.ReadJsonData(Path, response);
        if (DoesNodeExists(Path) && nodevalue.trim().isEmpty() == false) {
            return true;
        } else {
            return false;
        }
    }

}
