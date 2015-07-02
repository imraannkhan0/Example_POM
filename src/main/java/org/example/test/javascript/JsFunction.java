package org.example.test.javascript;

/**
 * Created by Sabyasachi on 10/6/2015.
 */
public enum JsFunction {

    CLICK("%s.click();"),
    CLEAR_CHECKBOX("if($(\".%s\").hasClass(\"checked\")){$(\".%s\").click();}"),
    ADD_LIST_FUNCTION("%s[%d].innerHTML;"),
    LENGTH("%s.length;"),
    SUBMIT(""),
    GET_TAG_NAME(""),
    GET_ATTRIBUTE(""),
    IS_SELECTED(""),
    IS_ENABLED(""),
    GET_TEXT(""),
    IS_DISPLAYED(""),
    GET_CSS_VALUE(""),
    SET_TEXT("%s.value=%s");
    private String baseQuery;

    private JsFunction(String functionAddOn) {
        this.baseQuery = functionAddOn;
    }

    public String getBaseQuery() {
        return baseQuery;
    }

    public void setBaseQuery(String functionAddOn) {
        this.baseQuery = functionAddOn;
    }
}
