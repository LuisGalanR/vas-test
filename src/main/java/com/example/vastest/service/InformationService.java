package com.example.vastest.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.vastest.dto.Kpis;
import com.example.vastest.dto.Metrics;
import com.example.vastest.utils.Validators;

@Service
public class InformationService {

	public final String informationUpdate(Date date) throws JSONException {
		
		Metrics.clear();

		final long start = System.currentTimeMillis();

		String lines = informationRequest(date);

		if (!"KO".equals(lines)) {
			processLines(lines.split("\r\n"));

			final long duration = System.currentTimeMillis() - start;

			Kpis.completed(duration);
		}

		return lines;
	}

	public final String sendMetrics() throws JSONException {
		return Metrics.dto();
	}

	public String sendKpis() throws JSONException {
		return Kpis.dto();
	}

	public void clear() {
		Metrics.clear();
		Kpis.clear();
	}
	
	private String informationRequest(Date date) {
		String res = "";
		String url = "https://raw.githubusercontent.com/vas-test/test1/master/logs/MCP_"
				+ new SimpleDateFormat("yyyyMMdd").format(date) + ".json";
		try {
			res = new RestTemplate().getForObject(url, String.class);
		} catch (Exception e) {
			res = "KO";
		}
		return res;
	}

	private void processLines(String[] lines) throws JSONException {
		for (String line : lines) {
			Kpis.incrementNumberOfRows();
			JSONObject jsonLine = new JSONObject(line);
			switch (jsonLine.getString("message_type")) {
			case "CALL":
				Kpis.incrementNumberOfCalls();
				processCall(jsonLine);
				break;
			case "MSG":
				Kpis.incrementNumberOfMsg();
				processMsg(jsonLine);
				break;
			case "":
				Metrics.increaseRowsMissingField();
				break;
			default:
				Metrics.increaseRowsFieldErrors();
				break;
			}
		}

	}

	private void processMsg(JSONObject jsonLine) throws JSONException {
		if (Validators.isMsgValid(jsonLine)) {
			String originCountryCode = jsonLine.getString("origin").substring(0, 2);
			String destinationCountryCode = jsonLine.getString("origin").substring(0, 2);

			String messageContent = jsonLine.getString("message_content");
			Metrics.messageContentWords(messageContent);

			Kpis.differentCountryCodes(originCountryCode, destinationCountryCode);
		}
	}

	private void processCall(JSONObject jsonLine) throws JSONException {
		if (Validators.isCallValid(jsonLine)) {
			String originCountryCode = jsonLine.getString("origin").substring(0, 2);
			String destinationCountryCode = jsonLine.getString("origin").substring(0, 2);
			Metrics.callsByCountryCode(originCountryCode, destinationCountryCode);

			String statusCode = jsonLine.getString("status_code");
			Metrics.relationOKKO(statusCode);

			Long duration = jsonLine.getLong("duration");
			Metrics.durationByCountryCode(duration, originCountryCode);

			Kpis.differentCountryCodes(originCountryCode, destinationCountryCode);
		}
	}

}
