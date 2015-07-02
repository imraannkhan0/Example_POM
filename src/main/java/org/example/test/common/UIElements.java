package org.example.test.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.example.test.Configuration;
import org.apache.commons.configuration.HierarchicalConfiguration;


public class UIElements {

    HashMap<String, UIElement> uielements = new HashMap<String, UIElement>();

    public UIElements(Configuration confobject) {
        BuildUIObject(confobject.GetAllUIElements());
    }

    private void BuildUIObject(List<HierarchicalConfiguration> AllUIElements) {
        String name;
        int count = 1;
        for (HierarchicalConfiguration listitem : AllUIElements) {
            UIElement element = new UIElement();
            name = GetAttributevalue(listitem, "name");
            element.name = name;
            element.value = GetAttributevalue(listitem, "value");
            element.jvalue = GetAttributevalue(listitem, "jValue");
            element.svalue = GetAttributevalue(listitem, "sValue");
            element.type = ElementIdentificationType
                    .valueOf(GetAttributevalue(listitem, "type").toUpperCase());
            if (uielements.containsKey(name)) {
                System.out.println(count++ + ". " + name
                        + " has a duplicate entry!!!");

            }

            uielements.put(name, element);

        }

    }

    public String GetAttributevalue(HierarchicalConfiguration configuration,
                                    String Attributename) {
        String value = configuration.getString("[@" + Attributename + "]");
        return value;
    }

    public UIElement GetUIElementDetails(String elementid) {
        UIElement elementdetail = null;
        for (Entry<String, UIElement> item : uielements.entrySet()) {
            if (item.getKey().equals(elementid)) {
                elementdetail = item.getValue();
                break;
            }
        }
        return elementdetail;
    }
}
