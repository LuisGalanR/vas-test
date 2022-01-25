package com.example.vastest.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InformationService {

	public final ResponseEntity<String> informationUpdate(Date date) {
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://raw.githubusercontent.com/vas-test/test1/master/logs/MCP_"+ new SimpleDateFormat("yyyyMMdd").format(date) +".json";
		ResponseEntity<String> res = restTemplate.getForEntity(url, String.class);
		return res;
	}
}
