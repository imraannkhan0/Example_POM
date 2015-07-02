package org.example.test.javascript;

/**
 * Created by Sabyasachi on 10/6/2015.
 */
public class JsBy {

    private String baseQuery;

    public JsBy(String baseQuery) {
        this.setBaseQuery(baseQuery);
    }

    public static JsBy className(String className) {
        return new JsBy(JsFinder.CLASSNAME.getBaseQuery().replace("%s",
                className));
    }

    public static JsBy id(String id) {
        return new JsBy(JsFinder.ID.getBaseQuery().replace("%s", id));
    }

    public static JsBy name(String name) {
        return new JsBy(JsFinder.NAME.getBaseQuery().replace("%s", name));
    }

    public static JsBy tagName(String tagName) {
        return new JsBy(JsFinder.TAG_NAME.getBaseQuery().replace("%s", tagName));
    }

    public static JsBy tagNameNs(String tagNameNs) {
        return new JsBy(JsFinder.TAG_NAME_NS.getBaseQuery().replace("%s",
                tagNameNs));
    }

    public static JsBy attributeValue(String attributeAndValue) {
        return new JsBy(JsFinder.CLASSNAME.getBaseQuery()
                .replace("%s", attributeAndValue).replace("#", "="));
    }

    public String getBaseQuery() {
        return baseQuery;
    }

    public void setBaseQuery(String baseQuery) {
        this.baseQuery = baseQuery;
    }
}
