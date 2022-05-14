package com.danielling.logparser.service;

import com.danielling.logparser.domain.DataEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {

    final Logger log = LoggerFactory.getLogger(LogParser.class);

    //private static final String NEW_RECORD_IDENTIFIER = "\n";
    private static final String KEY_IDENTIFIER = "ID";

    public List<DataEntry> getEntries(String filePath) {
        List<String> lines = getLines(filePath);
        List<DataEntry> dataEntries = new ArrayList<>();
        DataEntry dataEntry = new DataEntry();
        for (String line : lines) {
            String key = getKey(line);
            String value = getValue(line);
            if (key.equalsIgnoreCase(KEY_IDENTIFIER)) {
                validateAddEntry(dataEntry, dataEntries);
                dataEntry = new DataEntry();
            }
            validateKeyValue(dataEntry, key, value);
        }
        validateAddEntry(dataEntry, dataEntries);
        return dataEntries;
    }

    public String getCsvData(List<DataEntry> dataEntries) {
        String csv = "";
        if (dataEntries != null && dataEntries.size() > 0) {
            csv = dataEntries.get(0).getCsvHeader();
            for (DataEntry dataEntry : dataEntries) {
                csv += "\n" + dataEntry.getCsv();
            }
        }
        return csv;
    }

    private void validateAddEntry(DataEntry dataEntry, List<DataEntry> dataEntries) {
        if (dataEntry.values() > 0) { dataEntries.add(dataEntry); }
    }

    private void validateKeyValue(DataEntry dataEntry, String key, String value) {
        if (key.length() > 0) {
            value = value.contains(",") ? "\"" + value + "\"" : value;
            dataEntry.addValue(key, value);
        }
    }

    private String getKey(String line) {
        return regex(line, "(.*?)=").trim();
    }

    private String getValue(String line) {
        return regex(line, "=(.*)").trim();
    }

    private List<String> getLines(String filePath) {
        List<String> allLines = new ArrayList<>();
        try {
            allLines = Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            log.warn("Unable to read lines from input {}", filePath);
        }
        return allLines;
    }

    private String regex(String input, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        boolean foundMatch = matcher.find();
        return foundMatch ? matcher.group(1) : "";
    }

}
