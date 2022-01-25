package com.example.vastest.controller;

import java.util.Date;

import org.springframework.http.ResponseEntity;

public interface VasController {

	public ResponseEntity<String> information(Date date) throws Exception;
	
	public ResponseEntity<String> metrics();
	
	public ResponseEntity<String> kpis();
}
