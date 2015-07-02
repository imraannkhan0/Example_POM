package org.example.test;

public enum Environment {
    PRODUCTION,
    PREPROD,
    QA;

    public static Environment GetEnumStatus(String EnvironmentName) {
        Environment env;
        switch (EnvironmentName.toLowerCase()) {
            case "qa":
                env = Environment.QA;
                break;
            case "production":
                env = Environment.PRODUCTION;
                break;
            case "preprod":
                env = Environment.PREPROD;

            default:
                env = Environment.QA;
                break;
        }
        return env;
    }

}
