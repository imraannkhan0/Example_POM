package org.example.test.javascript;

/**
 * Created by Sabyasachi on 10/6/2015.
 */
public class JsElement {
    private JsBy jsBy;
    private String jsBaseQuery;

    public JsElement(JsBy jsBy) {
        this.setJsBy(jsBy);
        this.setJsBaseQuery(this.jsBy.getBaseQuery());
    }

    public JsBy getJsBy() {
        return jsBy;
    }

    public void setJsBy(JsBy jsBy) {
        this.jsBy = jsBy;
    }

    public String getJsBaseQuery() {
        return jsBaseQuery;
    }

    public void setJsBaseQuery(String jsBaseQuery) {
        this.jsBaseQuery = jsBaseQuery;
    }
}
