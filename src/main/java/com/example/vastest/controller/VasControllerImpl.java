package com.example.vastest.controller;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.vastest.service.InformationService;


@RestController()
public class VasControllerImpl implements VasController {
	
	private InformationService informationService;

	public VasControllerImpl(InformationService informationService) {
		this.informationService = informationService;
	}

	@GetMapping("/information/{date}")
	public ResponseEntity<String> information(@PathVariable @DateTimeFormat(pattern= "yyyyMMdd") Date date) {
		ResponseEntity<String> res = informationService.informationUpdate(date);
		return res;
	}

	@GetMapping("/metrics")
	public ResponseEntity<String> metrics() {
		// TODO Auto-generated method stub
		return null;
	}

	@GetMapping("/kpis")
	public ResponseEntity<String> kpis() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
