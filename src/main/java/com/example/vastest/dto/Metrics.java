package com.example.vastest.dto;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class Metrics {

	private static int rowsMissingField = 0;

	private static int messagesBlankContent = 0;

	private static int rowsFieldErrors = 0;

	private static HashMap<String, Integer> originCallsByCountryCode = new HashMap<String, Integer>();

	private static HashMap<String, Integer> destinationCallsByCountryCode = new HashMap<String, Integer>();

	private static int callsOK = 0;

	private static int callsKO = 0;

	private static HashMap<String, Long> callsDurationByCountryCode = new HashMap<String, Long>();

	private static HashMap<String, Integer> wordsCounter = new HashMap<String, Integer>();

	public static void increaseRowsMissingField() {
		++rowsMissingField;
	}

	public static void increaseMessagesBlankContent() {
		++messagesBlankContent;
	}

	public static void increaseRowsFieldErrors() {
		++rowsFieldErrors;
	}

	public static String dto() throws JSONException {
		JSONObject dto = new JSONObject();
		dto.put("rows_missing_fields", rowsMissingField);
		dto.put("messages_blank_content", messagesBlankContent);
		dto.put("rows_field_errors", rowsFieldErrors);
		dto.put("calls_by_origin_country_code", originCallsByCountryCode);
		dto.put("calls_by_destination_country_code", destinationCallsByCountryCode);
		dto.put("relation_OK_KO_calls", new HashMap<String, Integer>() {
			private static final long serialVersionUID = 184565730835476023L;
			{
				put("OK", callsOK);
				put("KO", callsKO);
			}
		});
		dto.put("duration_by_country_code", callsDurationByCountryCode);
		dto.put("word_ranking", wordsCounter);
		return dto.toString();
	}

	public static void callsByCountryCode(String originCountryCode, String destinationCountryCode) {
		originCallsByCountryCode.put(originCountryCode,
				originCallsByCountryCode.getOrDefault(originCountryCode, 0) + 1);
		destinationCallsByCountryCode.put(destinationCountryCode,
				destinationCallsByCountryCode.getOrDefault(destinationCountryCode, 0) + 1);
	}

	public static void relationOKKO(String statusCode) {
		switch (statusCode) {
		case "OK":
			++callsOK;
			break;
		case "KO":
			++callsKO;
			break;
		default:
			break;
		}
	}

	public static void durationByCountryCode(Long duration, String originCountryCode) {
		callsDurationByCountryCode.put(originCountryCode,
				callsDurationByCountryCode.getOrDefault(originCountryCode, duration) + duration);
	}

	public static void messageContentWords(String messageContent) {
		if (messageContent.isEmpty()) {
			increaseMessagesBlankContent();
		} else {
			messageContent = messageContent.replaceAll("\\d", "");
			messageContent = messageContent.replaceAll("\\p{Punct}", "");
			String[] words = messageContent.split(" ");
			for (String word : words) {
				if (!word.isBlank()) {
					wordsCounter.put(word, wordsCounter.getOrDefault(word, 0) + 1);
				}
			}
		}
	}

	public static void clear() {
		rowsMissingField = 0;
		messagesBlankContent = 0;
		rowsFieldErrors = 0;
		originCallsByCountryCode = new HashMap<String, Integer>();
		destinationCallsByCountryCode = new HashMap<String, Integer>();
		callsOK = 0;
		callsKO = 0;
		callsDurationByCountryCode = new HashMap<String, Long>();
		wordsCounter = new HashMap<String, Integer>();
	}

}
