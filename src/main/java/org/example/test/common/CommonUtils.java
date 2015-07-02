package org.example.test.common;

/**
 * Created by Sabyasachi on 19/6/2015.
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.report.ProcessingMessage;
import com.github.fge.jsonschema.report.ProcessingReport;
import com.github.fge.jsonschema.util.JsonLoader;
import com.jayway.jsonpath.JsonPath;
import org.example.test.Initialize;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class CommonUtils {
    static Logger log = Logger.getLogger(CommonUtils.class);
    // static Initialize init = new Initialize("./Data/configuration");

    private String xmlFile;
    private Document xmlDocument;
    private XPath xPath;

    public CommonUtils(String xmlFile) {
        this.xmlFile = xmlFile;
        initObjects();

    }

    private void initObjects() {
        try {
            InputSource inputSource = new InputSource();
            inputSource.setCharacterStream(new StringReader(xmlFile));
            xmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputSource);
            xPath = XPathFactory.newInstance().newXPath();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Object read(String expression, QName returnType) {
        try {
            XPathExpression xPathExpression = xPath.compile(expression);
            return xPathExpression.evaluate(xmlDocument, returnType);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void traverse(NodeList rootNode) {
        for (int index = 0; index < rootNode.getLength(); index++) {
            Node aNode = rootNode.item(index);
            if (aNode.getNodeType() == Node.ELEMENT_NODE) {
                NodeList childNodes = aNode.getChildNodes();
                if (childNodes.getLength() < 0) {
                    System.out.println("Node Name--&gt;" + aNode.getNodeName() + " , Node Value--&gt;" + aNode.getTextContent());
                }
                traverse(aNode.getChildNodes());
            }
        }
    }

    public static boolean validateXMLSchema(String xsdPath, String xmlPath) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
        } catch (IOException | SAXException e) {
            System.out.println("Exception: " + e.getMessage());
            return false;
        }
        return true;
    }

   /* public List returnValueFromXpath(String xmlString,String xpathExpression){
        try {
            XML xml = new XMLDocument(xmlString);
        }  catch(Exception e){

            }
        return xml.xpath(xpathExpression);

    }*/

    public List<String> validateServiceResponseNodesUsingSchemaValidator(String jsonData, String jsonSchema) throws Exception {
        // create the Json nodes for schema and data
        ProcessingReport report = null;
        ProcessingMessage message = null;
        List<String> missingNodeList = new ArrayList<String>();

        JsonNode schemaNode = JsonLoader.fromString(jsonSchema); // throws JsonProcessingException if error
        JsonNode data = JsonLoader.fromString(jsonData); // same here

        JsonSchemaFactory factory = JsonSchemaFactory.byDefault();

        // load the schema and validate
        JsonSchema schema = factory.getJsonSchema(schemaNode);
        report = schema.validate(data);

        System.out.println("\nProcessing Report = " + report.toString());
        log.info("\nProcessing Report = " + report.toString());

        Iterator<ProcessingMessage> itr = report.iterator();

        while (itr.hasNext()) {
            message = (ProcessingMessage) itr.next();
            if (null != message && null != message.asJson()) {
                missingNodeList.add(String.valueOf(JsonPath.read(String.valueOf(message.asJson()), "$.instance.pointer")) + "/" +
                        String.valueOf(JsonPath.read(String.valueOf(message.asJson()), "$.missing")));
                System.out.println("\nProcessing Message = " + message + "\n");
                log.info("\nProcessing Message = " + message + "\n");
            }
        }

        return missingNodeList;
    }

}
