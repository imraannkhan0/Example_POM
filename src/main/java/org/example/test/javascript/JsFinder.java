package org.example.test.javascript;

/**
 * Created by Sabyasachi on 10/6/2015.
 */
public enum JsFinder {

    CLASSNAME("return document.getElementsByClassName(\"%s\")"),
    ID("return document.getElementById(\"%s\")"),
    NAME("return document.getElementsByName(\"%s\")"),
    TAG_NAME("return document.getElementsByTagName(\"%s\")"),
    TAG_NAME_NS("return document.getElementsByTagNameNS(\"%s\")"),
    ATTRIBUTE_VALUE("return document.querySelectorAll(\"[%s]\")"),
    DATALAYER_GTM("return dataLayer[%s]");
    private String baseQuery;

    private JsFinder(String baseQuery) {
        this.setBaseQuery(baseQuery);
    }

    public String getBaseQuery() {
        return baseQuery;
    }

    public void setBaseQuery(String baseQuery) {
        this.baseQuery = baseQuery;
    }
}
