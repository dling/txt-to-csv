package com.danielling.logparser;

import com.danielling.logparser.domain.DataEntry;
import com.danielling.logparser.service.LogParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LogParserApplicationTests {

	private static final String TEST_FILE_LOCATION = "src/test/resources/sample-data.txt";

	private static final String EXPECTED_CSV_OUTPUT = "ID,Department,Employees,Location,Founded,Update,Notes\n" +
			"456,Marketing,5,\"Stockholm, Sweden\",2022-01-15,2022-01-15,\"Marketing includes email newsletters, facebook, twitter etc\"\n" +
			"954,IT,2,\"Stockholm, Sweden\",2022-01-15,2022-02-15,\"IT, workspace administration, firewalls etc\"\n" +
			"573,Engineering,17,\"Stockholm, Sweden\",2022-01-15,2022-02-25,Software development and devops\n" +
			"573,Management,4,\"Stockholm, Sweden\",2022-01-15,2022-03-04,CEO and founders";

	LogParser logParser = new LogParser();

	@Test
	void getEntriesAsCorrectNumberOfEntries() {
		List<DataEntry> entries = logParser.getEntries(TEST_FILE_LOCATION);
		assertEquals(4, entries.size());
	}

	@Test
	void getCorrectCSVData() {
		List<DataEntry> entries = logParser.getEntries(TEST_FILE_LOCATION);
		String csvData = logParser.getCsvData(entries);
		assertEquals(EXPECTED_CSV_OUTPUT, csvData);
	}

	//@Test
	void getCsvCorrectConsolePrinted() {
		List<DataEntry> entries = logParser.getEntries(TEST_FILE_LOCATION);
		String csvData = logParser.getCsvData(entries);
		System.out.println(csvData);
	}

}
