package org.example.test;

import com.jayway.jsonpath.JsonPath;

public class jsonValidator extends JSONUtilities implements validationActions {

    private String Data = null;


    public jsonValidator(String content) {
        Data = content;
    }


    @Override
    public String GetResponseAsString() {
        // TODO Auto-generated method stub
        return Data;
    }

    @Override
    public String GetNodeValue(String NodePathExpression) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int GetChildNodeCount(String NodePathExpression) {
        // TODO Auto-generated method stub
        return -1;
    }

    @Override
    public boolean checkifnodeexists(String NodeXpathexpression) {
        // TODO Auto-generated method stub
        return false;
    }

    public String GetNodeValuewithJsonPath(String JSONPathExpression) {
        return JsonPath.read(Data, JSONPathExpression);
    }

}
