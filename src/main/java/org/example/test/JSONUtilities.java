package org.example.test;

import argo.jdom.JdomParser;
import argo.jdom.JsonNode;
import argo.jdom.JsonObjectNodeBuilder;
import argo.jdom.JsonRootNode;
import argo.saj.InvalidSyntaxException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;


@SuppressWarnings("unused")
public class JSONUtilities extends ToolBox {


    public JSONUtilities() {

    }


    /**
     * @param NodePath     (A dot seperated jsonpath ex: "response.data.node"
     * @param JSONAsString
     * @return count of childnodes under the NodePath/node, returns zero
     * @throws argo.saj.InvalidSyntaxException
     * @author ashish.bajpai
     * @description A method implemented using framework ARGO for json processing
     */
    public String GetNodeValue_Argo(String NodePath, String JSONAsString) throws InvalidSyntaxException {
        JdomParser JDOM_PARSER = new JdomParser();
        JsonRootNode json = JDOM_PARSER.parse(JSONAsString);
        JsonNode currentnode = null;
        //JsonNode nextnode = null;
        String value = null;
        int counter = 0;
        if (NodePath != null) {
            currentnode = (JsonNode) json;
            String[] nodepath = NodePath.split("\\.");

            counter = nodepath.length;
            for (String node : nodepath) {
                //nextnode = currentnode.getNode(node);
                if (currentnode.isArrayNode(node)) {
                    if (counter == 1) {
                        //value = currentnode.getArrayNode(node).toString();
                        value = currentnode.getArrayNode(node).toString();
                    } else {
                        currentnode = currentnode.getArrayNode(node).get(0);
                        //value = currentnode.toString();
                        value = currentnode.toString();
                    }
                } else {
                    currentnode = currentnode.getNode(node);
                    try {
                        value = currentnode.toString();
                        //  System.out.println( currentnode.getNumberValue(NodePath));
                    } catch (IllegalStateException e) {
                        //log.info("[EXCEPTION] - Possibly there are no child nodes present under node '" + node + "'.");
                        value = null;
                    }
                }
                counter--;
                System.out.println("value = " + currentnode.getType());
            }
        }
        return value;
    }


    /**
     * @param NodePath     (A dot seperated jsonpath ex: "response.data.node"
     * @param JSONAsString
     * @return count of childnodes under the NodePath/node, returns zero
     * @throws argo.saj.InvalidSyntaxException
     * @author ashish.bajpai
     * @description Should return a List<String>
     */
    public String GetNodeValues(String NodePath, String JSONAsString) throws InvalidSyntaxException {
        JdomParser JDOM_PARSER = new JdomParser();
        JsonRootNode json = JDOM_PARSER.parse(JSONAsString);
        JsonNode currentnode = null;
        //JsonNode nextnode = null;
        String value = null;
        int counter = 0;
        if (NodePath != null) {
            currentnode = (JsonNode) json;
            String[] nodepath = NodePath.split("\\.");

            counter = nodepath.length;
            for (String node : nodepath) {
                //nextnode = currentnode.getNode(node);
                if (currentnode.isArrayNode(node)) {
                    if (counter == 1) {
                        //value = currentnode.getArrayNode(node).toString();
                        value = currentnode.getArrayNode(node).toString();
                    } else {
                        currentnode = currentnode.getArrayNode(node).get(0);
                        //value = currentnode.toString();
                        value = currentnode.toString();
                    }
                } else {
                    currentnode = currentnode.getNode(node);
                    try {
                        value = currentnode.toString();
                    } catch (IllegalStateException e) {
                        //  log.info("[EXCEPTION] - Possibly there are no child nodes present under node '" + node + "'.");
                        value = null;
                    }
                }
                counter--;
                System.out.println("value = " + currentnode.getType());
            }
        }
        return value;
    }


    public JsonNode GetCurrentNode(String NodePath, String JSONAsString) throws InvalidSyntaxException {
        JdomParser JDOM_PARSER = new JdomParser();
        JsonRootNode json = JDOM_PARSER.parse(JSONAsString);
        JsonNode currentnode = null, returnnode = null;
        //JsonNode nextnode = null;
        String value = null;
        int counter = 0;
        if (NodePath != null) {
            currentnode = (JsonNode) json;
            String[] nodepath = NodePath.split("\\.");

            counter = nodepath.length;
            for (String node : nodepath) {
                //nextnode = currentnode.getNode(node);
                if (currentnode.isArrayNode(node)) {
                    if (counter == 1) {
                        //value = currentnode.getArrayNode(node).toString();
                        currentnode = currentnode.getArrayNode(node).get(0);
                    } else {
                        currentnode = currentnode.getArrayNode(node).get(0);
                        //value = currentnode.toString();
                        //value = currentnode.toString();
                    }
                } else {
                    currentnode = currentnode.getNode(node);
                    /*try {
                        value = currentnode.toString();
                    } catch (IllegalStateException e) {
                        log.info("[EXCEPTION] - Possibly there are no child nodes present under node '" + node + "'.");
                        value = null;
                    }*/
                }
                counter--;

                //System.out.println("value = " + currentnode.getType() );
            }
        }
        returnnode = currentnode;
        return returnnode;
    }

    /*public String GetNodeValue_argo()
    {

    }*/


    /**
     * @param NodePath     (A dot seperated jsonpath ex: "response.data.node"
     * @param JSONAsString
     * @return count of childnodes under the NodePath/node, returns zero
     * @throws argo.saj.InvalidSyntaxException
     * @author ashish.bajpai
     * @description A method implemented using framework ARGO for json processing
     */
    public int GetChildNodeCountUsingIndex(String NodePath, String JSONAsString) throws InvalidSyntaxException {
        JdomParser JDOM_PARSER = new JdomParser();
        JsonRootNode json = JDOM_PARSER.parse(JSONAsString);
        JsonNode currentnode = null;
        JsonNode nextnode = null;
        int returnsize = 0;
        int counter = 0;
        int index = -1;
        if (NodePath != null) {
            currentnode = (JsonNode) json;
            String[] nodepath = NodePath.split("\\.");

            counter = nodepath.length;
            //for (String node : nodepath)
            for (int i = 0; i < nodepath.length; i++) {
                String node = nodepath[i];
                if (node.contains("[") && node.contains("]")) {
                    index = Integer.parseInt(StringUtils.substringBetween(node, "[", "]"));

                    node = StringUtils.replace(node, "[" + index + "]", "");

                } else {

                }
                nextnode = currentnode.getNode(node);
                if (currentnode.isArrayNode(node)) {
                    if (counter == 1) {
                        returnsize = currentnode.getArrayNode(node).size();
                    } else {
                        if (index != -1) {
                            currentnode = currentnode.getArrayNode(node).get(index);
                            returnsize = currentnode.getFields().size();
                        } else {
                            currentnode = currentnode.getArrayNode(node).get(0);
                            returnsize = currentnode.getFields().size();
                        }
                    }
                } else {
                    currentnode = currentnode.getNode(node);
                    try {
                        returnsize = currentnode.getFields().size();
                    } catch (IllegalStateException e) {
                        //  log.info("[EXCEPTION] - Possibaly there are no childnodes present under node '"+node+"'.");
                        returnsize = 0;
                    }
                }
                counter--;
            }
        }
        //  log.info("Node count under node '"+ NodePath +"' is = "+returnsize);
        return returnsize;
    }


    /**
     * @param NodePath     (A dot seperated jsonpath ex: "response.data.node"
     * @param JSONAsString
     * @return count of childnodes under the NodePath/node, returns zero
     * @throws argo.saj.InvalidSyntaxException
     * @author ashish.bajpai
     * @description A method implemented using framework ARGO for json processing
     */
    public String GetChildNodeValueUsingIndex(String NodePath, String JSONAsString) throws InvalidSyntaxException {
        JdomParser JDOM_PARSER = new JdomParser();
        JsonRootNode json = JDOM_PARSER.parse(JSONAsString);
        JsonNode currentnode = null;
        //JsonNode nextnode = null;
        //int returnsize = 0;
        String returnvalue = null;
        int counter = 0;
        int index = -1;
        if (NodePath != null) {
            currentnode = (JsonNode) json;
            String[] nodepath = NodePath.split("\\.");

            counter = nodepath.length;
            //for (String node : nodepath)
            for (int i = 0; i < nodepath.length; i++) {
                String node = nodepath[i];
                if (node.contains("[") && node.contains("]")) {
                    index = Integer.parseInt(StringUtils.substringBetween(node, "[", "]"));
                    // log.info("Node is "+node);
                    node = StringUtils.replace(node, "[" + index + "]", "");
                    //   log.info("Node is "+node);
                } else {

                }
                //nextnode = currentnode.getNode(node);
                if (currentnode.isArrayNode(node)) {
                    if (counter == 1) {
                        returnvalue = currentnode.getStringValue();
                    } else {
                        if (index != -1) {
                            currentnode = currentnode.getArrayNode(node).get(index);
                            returnvalue = currentnode.getStringValue();
                        } else {
                            currentnode = currentnode.getArrayNode(node).get(0);
                            returnvalue = currentnode.getText();
                        }
                    }
                } else {
                    currentnode = currentnode.getNode(node);
                    try {
                        returnvalue = currentnode.toString();
                    } catch (IllegalStateException e) {
                        //  log.info("[EXCEPTION] - Possibaly there are no childnodes present under node '"+node+"'.");
                        returnvalue = null;
                    }
                }
                counter--;
            }
        }
        //  log.info("Node count under node '"+ NodePath +"' is = "+returnvalue);
        return returnvalue;
    }


    public String GetNodeValue(String NodePath, String JsonString) {
        String result = null;
        JsonArray jarray, rootnodearray;
        JsonObject jobject = null;
        JsonElement jelement = new JsonParser().parse(JsonString);
        if (jelement.isJsonArray()) {
            jarray = jelement.getAsJsonArray();
            for (int i = 0; i < jarray.size(); i++) {
                jobject = jarray.get(i).getAsJsonObject();
            }
        } else {
            jobject = jelement.getAsJsonObject();
        }

        String[] nodepath = NodePath.split("\\.");

        int counter = nodepath.length;

        for (String node : nodepath) {
            JsonElement element = jobject.get(node);
            //  log.info("Node is : " + node + " : " + counter+"  element is "+element.toString());
            if (counter > 1) {
                if (element.isJsonObject()) {
                    jobject = jobject.getAsJsonObject(node);
                } else {
                    if (element.isJsonArray()) {
                        jarray = jobject.getAsJsonArray(node);
                        for (int i = 0; i < jarray.size(); i++) {
                            jobject = jarray.get(i).getAsJsonObject();
                        }
                    } else {

                        //  log.info("Node is neither a json array or a jason object. FOllowing is the Node \n\n\n"+jobject.toString()+"\n\n");
                    }
                }

            } else {
                result = jobject.get(node).toString();
            }
            counter--;

        }
        return result;
    }

}
