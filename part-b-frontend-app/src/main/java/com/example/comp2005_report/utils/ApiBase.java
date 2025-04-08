package com.example.comp2005_report.utils;

public enum ApiBase {
    UNI("https://web.socem.plymouth.ac.uk/COMP2005/api"),
    LOCAL("http://localhost:8080");

    private final String value;

    ApiBase(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
