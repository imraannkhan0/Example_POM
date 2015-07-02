package org.example.test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import argo.jdom.JdomParser;
import argo.jdom.JsonNode;
import argo.jdom.JsonRootNode;
import argo.saj.InvalidSyntaxException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public class APIUtilities extends JSONUtilities {
    //static Logger log = Logger.getLogger(APIUtilities.class);
    public String preparepayload(APINAME nameofapitobetested) {
        return null;
    }

    public String preparepayload(String PayloadRawString, String[] StringParameters) {
        Map<String, String> valuesMap = new HashMap<String, String>();
        int paramnumber = 0;
        for (String param : StringParameters) {
            valuesMap.put(Integer.toString(paramnumber), param);
            paramnumber++;
        }
        String templateString = PayloadRawString;
        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        String resolvedString = sub.replace(templateString);
        return resolvedString;
    }

    public String prepareparameterizedURL(String URL, String StringParameters) {
        Map<String, String> valuesMap = new HashMap<String, String>();
        int paramnumber = 0;
        // for(String param: StringParameters)
        // {
        valuesMap.put(Integer.toString(paramnumber), StringParameters);
        // paramnumber++;
        // }
        String templateString = URL;
        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        String resolvedString = sub.replace(templateString);
        return resolvedString;
    }

    public String prepareparameterizedURL(String URL, String[] StringParameters) {
        Map<String, String> valuesMap = new HashMap<String, String>();
        int paramnumber = 0;
        for (String param : StringParameters) {
            valuesMap.put(Integer.toString(paramnumber), param);
            paramnumber++;
        }
        String templateString = URL;
        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        String resolvedString = sub.replace(templateString);
        return resolvedString;
    }

	/*public int GetChildCount(String Nodepath, String JSONString)
    {
		int result = 0;

		return result;
	}*/

    public List<String> GetallNodeValuesfromjsonarray(String NodePath, String JSONString) {
        List<String> result = new ArrayList<String>();
        boolean isjsonarray = false;
        JsonArray jarray, jarray1;
        JsonObject jobject = null;
        JsonElement jelement = new JsonParser().parse(JSONString);
        String[] nodepath = NodePath.split("\\.");

        int counter = nodepath.length;
        if (jelement.isJsonArray()) {
            isjsonarray = true;
            //jarray1 = jelement.getAsJsonArray();
        } else {
            jobject = jelement.getAsJsonObject();
        }


        if (isjsonarray) {

            for (String node : nodepath) {
                jarray1 = jelement.getAsJsonArray();
                for (int i = 0; i < jarray1.size(); i++) {
                    jobject = jarray1.get(i).getAsJsonObject();
                    result.add(jobject.get(node).toString());
                }
            }
        } else {
            for (String node : nodepath) {
                JsonElement element = jobject.get(node);
                // log.info("Node is : " + node + " : " + counter);
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
                            //	log.info("Node is neither a json array or a jason object.");
                        }
                    }

                } else {
                    result.add(jobject.get(node).toString());
                }
                counter--;

            }
        }

        return result;
    }

    public String ReadJsonData(String NodePath, String JsonString) {
		/*
		 * JsonParser parser = new JsonParser();
		 * ResponseFromService.bufferEntity(); String jsonLine =
		 * ResponseFromService.readEntity(String.class),result = null;
		 */
        //JsonReader.setLenient(true);
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
            //	log.info("Node is : " + node + " : " + counter+"  element is "+element.toString());
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

                        //		log.info("Node is neither a json array or a jason object. FOllowing is the Node \n\n\n"+jobject.toString()+"\n\n");
                    }
                }

            } else {
                result = jobject.get(node).toString();
            }
            counter--;

        }
        return result;
    }


    /**
     * Final method which would be used to parse JSON arrays within a JSON object
     *
     * @param NodePath
     * @param JsonString
     */
    public void GetJSONObject(String NodePath, String JsonString) {
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
            //	log.info("Nodename = "+node);
            Findelementtype(element);
            if (element.toString().isEmpty() == false) {
                //	log.info("counter = "+ counter +", Inside Nodename = "+node);

                if (counter <= 1) {
                    if (Findelementtype(element).toLowerCase().contains("jsonarray")) {
                        //Add code here
                    } else {

                    }
                    //log.info("Further Inside Nodename = " + node);
                    //log.info("The Class of element is identified as : "+Findelementtype(element));
                } else {
                    Findelementtype(element);

                    jobject = jobject.getAsJsonObject(node);
                }

            }
            counter--;
        }

    }


    /**
     * @param NodePath     (A dot seperated jsonpath ex: "response.data.node"
     * @param JSONAsString
     * @return count of childnodes under the NodePath/node, returns zero
     * @throws argo.saj.InvalidSyntaxException
     * @author ashish.bajpai
     * @description A method implemented using framework ARGO for json processing
     */
    public int GetChildElementCount(String NodePath, String JSONAsString) throws InvalidSyntaxException {
        JdomParser JDOM_PARSER = new JdomParser();
        JsonRootNode json = JDOM_PARSER.parse(JSONAsString);
        JsonNode currentnode = null;
        JsonNode nextnode = null;
        int returnsize = 0;
        int counter = 0;
        if (NodePath != null) {
            currentnode = (JsonNode) json;
            String[] nodepath = NodePath.split("\\.");

            counter = nodepath.length;
            for (String node : nodepath) {
                nextnode = currentnode.getNode(node);
                if (currentnode.isArrayNode(node)) {
                    if (counter == 1) {
                        returnsize = currentnode.getArrayNode(node).size();
                    } else {
                        currentnode = currentnode.getArrayNode(node).get(0);
                        returnsize = currentnode.getFields().size();
                    }
                } else {
                    currentnode = currentnode.getNode(node);
                    try {
                        returnsize = currentnode.getFields().size();
                    } catch (IllegalStateException e) {
                        //			log.info("[EXCEPTION] - Possibaly there are no childnodes present under node '"+node+"'.");
                        returnsize = 0;
                    }
                }
                counter--;
            }
        }
        // log.info("Node count under node '"+ NodePath +"' is = "+returnsize);
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
    public int ReadChildNodeCountUsingIndex(String NodePath, String JSONAsString) throws InvalidSyntaxException {
        return GetChildNodeCountUsingIndex(NodePath, JSONAsString);
    }

    /*public String ReadNodeValue(String NodePath, String JSONAsString, int indexinarray) throws InvalidSyntaxException
    {
        return Get
    }*/


    private void GetAs(JsonElement jsonelement, String classname) {

    }

    public String Findelementtype(JsonElement element) {
        //log.info(element.getClass().getName());
        return element.getClass().getName();
    }

    public boolean IsJsonArray(JsonElement element) {
        return element.isJsonArray();
    }


    public String ReadJsonDataMinimalJSON(String NodePath, String JsonString) {

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
            //log.info("Node is : " + node + " : " + counter+"  element is "+element.toString());
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

                        //	log.info("Node is neither a json array or a jason object. FOllowing is the Node \n\n\n"+jobject.toString()+"\n\n");
                    }
                }

            } else {
                result = jobject.get(node).toString();
            }
            counter--;

        }
        return result;
    }

    public String ReadJsonData_old(String NodePath, String JsonString) {
		/*
		 * JsonParser parser = new JsonParser();
		 * ResponseFromService.bufferEntity(); String jsonLine =
		 * ResponseFromService.readEntity(String.class),result = null;
		 */

        String result = null;
        JsonElement jelement = new JsonParser().parse(JsonString);
        JsonObject jobject = jelement.getAsJsonObject();

        String[] nodepath = NodePath.split("\\.");

        int counter = nodepath.length;

        for (String node : nodepath) {
            //	log.info("Node is : " + node + " : " + counter);
            if (counter > 1) {
                jobject = jobject.getAsJsonObject(node);
            } else {
                result = jobject.get(node).toString();
            }
            counter--;

        }
        return result;
    }

    public boolean Exists2(String NodePath, String JsonString) {
        boolean result = false, temp = true;
        JsonElement jelement = new JsonParser().parse(JsonString);
        JsonObject jobject = jelement.getAsJsonObject();
        JsonArray jarray;
        String[] nodepath = NodePath.split("\\.");
        boolean arrayprocessing = false;

        if (NodePath.contains("#[") && NodePath.contains("]#")) {
            arrayprocessing = true;
        }

        int counter = nodepath.length;

        for (String node : nodepath) {
            JsonElement element = jobject.get(node);
            //log.info("Node is : " + node + " : " + counter);
            if (counter > 1) {
                if (element.isJsonObject()) {
                    //	log.info("Node is : " + node + " is Object and counter is : " + counter);
                    jobject = jobject.getAsJsonObject(node);
                } else {
                    if (element.isJsonArray()) {
//						log.info("Node is : " + node + " is Array and counter is : " + counter);
                        jarray = jobject.getAsJsonArray(node);
//						log.info(jarray.size());
                        for (int i = 0; i < jarray.size(); i++) {
//							log.info(jarray.toString());
//							log.info("Each Element :" + jarray.get(i).getAsJsonObject());
                            // log.info("Json Object :"+jarray.getAsJsonObject());
                            // jobject = jarray.get(i).get("id");
                        }
                    } else {
//						log.info("Node is neither a json array or a jason object.");
                    }
                }

            } else {
                result = jobject.has(node);
            }
            counter--;

        }
        return result;
    }

    public boolean Exists(String NodePath, String JsonString) {
        boolean result = false, temp = true;
        JsonElement jelement = new JsonParser().parse(JsonString);
        JsonObject jobject = jelement.getAsJsonObject();
        JsonArray jarray;
        String[] nodepath = NodePath.split("\\.");

        int counter = nodepath.length;

        for (String node : nodepath) {
            JsonElement element = jobject.get(node);
//			log.info("Node is : " + node + " : " + counter);
            if (counter > 1) {
                if (element.isJsonObject()) {
//					log.info("Identified as jObject");
                    jobject = jobject.getAsJsonObject(node);
                } else {
                    if (element.isJsonArray()) {
//						log.info("Identified as array");
                        jarray = jobject.getAsJsonArray(node);
                        for (int i = 0; i < jarray.size(); i++) {
                            if (counter == 2) {
                                jobject = jarray.get(i).getAsJsonObject();
                                temp = iskeypresent(jobject, nodepath[nodepath.length - 1]);
                                result = result && temp;
                            } else {
                                jobject = jarray.get(i).getAsJsonObject();

                            }

                        }
                    } else {
//						log.info("Node is neither a json array or a jason object.");
                    }
                }

            } else {
                result = jobject.has(node);
//				log.info("Finally checking hasnode and result is "+result);
//				log.info("\n\n"+jobject.toString());
            }
            counter--;
            if (!result) {

//				log.debug("Node '" + node + "' is missing from "+NodePath);
//				log.debug(jobject.toString());

            }
        }

        return result;
    }


    /**
     * Needs to be coded afresh
     *
     * @param NodePath
     * @param JsonString
     * @return
     */
    public boolean GetArraySize(String NodePath, String JsonString) {
        boolean result = false, temp = true;
        JsonElement jelement = new JsonParser().parse(JsonString);
        JsonObject jobject = jelement.getAsJsonObject();
        JsonArray jarray;
        String[] nodepath = NodePath.split("\\.");

        int counter = nodepath.length;

        for (String node : nodepath) {
            JsonElement element = jobject.get(node);
//			log.info("Node is : " + node + " : " + counter);
            if (counter > 1) {
                if (element.isJsonObject()) {
//					log.info("Identified as jObject");
                    jobject = jobject.getAsJsonObject(node);
                } else {
                    if (element.isJsonArray()) {
//						log.info("Identified as array");
                        jarray = jobject.getAsJsonArray(node);
                        for (int i = 0; i < jarray.size(); i++) {
                            if (counter == 2) {
                                jobject = jarray.get(i).getAsJsonObject();
                                temp = iskeypresent(jobject, nodepath[nodepath.length - 1]);
                                result = result && temp;
                            } else {
                                jobject = jarray.get(i).getAsJsonObject();

                            }

                        }
                    } else {
//						log.info("Node is neither a json array or a jason object.");
                    }
                }

            } else {
                result = jobject.has(node);
//				log.info("Finally checking hasnode and result is "+result);
//				log.info("\n\n"+jobject.toString());
            }
            counter--;
            if (!result) {

//				log.debug("Node '" + node + "' is missing from "+NodePath);
//				log.debug(jobject.toString());

            }
        }

        return result;
    }


    public void DynamicParsingofJSON(String JsonString) {
        boolean result = false, temp = true;
        JsonElement jelement = new JsonParser().parse(JsonString);
        JsonObject jobject = jelement.getAsJsonObject();
        JsonArray jarray;
        //String[] nodepath = NodePath.split("\\.");
        List<String> keys = new ArrayList<String>();
        //int counter = nodepath.length;

        int i = 0;
        for (Entry<String, JsonElement> entry : jobject.entrySet()) {
            String key = entry.getKey();
            JsonElement value = entry.getValue();

            keys.add(key);
            i++;
        }

        for (String keyname : keys) {
            System.out.println(keyname);
        }
        System.out.println();
        //nKeys = i;
		/*for (String node : nodepath)
		{
			JsonElement element = jobject.get(node);
			//log.info("Node is : " + node + " : " + counter);
			if (counter > 1)
			{
				if (element.isJsonObject())
				{
					log.info("Identified as jObject");
					jobject = jobject.getAsJsonObject(node);
				} 
				else
				{
					if (element.isJsonArray())
					{
						log.info("Identified as array");
						jarray = jobject.getAsJsonArray(node);
						for (int i = 0; i < jarray.size(); i++)
						{
							if (counter==2)
							{
								jobject = jarray.get(i).getAsJsonObject();
								temp = iskeypresent(jobject, nodepath[nodepath.length-1]);
								result = result && temp;
							}
							else
							{
								jobject = jarray.get(i).getAsJsonObject();
								
							}
							
						}
					} 
					else
					{
						log.info("Node is neither a json array or a jason object.");
					}
				}

			} 
			else
			{
				result = jobject.has(node);
				log.info("Finally checking hasnode and result is "+result);
				log.info("\n\n"+jobject.toString());
			}
			counter--;
			if (!result)
			{
				
				log.debug("Node '" + node + "' is missing from "+NodePath);
				log.debug(jobject.toString());
				
			}*/
        //}

        //return result;
    }

    public boolean iskeypresent(JsonObject jobject, String Key) {
        boolean result = false;
//		log.info("Checking key "+Key);
        result = jobject.has(Key);
        return result;
    }

    public String getvalue(JsonObject jobject, String Key) {
        String result = null;
        result = jobject.get(Key).toString();
        return result;
    }


    public int GetChildNodeCount(String jsonstring, String Nodepath) {
        try {
            List<String> messages = new ArrayList<String>();
            String value, key;
            JsonReader reader = new JsonReader(new StringReader(jsonstring));
            reader.beginArray();
            reader.beginObject();
            while (reader.hasNext()) {
//				log.info(reader.toString());
                key = reader.nextName();
//				log.info("Node Name : "+key);
                messages.add(key);
            }
            reader.endObject();
            reader.endArray();
            return messages.size();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }

    }

}
	
	/*public String readMessage(JsonReader reader) throws IOException {
	     long id = -1;
	     String text = null;
	     //User user = null;
	     List<Double> geo = null;

	     reader.beginObject();
	     while (reader.hasNext()) {
	       String name = reader.nextName();
	       if (name.equals("id")) {
	         id = reader.nextLong();
	       } else if (name.equals("text")) {
	         text = reader.nextString();
	       } else if (name.equals("geo") && reader.peek() != JsonToken.NULL) {
	         geo = readDoublesArray(reader);
	       } else if (name.equals("user")) {
	         user = readUser(reader);
	       } else {
	         reader.skipValue();
	       }
	     }
	     reader.endObject();
	     return new Message(id, text, user, geo);
	   }*/


//}
/*
 * 
 * public boolean Exists(String NodePath, String JsonString) {
 * 
 * JsonParser parser = new JsonParser(); ResponseFromService.bufferEntity();
 * String jsonLine = ResponseFromService.readEntity(String.class),result = null;
 * 
 * 
 * boolean result = false; JsonElement jelement = new
 * JsonParser().parse(JsonString); JsonObject jobject =
 * jelement.getAsJsonObject();
 * 
 * String[] nodepath = NodePath.split("\\.");
 * 
 * int counter = nodepath.length;
 * 
 * for (String node : nodepath) { log.info("Node is : " + node + " : " +
 * counter); if (counter > 1) { jobject = jobject.getAsJsonObject(node); } else
 * { result = jobject.has(node); } counter--;
 * 
 * }
 * 
 * return result; }
 */

/*
 * public boolean Exists(String Nodetocheck, String JsonString) { JSONObject
 * json = null; JSONObject objectInQuestion = null; try { json = new
 * JSONObject(JsonString); objectInQuestion = json.getJSONObject(Nodetocheck); }
 * catch (JSONException ignored) { log.fatal(ignored.toString()); }
 * 
 * if (objectInQuestion == null) {
 * log.info("The json object '"+Nodetocheck+"' is not found"); return false; }
 * else { log.info("The json object '"+Nodetocheck+"' found"); return true; } }
 */

/*
 * public boolean Exists(String Nodetocheck, String JsonString) { JSONObject
 * json = null; boolean result = false; try { json = new JSONObject(JsonString);
 * //objectInQuestion = json.getJSONObject(Nodetocheck); result =
 * json.has("Nodetocheck"); } catch (JSONException ignored) {
 * log.fatal(ignored.toString()); } return result; }
 */

// }
