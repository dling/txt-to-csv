package com.danielling.logparser.domain;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DataEntry {

    private final Map<String,String> keyValue = new LinkedHashMap<>();

    public int values() {
        return keyValue.size();
    }

    public void addValue(String key, String value) {
        keyValue.put(key, value);
    }

    public String getCsvHeader() {
        return keyValue.entrySet()
                .stream()
                .map(entry -> entry.getKey())
                .collect(Collectors.joining(","));
    }

    public String getCsv() {
        return keyValue.entrySet()
                .stream()
                .map(entry -> entry.getValue())
                .collect(Collectors.joining(","));
    }


}
