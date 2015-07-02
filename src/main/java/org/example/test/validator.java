package org.example.test;

public class validator {

    validationActions validations = null;


    public validator(String content, DataType WebservicePartAndDatatype) {
        setvalidator(content, WebservicePartAndDatatype);
    }

    public validationActions getValidator() {
        return validations;
    }

    private void setvalidator(String content, DataType WebservicePartAndDatatype) {
        switch (WebservicePartAndDatatype) {
            case JSON:
                validations = new jsonValidator(content);
                break;
            case XML:
                validations = new xmlValidator(content);
                break;
            case URLENCODED:
                validations = new jsonValidator(content);
                break;
        }
    }
}



