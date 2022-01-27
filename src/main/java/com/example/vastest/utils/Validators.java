package com.example.vastest.utils;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.exampe.vastest.enums.MessageStatus;
import com.exampe.vastest.enums.StatusCode;
import com.example.vastest.dto.Metrics;

@Component
public class Validators {
	
	public static boolean isDateFormatValid(String date) {
		boolean res = false;
		if(date.length() == 8 && Utils.isNumeric(date)) {
			res = true;
		}
		return res;
	}

	public static boolean isCallValid(JSONObject call) throws JSONException {
		boolean ret = false;

		String timestamp = call.getString("timestamp");
		String origin = call.getString("origin");
		String destination = call.getString("destination");
		String duration = call.getString("duration");
		String statusCode = call.getString("status_code");
		String statusDescription = call.getString("status_description");

		boolean missingFields = checkIfMissingFields(
				List.of(timestamp, origin, destination, duration, statusCode, statusDescription));

		boolean validFields = checkIfCallFieldsValid(timestamp, origin, destination, duration, statusCode,
				statusDescription);

		if (!missingFields && validFields) {
			ret = true;
		}
		return ret;
	}

	public static boolean isMsgValid(JSONObject msg) throws JSONException {
		boolean ret = false;

		String timestamp = msg.getString("timestamp");
		String origin = msg.getString("origin");
		String destination = msg.getString("destination");
		String messageContent = msg.getString("message_content");
		String messageStatus = msg.getString("message_status");

		boolean missingFields = checkIfMissingFields(List.of(timestamp, origin, destination, messageStatus));

		boolean validFields = checkIfMsgFieldsValid(timestamp, origin, destination, messageContent, messageStatus);

		if (!missingFields && validFields) {
			ret = true;
		}
		return ret;
	}

	private static boolean checkIfMissingFields(List<String> fields) {
		boolean missingField = fields.stream().anyMatch((f) -> f.isEmpty());
		if (missingField) {
			Metrics.increaseRowsMissingField();
		}
		return missingField;
	}

	private static boolean checkIfCallFieldsValid(String timestamp, String origin, String destination, String duration,
			String statusCode, String statusDescription) {
		boolean ret = true;

		if (!Utils.isNumeric(timestamp) || !Utils.isNumeric(origin) || !Utils.isNumeric(destination)
				|| !Utils.isNumeric(duration)) {
			Metrics.increaseRowsFieldErrors();
			ret = false;
		}

		if (ret && !StatusCode.KO.toString().equals(statusCode) && !StatusCode.OK.toString().equals(statusCode)) {
			Metrics.increaseRowsFieldErrors();
			ret = false;
		}

		return ret;
	}

	private static boolean checkIfMsgFieldsValid(String timestamp, String origin, String destination,
			String messageContent, String messageStatus) {
		boolean ret = true;

		if (!Utils.isNumeric(timestamp) || !Utils.isNumeric(origin) || !Utils.isNumeric(destination)) {
			Metrics.increaseRowsFieldErrors();
			ret = false;
		}

		if (ret && !MessageStatus.DELIVERED.toString().equals(messageStatus)
				&& !MessageStatus.SEEN.toString().equals(messageStatus)) {
			Metrics.increaseRowsFieldErrors();
			ret = false;
		}

		return ret;
	}

}
