package org.example.test;

public interface validationActions {
    public String GetResponseAsString();

    public String GetNodeValue(String NodePathExpression);

    public int GetChildNodeCount(String NodePathExpression);

    public boolean checkifnodeexists(String NodeXpathexpression);

}
