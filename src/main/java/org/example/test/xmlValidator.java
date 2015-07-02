package org.example.test;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;

public class xmlValidator implements validationActions {

    private String data = null;

    public xmlValidator(String content) {
        //Data = content;
        data = content;
    }

    @Override
    public String GetResponseAsString() {
        return data;
    }

    @Override
    public String GetNodeValue(String JSONPathExpression) {
        return null;
    }

    @Override
    public int GetChildNodeCount(String JSONPathExpression) {
        return -1;
    }

    @Override
    public boolean checkifnodeexists(String name) {
        return checkifsinglenodeexists(name);
    }

    private boolean checkifsinglenodeexists(String xpathexpression) {
        try {
            //  log.info("this is in xml validation");
            System.out.println("this is in xml validation");
            Document doc = preparexmldocument();
            XPath xPath = XPathFactory.newInstance().newXPath();
            //XPathExpression expr = xPath.compile("//codes/code[@code ='ABC']");
            XPathExpression expr = xPath.compile(xpathexpression);
            Object result = expr.evaluate(doc, XPathConstants.NODESET);

            NodeList nodes = (NodeList) result;
            //System.out.println("Have I found anything? " + (nodes.getLength() > 0 ? return true: return false));
            if (nodes.getLength() > 0) {
                return true;
            } else {
                return false;
            }
                /*for (int i = 0; i < nodes.getLength(); i++) {
	                System.out.println("nodes: "+ nodes.item(i).getNodeValue());

	            }*/
        } catch (Exception exp) {
            exp.printStackTrace();
            return false;
        }
    }


    private Document preparexmldocument() {
        String xml = data;

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {

            DocumentBuilder db = dbf.newDocumentBuilder();
            Document dom = db.parse(new ByteArrayInputStream(xml.getBytes()));
            return dom;

        } catch (Exception pce) {
            pce.printStackTrace();
        }
        return null;
    }


}
