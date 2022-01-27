package com.example.vastest.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.vastest.service.InformationService;
import com.example.vastest.utils.Validators;

@RestController()
public class InformationController {

	private InformationService informationService;

	public InformationController(InformationService informationService) {
		this.informationService = informationService;
	}

	@GetMapping("/information/{date}")
	public ResponseEntity<String> information(@PathVariable String date) throws JSONException, ParseException {
		ResponseEntity<String> res = null;
		if (Validators.isDateFormatValid(date)) {
			String update = informationService.informationUpdate(new SimpleDateFormat("yyyyMMdd").parse(date));
			if ("KO".equals(update)) {
				res = new ResponseEntity<String>("No JSON for date specified", HttpStatus.NOT_FOUND);
			} else {
				res = new ResponseEntity<String>("JSON Processed correctly", HttpStatus.OK);
			}
		} else {
			res = new ResponseEntity<String>("Date format not valid", HttpStatus.BAD_REQUEST);
		}

		return res;
	}

	@RequestMapping(value = "metrics", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> metrics() throws JSONException {
		return new ResponseEntity<String>(informationService.sendMetrics(), HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "kpis", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> kpis() throws JSONException {
		return new ResponseEntity<String>(informationService.sendKpis(), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/clear")
	public ResponseEntity<String> clear() {
		informationService.clear();
		return new ResponseEntity<String>("Data cleared", HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/")
	public ResponseEntity<String> welcome() {
		return new ResponseEntity<String>("Insert one endpoint in the URL", HttpStatus.OK);
	}
	

}
