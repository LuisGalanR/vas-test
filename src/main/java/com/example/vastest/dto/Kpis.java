package com.example.vastest.dto;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class Kpis {

	private static int processedJsonFiles = 0;

	private static int numberOfRows = 0;

	private static int numberOfCalls = 0;

	private static int numberOfMessages = 0;

	private static List<String> originCountryCodes = new ArrayList<String>();

	private static List<String> destinationCountryCodes = new ArrayList<String>();

	private static List<Long> durationOfProcesses = new ArrayList<Long>();

	public static void addDuration(long duration) {
		durationOfProcesses.add(duration);
	}

	public static void incrementProcessedJsonFiles() {
		++processedJsonFiles;
	}

	public static void incrementNumberOfRows() {
		++numberOfRows;
	}

	public static void incrementNumberOfCalls() {
		++numberOfCalls;
	}

	public static void incrementNumberOfMsg() {
		++numberOfMessages;
	}

	public static String dto() throws JSONException {
		JSONObject dto = new JSONObject();
		dto.put("proccesed_json_files", processedJsonFiles);
		dto.put("number_of_rows", numberOfRows);
		dto.put("number_of_calls", numberOfCalls);
		dto.put("number_of_messages", numberOfMessages);
		dto.put("number_different_origin_country_codes", originCountryCodes.size());
		dto.put("different_origin_country_codes", originCountryCodes);
		dto.put("number_different_destination_country_codes", destinationCountryCodes.size());
		dto.put("different_destination_country_codes", destinationCountryCodes);
		dto.put("duration_of_proccesed_json_files", durationOfProcesses);
		return dto.toString();
	}

	public static void completed(long duration) {
		Kpis.incrementProcessedJsonFiles();
		Kpis.addDuration(duration);
	}

	public static void differentCountryCodes(String originCountryCode, String destinationCountryCode) {
		if (!originCountryCodes.contains(originCountryCode)) {
			originCountryCodes.add(originCountryCode);
		}

		if (!destinationCountryCodes.contains(destinationCountryCode)) {
			destinationCountryCodes.add(destinationCountryCode);
		}
	}

	public static void clear() {
		processedJsonFiles = 0;
		numberOfRows = 0;
		numberOfCalls = 0;
		numberOfMessages = 0;
		originCountryCodes = new ArrayList<String>();
		destinationCountryCodes = new ArrayList<String>();
		durationOfProcesses = new ArrayList<Long>();
	}

}
