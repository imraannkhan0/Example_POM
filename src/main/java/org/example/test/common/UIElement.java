package org.example.test.common;

public class UIElement {
    public String name;
    public String value;
    public ElementIdentificationType type;
    public String jvalue;
    public String svalue;

    public void printuielement() {
        System.out.println("Name = '" + name + "', value = '" + value + "', type = '" + type.name() + "', jvalue = '" + jvalue + "' svalue = " + svalue + "'");
    }

}
